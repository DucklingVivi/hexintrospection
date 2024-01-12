package io.duckling.hexintrospection;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;


public class MindSphereEyeData {
    public int tier = 0;

    public BlockPos position;

    public float currentmedia = 0f;



    public NbtCompound toNBT(){
        NbtCompound compound = new NbtCompound();
        compound.putInt("tier", tier);
        compound.putFloat("currentmedia",currentmedia);
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
        if(compound.contains("currentmedia")) {
            this.currentmedia = compound.getFloat("currentmedia");
        }
        if(this.currentmedia > getMaxMediaReserve()){
            this.currentmedia = getMaxMediaReserve();
        }
    }

    public float getMaxMediaReserve() {
        float value = 0f;
        value += Math.floor(Math.pow(value, 1.5f)) * 5;
        return value;
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
