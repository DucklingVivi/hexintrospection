package io.duckling.hexintrospection;

import at.petrak.hexcasting.api.spell.math.HexPattern;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.*;

public class EyeInfusion {
    public static final EyeInfusion NONE = getNone();

    public boolean empty = false;

    public static EyeInfusion fromNBT(NbtCompound infusioncompound) {
        EyeInfusion retInfusion = new EyeInfusion();
        NbtList patternList = infusioncompound.getList("patterns", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < patternList.size(); i++) {
            NbtCompound patternCompound = patternList.getCompound(i);
            HexPattern pattern = HexPattern.fromNBT(patternCompound);
            retInfusion.patterns.add(pattern);
        }
        return retInfusion;
    }

    public NbtCompound toNBT(){
        NbtCompound compound = new NbtCompound();
        NbtList patternList = new NbtList();

        for(HexPattern pattern : patterns){
            patternList.add(pattern.serializeToNBT());
        }
        compound.put("patterns", patternList);


        return compound;
    }

    public boolean isNone(){
        return empty;
    }

    public List<HexPattern> patterns;




    public EyeInfusion(){
        patterns = new ArrayList<>();
    }
    private static EyeInfusion getNone(){
        EyeInfusion infusion = new EyeInfusion();
        infusion.empty = true;
        return infusion;
    }
}
