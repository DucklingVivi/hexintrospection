package io.duckling.hexintrospection.mixin;


import io.duckling.hexintrospection.HexIntrospection;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow private ClientWorld world;

    @Inject(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V", at = @At("HEAD"), cancellable = true)
    public void renderClouds(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, double d, double e, double f, CallbackInfo ci) {
        if (Objects.equals(world.getDimensionKey().getValue().getNamespace(), HexIntrospection.MOD_ID)){
            ci.cancel();
        }
    }

    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At("HEAD"), cancellable = true)
    public void renderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        if (Objects.equals(world.getDimensionKey().getValue().getNamespace(), HexIntrospection.MOD_ID)){
            ci.cancel();
        }
    }

}
