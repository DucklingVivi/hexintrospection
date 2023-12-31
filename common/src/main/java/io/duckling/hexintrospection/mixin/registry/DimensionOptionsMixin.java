package io.duckling.hexintrospection.mixin.registry;

import io.duckling.hexintrospection.DucklingDimensionOptions;
import net.minecraft.world.dimension.DimensionOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DimensionOptions.class)
public class DimensionOptionsMixin implements DucklingDimensionOptions {

    @Unique
    private boolean duckling$save = true;
    @Override
    public void duckling$setSave(boolean value) {
        this.duckling$save = value;
    }

    @Override
    public boolean duckling$getSave() {
        return this.duckling$save;
    }
}
