package io.duckling.hexintrospection;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class DynamicWorldHandle {

    private final DynamicWorldManager manager;
    private final ServerWorld world;

    DynamicWorldHandle(DynamicWorldManager manager, ServerWorld world){
        this.manager = manager;
        this.world = world;
    }

    public void delete(){
        //TODO implement
    }

    public void unload(){
        //TODO implement
    }
    public ServerWorld asWorld() {
        return this.world;
    }
    public RegistryKey<World> getRegistryKey() {
        return this.world.getRegistryKey();
    }

}
