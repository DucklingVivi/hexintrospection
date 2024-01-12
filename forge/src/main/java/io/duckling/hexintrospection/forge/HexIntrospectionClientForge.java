package io.duckling.hexintrospection.forge;

import io.duckling.hexintrospection.MindSphereRenderer;
import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.HexIntrospectionClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Forge client loading entrypoint.
 */
public class HexIntrospectionClientForge {
    public static void init(FMLClientSetupEvent event) {
        HexIntrospectionClient.init();

        MinecraftForge.EVENT_BUS.addListener(HexIntrospectionClientForge::renderEvent);
    }


    private static void renderEvent(RenderLevelStageEvent event){
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS){
            ClientWorld world = MinecraftClient.getInstance().world;
            if(world != null){
                if(world.getDimensionKey().getValue().getNamespace().equals(HexIntrospection.MOD_ID)){
                    Tessellator tessellator = Tessellator.getInstance();
                    MatrixStack poseStack = event.getPoseStack();
                    Camera camera = event.getCamera();
                    MindSphereRenderer.render(tessellator,poseStack,camera);
                }
            }
        }
    }




}
