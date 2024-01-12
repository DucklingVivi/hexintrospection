package io.duckling.hexintrospection;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.util.math.MatrixStack;

import java.nio.ByteBuffer;

public interface DucklingBufferBuilderAccess {

    public void putBulkData(ByteBuffer buffer);

    public ByteBuffer cloneBytes();

    public ByteBuffer poseData(MatrixStack poseStack, int packedLight);
}
