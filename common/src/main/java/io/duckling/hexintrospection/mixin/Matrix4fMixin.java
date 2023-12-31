package io.duckling.hexintrospection.mixin;


import io.duckling.hexintrospection.Matrix4fExtended;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Matrix4f.class)
public abstract class Matrix4fMixin implements Matrix4fExtended
{
    @Shadow protected float a00;
    @Shadow protected float a01;
    @Shadow protected float a02;
    @Shadow protected float a03;
    @Shadow protected float a10;
    @Shadow protected float a11;
    @Shadow protected float a12;
    @Shadow protected float a13;
    @Shadow protected float a20;
    @Shadow protected float a21;
    @Shadow protected float a22;
    @Shadow protected float a23;
    @Shadow protected float a30;
    @Shadow protected float a31;
    @Shadow protected float a32;
    @Shadow protected float a33;


    @Override
    public void rotation(float a, float x, float y, float z) {
        Vec3f vector = new Vec3f(x,y,z);
        vector.normalize();
        float ux = vector.getX();
        float uy = vector.getY();
        float uz = vector.getZ();
        float cos = (float) Math.cos(a);
        float sin = (float) Math.sin(a);
        a00 = cos + ux * ux * (1 - cos);
        a01 = uy * ux * (1 - cos) + uz * sin;
        a02 = uz * ux * (1 - cos) - uy * sin;
        a03 = 0;
        a10 = ux * uy * (1 - cos) - uz * sin;
        a11 = cos + uy * uy * (1 - cos);
        a12 = uz * uy * (1 - cos) + ux * sin;
        a13 = 0;
        a20 = ux * uz * (1 - cos) + uy * sin;
        a21 = uy * uz * (1- cos) - ux * sin;
        a22 = cos + uz * uz* (1 - cos);
        a23 = 0;
        a30 = 0;
        a31 = 0;
        a32 = 0;
        a33 = 1;
    }
}
