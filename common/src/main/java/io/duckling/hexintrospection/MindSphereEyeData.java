package io.duckling.hexintrospection;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class MindSphereEyeData {
    private int tier = 0;

    public BlockPos position;




    //tonbt
    public NbtCompound toNBT(){
        NbtCompound compound = new NbtCompound();
        compound.putInt("tier", tier);
        return compound;
    }
    public NbtCompound toClientNBT(){
        NbtCompound compound = new NbtCompound();
        compound.putInt("tier", tier);
        compound.putLong("position",position.asLong());
        return compound;
    }
    //fromnbt

    public void updateFromNBT(NbtCompound compound){
        this.tier = compound.getInt("tier");
    }


    public enum eyeLocation {
        CENTER,
        NORTH_EAST,
        EAST,
        SOUTH_EAST,
        SOUTH_WEST,
        WEST,
        NORTH_WEST
    }
}
