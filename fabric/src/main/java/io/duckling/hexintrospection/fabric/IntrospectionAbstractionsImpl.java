package io.duckling.hexintrospection.fabric;

import io.duckling.hexintrospection.DynamicWorld;
import io.duckling.hexintrospection.IntrospectionAbstractions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;

public class IntrospectionAbstractionsImpl {
    /**
     * This is the actual implementation of {@link IntrospectionAbstractions#getConfigDirectory()}.
     */
    public static void onWorldLoad(MinecraftServer server, DynamicWorld world){
        ServerWorldEvents.LOAD.invoker().onWorldLoad(server, world);
    }
}
