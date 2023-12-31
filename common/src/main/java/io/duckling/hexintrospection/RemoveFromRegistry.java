package io.duckling.hexintrospection;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

public interface RemoveFromRegistry<T> {

    @SuppressWarnings("unchecked")
    static <T> boolean remove(SimpleRegistry<T> registry, Identifier key) {
        return ((RemoveFromRegistry<T>) registry).duckling$remove(key);
    }

    @SuppressWarnings("unchecked")
    static <T> boolean remove(SimpleRegistry<T> registry, T value) {
        return ((RemoveFromRegistry<T>) registry).duckling$remove(value);
    }

    boolean duckling$remove(T value);

    boolean duckling$remove(Identifier key);

    void duckling$setFrozen(boolean value);

    boolean duckling$isFrozen();

}
