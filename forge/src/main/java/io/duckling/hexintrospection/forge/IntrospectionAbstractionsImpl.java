package io.duckling.hexintrospection.forge;

import io.duckling.hexintrospection.DynamicWorld;
import io.duckling.hexintrospection.IntrospectionAbstractions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.NotImplementedException;

import java.nio.file.Path;

public class IntrospectionAbstractionsImpl {
    /**
     * This is the actual implementation of {@link IntrospectionAbstractions#getConfigDirectory()}.
     */
    public static void onWorldLoad(MinecraftServer server, DynamicWorld world){
        MinecraftForge.EVENT_BUS.post(new LevelEvent.Load(world));
    }
}
