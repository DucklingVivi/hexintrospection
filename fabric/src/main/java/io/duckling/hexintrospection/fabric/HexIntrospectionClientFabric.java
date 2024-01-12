package io.duckling.hexintrospection.fabric;

import io.duckling.hexintrospection.*;
import io.duckling.hexintrospection.registry.HexIntrospectionBlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Fabric client loading entrypoint.
 */
public class HexIntrospectionClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HexIntrospectionClient.init();

        WorldRenderEvents.AFTER_ENTITIES.register((context -> {
            Tessellator tessellator = Tessellator.getInstance();
            MatrixStack poseStack = context.matrixStack();
            Camera camera = context.camera();
            MindSphereRenderer.render(tessellator,poseStack,camera);
        }));

    }
}
