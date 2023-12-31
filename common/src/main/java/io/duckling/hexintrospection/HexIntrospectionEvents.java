package io.duckling.hexintrospection;

import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import io.duckling.hexintrospection.network.MindSpherePacket;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;
import java.util.UUID;

public class HexIntrospectionEvents {

    public static void init(){
        LifecycleEvent.SERVER_LEVEL_LOAD.register(event ->{
            HexIntrospectionSaveManager.get(event.getServer());
        });
        LifecycleEvent.SERVER_LEVEL_SAVE.register(event ->{
            HexIntrospectionSaveManager.get(event.getServer());
        });

        PlayerEvent.CHANGE_DIMENSION.register((player, oldDimension, newDimension) ->{
            if(Objects.equals(newDimension.getValue().getNamespace(), "hexintrospection")){
                MindSphereData data = HexIntrospectionSaveManager.get(player.getServer()).getMindSphereData(UUID.fromString(newDimension.getValue().getPath()));
                MindSpherePacket.syncAll(data).sendToPlayer(player);
            }
        });
        PlayerEvent.PLAYER_JOIN.register(player -> {
            if(Objects.equals(player.getWorld().getRegistryKey().getValue().getNamespace(), "hexintrospection")) {
                MindSphereData data = HexIntrospectionSaveManager.get(player.getServer()).getMindSphereData(UUID.fromString(player.getWorld().getRegistryKey().getValue().getPath()));
                MindSpherePacket.syncAll(data).sendToPlayer(player);
            }
        });
    }

}
