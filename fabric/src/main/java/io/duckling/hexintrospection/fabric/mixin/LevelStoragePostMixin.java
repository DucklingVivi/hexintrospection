package io.duckling.hexintrospection.fabric.mixin;



import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.fabric.DucklingDimensionFixer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = LevelStorage.class, priority = 1001)
public class LevelStoragePostMixin {


    @Inject(method = "readGeneratorProperties", at=@At("HEAD"))
    private static <T> void onReadGeneratorProperties(
            Dynamic<T> nbt, DataFixer dataFixer, int version,
            CallbackInfoReturnable<Pair<GeneratorOptions, Lifecycle>> cir
    ) {
        NbtElement nbtTag = ((Dynamic<NbtElement>) nbt).getValue();
        NbtCompound worldGenSettings = ((NbtCompound) nbtTag).getCompound("WorldGenSettings");
        NbtCompound dimensions = worldGenSettings.getCompound("dimensions");


        NbtCompound hexIntrospectionDims = DucklingDimensionFixer.getHexIntrospectionDims;
        hexIntrospectionDims.getKeys().forEach(k->{
            dimensions.put(k, hexIntrospectionDims.get(k));
        });
    }
}
