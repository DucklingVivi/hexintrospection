package io.duckling.hexintrospection;

import net.minecraft.world.dimension.DimensionOptions;

import java.util.function.Predicate;

public interface DucklingDimensionOptions {
    Predicate<DimensionOptions> SAVE_PREDICATE = (v) -> ((DucklingDimensionOptions) (Object) v).duckling$getSave();
    void duckling$setSave(boolean value);
    boolean duckling$getSave();
}
