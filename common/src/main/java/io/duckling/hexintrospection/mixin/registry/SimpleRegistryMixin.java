package io.duckling.hexintrospection.mixin.registry;

import com.mojang.serialization.Lifecycle;
import io.duckling.hexintrospection.RemoveFromRegistry;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;


@Mixin(SimpleRegistry.class)
public abstract class SimpleRegistryMixin<T> implements RemoveFromRegistry<T> {

    @Shadow @Final private Map<T, RegistryEntry.Reference<T>> valueToEntry;

    @Shadow @Final private Map<Identifier, RegistryEntry.Reference<T>> idToEntry;

    @Shadow @Final private Map<RegistryKey<T>, RegistryEntry.Reference<T>> keyToEntry;

    @Shadow @Final private Map<T, Lifecycle> entryToLifecycle;

    @Shadow @Final private ObjectList<RegistryEntry.Reference<T>> rawIdToEntry;

    @Shadow @Final private Object2IntMap<T> entryToRawId;
    @Shadow private boolean frozen;

    @Shadow @Nullable private List<RegistryEntry.Reference<T>> cachedEntries;

    @Override
    public boolean duckling$remove(T entry) {
        var registryEntry = this.valueToEntry.get(entry);
        int rawId = this.entryToRawId.removeInt(entry);
        if (rawId == -1) {
            return false;
        }

        try {
            this.rawIdToEntry.set(rawId, null);
            this.idToEntry.remove(registryEntry.registryKey().getValue());
            this.keyToEntry.remove(registryEntry.registryKey());
            this.entryToLifecycle.remove(entry);
            this.valueToEntry.remove(entry);
            if (this.cachedEntries != null) {
                this.cachedEntries.remove(registryEntry);
            }

            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean duckling$remove(Identifier key) {
        var entry = this.idToEntry.get(key);
        return entry != null && entry.hasKeyAndValue() && this.duckling$remove(entry.value());
    }

    @Override
    public void duckling$setFrozen(boolean value) {
        this.frozen = false;
    }

    @Override
    public boolean duckling$isFrozen() {
        return this.frozen;
    }
}
