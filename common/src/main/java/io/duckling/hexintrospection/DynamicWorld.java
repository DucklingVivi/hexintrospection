package io.duckling.hexintrospection;


import com.google.common.collect.ImmutableList;
import io.duckling.hexintrospection.mixin.MinecraftServerAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Util;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.ToDoubleBiFunction;

public class DynamicWorld extends ServerWorld {

    DynamicWorld(MinecraftServer server, RegistryKey<World> registryKey, DimensionOptions options){
        super(
                server,
                Util.getMainWorkerExecutor(),
                ((MinecraftServerAccess)server).getSession(),
                server.getSaveProperties().getMainWorldProperties(),
                registryKey,
                options,
                DynamicProgressListener.INSTANCE,
                false,
                0L,
                ImmutableList.of(),
                false
        );

    }

}
