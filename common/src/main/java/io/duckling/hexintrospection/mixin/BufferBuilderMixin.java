package io.duckling.hexintrospection.mixin;

import io.duckling.hexintrospection.DucklingBufferBuilderAccess;
import io.duckling.hexintrospection.HexIntrospection;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.ByteBuffer;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin implements DucklingBufferBuilderAccess {

    @Shadow private VertexFormat format;
    @Shadow private ByteBuffer buffer;
    @Shadow private int vertexCount;
    @Shadow private int elementOffset;


    @Shadow protected abstract void grow();

    @Shadow protected abstract void grow(int size);

    @Override
    public void putBulkData(ByteBuffer buffer) {
        grow(buffer.limit() + format.getVertexSizeByte());
        this.buffer.position(this.elementOffset);
        this.buffer.put(buffer);
        this.buffer.position(0);
        this.vertexCount += buffer.limit() / this.format.getVertexSizeByte();
        this.elementOffset += buffer.limit();
    }

    @Override
    public ByteBuffer cloneBytes() {
        int size = vertexCount * format.getVertexSizeByte();
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(size);
        ByteBuffer tempbuffer = buffer.duplicate();
        tempbuffer.rewind();
        tempbuffer.limit(size);
        buffer2.put(tempbuffer);
        buffer2.position(0);
        buffer2.order(buffer.order());
        return buffer2;
    }

    @Override
    public ByteBuffer poseData(MatrixStack poseStack, int packedLight) {
        ByteBuffer buffer2 = cloneBytes();
        int size = format.getVertexSizeByte();
        boolean flag = format.getElements().stream().anyMatch(e -> e.getUvIndex() == 2);
        for (int i = 0; i < vertexCount; i++) {
            int index = i * size;
            float f0 = buffer2.getFloat(index);
            float f1 = buffer2.getFloat(4 + index);
            float f2 = buffer2.getFloat(8 + index);
            Vector4f vector = new Vector4f(f0,f1,f2,1);
            vector.transform(poseStack.peek().getPositionMatrix());
            buffer2.putFloat(index, vector.getX());
            buffer2.putFloat(4 + index, vector.getY());
            buffer2.putFloat(8 + index, vector.getZ());
            if(flag){
                buffer2.putInt(24 + index, packedLight);
            }
        }


        return buffer2;
    }
}
