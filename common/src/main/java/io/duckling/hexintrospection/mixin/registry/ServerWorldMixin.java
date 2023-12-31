package io.duckling.hexintrospection.mixin.registry;

import io.duckling.hexintrospection.DucklingWorldAccess;
import net.minecraft.network.Packet;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements DucklingWorldAccess {

    private static final int TIMEOUT = 300;
    @Unique
    private boolean duckling$tickWhenEmpty = true;
    @Unique
    private int duckling$tickTimeout;

    @Shadow
    public abstract List<ServerPlayerEntity> getPlayers();

    @Shadow
    public abstract ServerChunkManager getChunkManager();


    @Override
    public void duckling$setTickWhenEmpty(boolean tickWhenEmpty) {

    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        boolean shouldTick = this.duckling$tickWhenEmpty || !this.isWorldEmpty();
        if (shouldTick) {
            this.duckling$tickTimeout = TIMEOUT;
        } else if (this.duckling$tickTimeout-- <= 0) {
            ci.cancel();
        }
    }
    @Override
    public boolean duckling$shouldTick() {
        boolean shouldTick = this.duckling$tickWhenEmpty || !this.isWorldEmpty();
        return shouldTick || this.duckling$tickTimeout > 0;
    }


    private boolean isWorldEmpty() {
        return this.getPlayers().isEmpty() && this.getChunkManager().getLoadedChunkCount() <= 0;
    }

}
