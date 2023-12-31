package io.duckling.hexintrospection.mixin;

import io.duckling.hexintrospection.HexIntrospection;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionOptions.class)
public class LevelPropertiesMixin {


    @Inject(method = "createRegistry", at = @At("HEAD"), cancellable = false)
    private static void createRegistry(Registry<DimensionOptions> registry, CallbackInfoReturnable<Registry<DimensionOptions>> cir){
        registry.getEntrySet().forEach(e->{
            HexIntrospection.LOGGER.info(e.toString());
        });

    }

}
