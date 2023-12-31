package io.duckling.hexintrospection;

import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import org.jetbrains.annotations.Nullable;

public final class DynamicProgressListener implements WorldGenerationProgressListener {
    public static final DynamicProgressListener INSTANCE = new DynamicProgressListener();
    @Override
    public void start(ChunkPos spawnPos) {

    }

    @Override
    public void setChunkStatus(ChunkPos pos, @Nullable ChunkStatus status) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
