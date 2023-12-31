package io.duckling.hexintrospection.fabric.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.fabric.DucklingDimensionFixer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(value = LevelStorage.class, priority = 999)
public class LevelStoragePreMixin {


    //THIS IS SO HACKY
    @Inject(method = "readGeneratorProperties", at=@At("HEAD"))
    private static <T> void onReadGeneratorProperties(
            Dynamic<T> nbt, DataFixer dataFixer, int version,
            CallbackInfoReturnable<Pair<GeneratorOptions, Lifecycle>> cir
    ) {
        NbtElement nbtTag = ((Dynamic<NbtElement>) nbt).getValue();
        NbtCompound worldGenSettings = ((NbtCompound) nbtTag).getCompound("WorldGenSettings");
        NbtCompound dimensions = worldGenSettings.getCompound("dimensions");
        NbtCompound hexintrospectionDims = new NbtCompound();
        dimensions.getKeys().forEach(k ->{
            Identifier i = Identifier.tryParse(k);
            if(i != null){
                if(Objects.equals(i.getNamespace(), HexIntrospection.MOD_ID)){
                    hexintrospectionDims.put(k, dimensions.get(k));
                }
            }
        });
        DucklingDimensionFixer.getHexIntrospectionDims = hexintrospectionDims;
    }


}
