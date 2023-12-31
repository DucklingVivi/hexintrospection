package io.duckling.hexintrospection.network;


import com.mojang.brigadier.context.CommandContext;
import dev.architectury.networking.NetworkManager;
import io.duckling.hexintrospection.HexIntrospection;
import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class DimensionalSyncPacket {


    Set<RegistryKey<World>> newDims;
    Set<RegistryKey<World>> removedDims;
    public DimensionalSyncPacket(Set<RegistryKey<World>> newDims, Set<RegistryKey<World>> removedDims){
        this.newDims = newDims;
        this.removedDims = removedDims;
    }

    public DimensionalSyncPacket(PacketByteBuf packetByteBuf){
        newDims = new HashSet<>();
        removedDims = new HashSet<>();

        final int newDimensionCount = packetByteBuf.readVarInt();
        for (int i = 0; i < newDimensionCount; i++) {
            final RegistryKey<World> worldID = packetByteBuf.readRegistryKey(Registry.WORLD_KEY);
            newDims.add(worldID);
        }

        final int removedDimensionCount = packetByteBuf.readVarInt();
        for (int i = 0; i < removedDimensionCount; i++) {
            final RegistryKey<World> worldID = packetByteBuf.readRegistryKey(Registry.WORLD_KEY);
            removedDims.add(worldID);
        }

    }
    public PacketByteBuf encode(){
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

        buf.writeVarInt(this.newDims.size());
        for (RegistryKey<World> key : this.newDims){
            buf.writeRegistryKey(key);
        }

        buf.writeVarInt(this.removedDims.size());
        for (RegistryKey<World> key : this.removedDims){
            buf.writeRegistryKey(key);
        }
        return buf;
    }
    public void sendToPlayer(ServerPlayerEntity player){
        NetworkManager.sendToPlayer(player, Packets.DIMENSIONALSYNC, encode());
    }

    public void sendToPlayers(Iterable<ServerPlayerEntity> players){
        NetworkManager.sendToPlayers(players, Packets.DIMENSIONALSYNC, encode());
    }

    private void handle(NetworkManager.PacketContext context) {

        ClientPlayNetworkHandler handler = MinecraftClient.getInstance().getNetworkHandler();
        if(handler == null){
            return;
        }
        Set<RegistryKey<World>> keys = handler.getWorldKeys();

        keys.addAll(newDims);
        for (RegistryKey<World> key : this.removedDims){
            keys.remove(key);
        }
    }

    public static void recieve(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        DimensionalSyncPacket packet = new DimensionalSyncPacket(packetByteBuf);
        packet.handle(packetContext);


    }


}
