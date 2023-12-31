package io.duckling.hexintrospection;

import io.duckling.hexintrospection.network.MindSpherePacket;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class MindSphereData {
    public float fogdistance = 20f;

    public Map<Integer, MindSphereEyeData> eyeData = new HashMap<>();


    public MindSphereData(){
        MindSphereEyeData data = new MindSphereEyeData();
        data.position = new BlockPos(0,50,0);

        int radius = 49;
        int index = 0;
        eyeData.put(index++, data);
        float angle = 0;
        for (int i = 0; i < 6; i++) {
            MindSphereEyeData newdata = new MindSphereEyeData();
            int x = (int)(Math.cos(angle) * radius);
            int z = (int)(Math.sin(angle) * radius);
            angle = (float) (angle + Math.PI / 3.0);
            newdata.position = new BlockPos(x, 50, z);
            eyeData.put(index++, newdata);
        }

    }

    public MindSpherePacket addEyeData(MindSphereEyeData data){
        int id = 0;
        while (eyeData.containsKey(id)){
            id++;
        }
        eyeData.put(id,data);
        return MindSpherePacket.addEye(id,data);
    }

    public NbtCompound toNBT(){
        NbtCompound compound = new NbtCompound();
        compound.putFloat("fogdistance", fogdistance);
        NbtCompound eyeData = new NbtCompound();
        for (Map.Entry<Integer, MindSphereEyeData> entry : this.eyeData.entrySet()){
            eyeData.put(String.valueOf(entry.getKey()), entry.getValue().toNBT());
        }
        compound.put("eyeData", eyeData);
        return compound;
    }
    public NbtCompound toClientNBT(){
        NbtCompound compound = new NbtCompound();
        compound.putFloat("fogdistance", fogdistance);
        NbtCompound eyeData = new NbtCompound();
        for (Map.Entry<Integer, MindSphereEyeData> entry : this.eyeData.entrySet()){
            eyeData.put(String.valueOf(entry.getKey()), entry.getValue().toClientNBT());
        }
        compound.put("eyeData", eyeData);
        return compound;
    }
    public static MindSphereData fromNBT(NbtCompound compound){
        MindSphereData data = new MindSphereData();
        data.fogdistance = compound.getFloat("fogdistance");
        NbtCompound eyeData = compound.getCompound("eyeData");
        for (String key : eyeData.getKeys()){
            data.eyeData.get(Integer.parseInt(key)).updateFromNBT(eyeData.getCompound(key));
        }
        return data;
    }

}
