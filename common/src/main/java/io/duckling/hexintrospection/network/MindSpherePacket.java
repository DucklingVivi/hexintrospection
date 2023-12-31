package io.duckling.hexintrospection.network;

import dev.architectury.networking.NetworkManager;
import io.duckling.hexintrospection.MindSphereData;
import io.duckling.hexintrospection.MindSphereEyeData;
import io.duckling.hexintrospection.client.MindSphereClientData;
import io.duckling.hexintrospection.client.MindSphereClientEyeData;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class MindSpherePacket {

    NbtCompound data;
    MindSphereOperation operation;

    public MindSpherePacket(NbtCompound data, MindSphereOperation operation){
        this.data = data;
        this.operation = operation;
    }
    public MindSpherePacket(PacketByteBuf packetByteBuf) {
        NbtCompound compound = packetByteBuf.readNbt();
        assert compound!= null;
        data = compound.getCompound("data");
        operation = MindSphereOperation.valueOf(compound.getString("operation"));
    }


    public static MindSpherePacket syncAll(MindSphereData data){
        NbtCompound compound = data.toClientNBT();
        return new MindSpherePacket(compound, MindSphereOperation.SYNCALL);
    }

    public static MindSpherePacket addEye(int id, MindSphereEyeData eye){
        NbtCompound data = new NbtCompound();
        data.putInt("id",id);
        data.put("eyeData", eye.toClientNBT());
        return new MindSpherePacket(data, MindSphereOperation.ADDEYE);
    }
    public static MindSpherePacket removeEye(int id){
        NbtCompound data = new NbtCompound();
        data.putInt("id",id);
        return new MindSpherePacket(data, MindSphereOperation.REMOVEEYE);
    }
    public static void recieve(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        MindSpherePacket packet = new MindSpherePacket(packetByteBuf);
        packet.handle(packetContext);
    }

    public PacketByteBuf encode() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        NbtCompound compound = new NbtCompound();
        compound.put("data", data);
        compound.putString("operation", operation.toString());
        buf.writeNbt(compound);
        return buf;
    }
    public void sendToPlayer(ServerPlayerEntity player){
        NetworkManager.sendToPlayer(player, Packets.MINDSPHERE, encode());
    }

    public void sendToPlayers(Iterable<ServerPlayerEntity> players){
        NetworkManager.sendToPlayers(players, Packets.MINDSPHERE, encode());
    }
    private void handle(NetworkManager.PacketContext packetContext) {
        switch (operation){
            case SYNCALL -> {
                handleSYNCALL(packetContext);
            }
            case ADDEYE -> {
                handleADDEYE(packetContext);
            }
            case REMOVEEYE -> {
                handleREMOVEEYE(packetContext);
            }
        }
    }


    private void handleSYNCALL(NetworkManager.PacketContext packetContext) {
        MindSphereClientData wipData = MindSphereClientData.get_currentData();
        wipData.eyeData.clear();
        wipData.set_fogdistance(data.getFloat("fogdistance"));
        NbtCompound eyeData = data.getCompound("eyeData");
        for (String key : eyeData.getKeys()){
            NbtCompound eyeDataCompound = eyeData.getCompound(key);
            MindSphereClientEyeData eyeDataObject = MindSphereClientEyeData.fromNBT(eyeDataCompound);
            wipData.eyeData.put(Integer.parseInt(key), eyeDataObject);
        }
        MindSphereClientData.set_currentData(wipData);
    }

    private void handleADDEYE(NetworkManager.PacketContext packetContext) {
        MindSphereClientData.get_currentData().eyeData.put(data.getInt("id"), MindSphereClientEyeData.fromNBT(data.getCompound("eyeData")));
    }
    private void handleREMOVEEYE(NetworkManager.PacketContext packetContext) {
        MindSphereClientData.get_currentData().eyeData.remove(data.getInt("id"));
    }
    public enum MindSphereOperation {
        SYNCALL,
        ADDEYE,
        REMOVEEYE
    }

}
