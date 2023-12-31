package io.duckling.hexintrospection.client;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class MindSphereClientEyeData {


    public BlockPos position = new BlockPos(0,0,0);
    public float eyeScale;
    private int _tier = 0;
    public int get_tier(){
        return _tier;
    }
    public void set_tier(int tier){
        this._tier = tier;
    }




    public static MindSphereClientEyeData fromNBT(NbtCompound compound){
        MindSphereClientEyeData data = new MindSphereClientEyeData();
        data.set_tier(compound.getInt("tier"));
        data.position = BlockPos.fromLong(compound.getLong("position"));
        return data;
    }

}
