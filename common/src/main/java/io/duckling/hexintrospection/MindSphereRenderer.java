package io.duckling.hexintrospection;

import com.mojang.blaze3d.systems.RenderSystem;
import io.duckling.hexintrospection.client.MindSphereClientData;
import io.duckling.hexintrospection.client.MindSphereClientEyeData;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

public class MindSphereRenderer {



    private static float mindsphererotationoffset = 0;


    private static Identifier FOG_TEXTURE = new Identifier(HexIntrospection.MOD_ID,"textures/misc/fog.png");
    private static Identifier EYE_TEXTURE = new Identifier(HexIntrospection.MOD_ID,"textures/misc/eye.png");
    public static final float EYE_TEXTURE_HEIGHT_COUNT = 28;
    public static final float EYE_TEXTURE_WIDTH_COUNT = 3;
    public static void render(Tessellator tesselator, MatrixStack poseStack, Camera camera) {
        RenderEyes(tesselator,poseStack,camera);
        RenderFog(tesselator,poseStack,camera);

    }



    private static void RenderEyes(Tessellator tesselator, MatrixStack poseStack, Camera camera){
        BufferBuilder buffer = tesselator.getBuffer();
        poseStack.push();
        poseStack.translate(-camera.getPos().x, -camera.getPos().y,
                -camera.getPos().z);
        RenderSystem.setShaderTexture(0, EYE_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableDepthTest();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);


        for(MindSphereClientEyeData eyeData : MindSphereClientData.get_currentData().eyeData.values()) {
            int index = 10;
            float u_multi = 1f / EYE_TEXTURE_WIDTH_COUNT;
            float v_multi = 1f / EYE_TEXTURE_HEIGHT_COUNT;
            float outlinev0 = (index * v_multi);
            float outlinev1 = ((index + 1) * v_multi);
            poseStack.push();
            poseStack.translate(eyeData.position.getX() + 0.5f, eyeData.position.getY(), eyeData.position.getZ()+ 0.5f);

            Vector2f up = new Vector2f(0, 1);
            float angle = (float) Math.atan2(eyeData.position.getZ(), eyeData.position.getX()) - (float)Math.atan2(up.getY(), up.getX());
            Matrix4f mat = new Matrix4f();
            ((Matrix4fExtended) (Object) mat).rotation(angle, 0, 1, 0);
            poseStack.multiplyPositionMatrix(mat);



            float f12 = 10;
            buffer.vertex(poseStack.peek().getPositionMatrix(), -f12, 0, -f12).texture(0, outlinev0).next();
            buffer.vertex(poseStack.peek().getPositionMatrix(), f12, 0, -f12).texture(u_multi, outlinev0).next();
            buffer.vertex(poseStack.peek().getPositionMatrix(), f12, 0, f12).texture(u_multi, outlinev1).next();
            buffer.vertex(poseStack.peek().getPositionMatrix(), -f12, 0, f12).texture(0, outlinev1).next();
            poseStack.push();
            poseStack.translate(0, 0.01, 0);
            buffer.vertex(poseStack.peek().getPositionMatrix(), -f12, 0, -f12).texture(u_multi, 0).next();
            buffer.vertex(poseStack.peek().getPositionMatrix(), f12, 0, -f12).texture(u_multi*2, 0).next();
            buffer.vertex(poseStack.peek().getPositionMatrix(), f12, 0, f12).texture(u_multi*2, v_multi).next();
            buffer.vertex(poseStack.peek().getPositionMatrix(), -f12, 0, f12).texture(u_multi, v_multi).next();
            poseStack.pop();
            poseStack.pop();
        }


        tesselator.draw();
        poseStack.pop();
    }
    private static void RenderFog(Tessellator tesselator, MatrixStack poseStack, Camera camera){
        BufferBuilder buffer = tesselator.getBuffer();
        poseStack.push();
        poseStack.translate(-camera.getPos().x, -camera.getPos().y,
                -camera.getPos().z);

        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, FOG_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();


        float rotation_offset_speed = 0.001f;
        mindsphererotationoffset += rotation_offset_speed;
        float distance = MindSphereClientData.get_currentData().get_fogdistance();

        float offset = 0.5f;
        int count = Math.max((int)(distance * Math.PI / 10),10);
        float true_distance = distance + 50;


        for (int i = 0; i < count; i++) {
            float fog_angle = (float) (((((2*Math.PI) / count) * i) + mindsphererotationoffset) % (Math.PI*2));
            Vec3d position = new Vec3d(Math.cos(fog_angle) * true_distance + offset,1,Math.sin(fog_angle) * true_distance + offset);
            fog_angle = (float) (-fog_angle + Math.PI / 2);
            RenderFogInstance(poseStack,buffer,position,fog_angle);
        }
        true_distance += 5;
        for (int i = 0; i < count; i++) {
            float fog_angle = (float) (((((2*Math.PI) / count) * i)) % (Math.PI*2));
            Vec3d position = new Vec3d(Math.cos(fog_angle) * true_distance + offset,1,Math.sin(fog_angle) * true_distance + offset);
            fog_angle = (float) (-fog_angle + Math.PI / 2);
            RenderFogInstance(poseStack,buffer,position,fog_angle);
        }




        tesselator.draw();
        poseStack.pop();


        RenderSystem.disableBlend();
        RenderSystem.enableCull();
    }
    private static void RenderFogInstance(MatrixStack poseStack, BufferBuilder buffer, Position position, float angle){
        poseStack.push();

        Matrix4f rotationmatrix = new Matrix4f();
        ((Matrix4fExtended)(Object)rotationmatrix).rotation(-angle, 0, 1, 0);
        poseStack.translate(position.getX(),position.getY(),position.getZ());
        poseStack.multiplyPositionMatrix(rotationmatrix);

        poseStack.scale(100,50,100);
        poseStack.translate(-.5,-.1,-.5);


        buffer.vertex(poseStack.peek().getPositionMatrix(),0,0,0).texture(0,1).color(255,255,255,255).normal(poseStack.peek().getNormalMatrix(), 1,1 ,1).next();
        buffer.vertex(poseStack.peek().getPositionMatrix(),0,1,0).texture(0,0).color(255,255,255,255).normal(poseStack.peek().getNormalMatrix(), 1,1 ,1).next();
        buffer.vertex(poseStack.peek().getPositionMatrix(),1,1,0).texture(1,0).color(255,255,255,255).normal(poseStack.peek().getNormalMatrix(), 1,1 ,1).next();
        buffer.vertex(poseStack.peek().getPositionMatrix(),1,0,0).texture(1,1).color(255,255,255,255).normal(poseStack.peek().getNormalMatrix(), 1,1 ,1).next();

        poseStack.pop();
    }





}
