package io.duckling.hexintrospection.client;

import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import at.petrak.hexcasting.client.RenderLib;
import com.mojang.blaze3d.systems.RenderSystem;
import io.duckling.hexintrospection.DucklingBufferBuilderAccess;
import io.duckling.hexintrospection.EyeInfusion;
import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.Matrix4fExtended;
import io.duckling.hexintrospection.blocks.BlockEntityEyeInfuse;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;

import java.nio.ByteBuffer;

public class BlockEntityEyeInfuseRenderer implements BlockEntityRenderer<BlockEntityEyeInfuse> {

    public BlockEntityEyeInfuseRenderer(BlockEntityRendererFactory.Context ctx) {

    }
    @Override
    public void render(BlockEntityEyeInfuse entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        EyeInfusion infusion = entity.getInfusion();

        var oldShader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableDepthTest();
        matrices.push();

        HexPattern pattern = HexPattern.fromAngles("wqqqqq", HexDir.NORTH_EAST);


        matrices.translate(0,1,0);
        matrices.translate(0.5,0.5,0.5);
        matrices.scale(0.05f,0.05f,0.05f);

        Vec3f eye = new Vec3f(MinecraftClient.getInstance().getCameraEntity().getCameraPosVec(tickDelta));
        Vec3f at = new Vec3f(Vec3d.ofCenter(entity.getPos()));

        Vec2f a = new Vec2f(eye.getX()-at.getX(), eye.getZ()-at.getZ());
        Vec2f b = new Vec2f(0,1);
        float angle = (float) (Math.atan2(b.y, b.x) - Math.atan2(a.y, a.x));


        Matrix4f matrix4f = new Matrix4f();
        ((Matrix4fExtended)(Object) matrix4f).duckling$rotation(-angle,0,1,0);
        matrices.multiplyPositionMatrix(matrix4f);








        RenderPattern(pattern, entity, matrices, 1f);

        matrices.pop();

        RenderSystem.setShader(()-> oldShader);

    }

    private void RenderPattern(HexPattern pattern, BlockEntityEyeInfuse entity, MatrixStack matrices, float alpha){
        var com1 = pattern.getCenter(1);
        var lines1 = pattern.toLines(1, Vec2f.ZERO);

        var maxDx = -1f;
        var maxDy = -1f;
        for (var dot : lines1) {
            var dx = Math.abs(dot.x - com1.x);
            if (dx > maxDx) {
                maxDx = dx;
            }
            var dy = Math.abs(dot.y - com1.y);
            if (dy > maxDy) {
                maxDy = dy;
            }
        }
        var scale = Math.min(3.8f, Math.min(16 / 2.5f / maxDx, 16 / 2.5f / maxDy));

        var com2 = pattern.getCenter(scale);
        var lines2 = pattern.toLines(scale, com2.negate());
        // For some reason it is mirrored left to right and i can't seem to posestack-fu it into shape
        for (int i = 0; i < lines2.size(); i++) {
            var v = lines2.get(i);
            lines2.set(i, new Vec2f(-v.x, v.y));
        }
        var stupidHash = entity.getPos().hashCode();
        //var zappy = RenderLib.makeZappy(lines2, RenderLib.findDupIndices(pattern.positions()),
        //        10, 2.5f, 0.1f, 0.2f, 0f, 1f, stupidHash);

        int outer = 0xff_64c8ff;

        outer = (outer & 0x00ffffff) | (((int)(0xff*alpha)) << 24);

        int inner = RenderLib.screenCol(outer);

        RenderLib.drawLineSeq(matrices.peek().getPositionMatrix(), lines2, 1f, 0f, outer, outer);
        RenderLib.drawLineSeq(matrices.peek().getPositionMatrix(), lines2, 0.4f, 0.01f, inner, inner);
    }

    @Override
    public boolean rendersOutsideBoundingBox(BlockEntityEyeInfuse blockEntity) {
        return true;
    }

    @Override
    public int getRenderDistance() {
        return BlockEntityRenderer.super.getRenderDistance();
    }

    @Override
    public boolean isInRenderDistance(BlockEntityEyeInfuse blockEntity, Vec3d pos) {
        return BlockEntityRenderer.super.isInRenderDistance(blockEntity, pos);
    }





}
