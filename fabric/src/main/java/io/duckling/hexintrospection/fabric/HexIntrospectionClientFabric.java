package io.duckling.hexintrospection.fabric;

import io.duckling.hexintrospection.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Fabric client loading entrypoint.
 */
public class HexIntrospectionClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HexIntrospectionClient.init();
        WorldRenderEvents.AFTER_TRANSLUCENT.register((context -> {
            Tessellator tessellator = Tessellator.getInstance();
            MatrixStack poseStack = context.matrixStack();
            Camera camera = context.camera();
            MindSphereRenderer.render(tessellator,poseStack,camera);
        }));
    }
}
