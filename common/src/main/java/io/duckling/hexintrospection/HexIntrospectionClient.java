package io.duckling.hexintrospection;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import io.duckling.hexintrospection.client.BlockEntityEyeInfuseRenderer;
import io.duckling.hexintrospection.registry.HexIntrospectionBlockEntityRegistry;
import net.minecraft.client.render.item.ItemRenderer;

/**
 * Common client loading entrypoint.
 */
public class HexIntrospectionClient {
    public static void init() {
        BlockEntityRendererRegistry.register(HexIntrospectionBlockEntityRegistry.EYE_INFUSE.get(), BlockEntityEyeInfuseRenderer::new);
    }


}
