package io.duckling.hexintrospection.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.duckling.hexintrospection.HexIntrospection;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class HexIntrospectionItemRegistry {
    // Register items through this
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(HexIntrospection.MOD_ID, Registry.ITEM_KEY);

    public static void init() {
        ITEMS.register();
    }

    // A new creative tab. Notice how it is one of the few things that are not deferred
    public static final ItemGroup DUMMY_GROUP = CreativeTabRegistry.create(HexIntrospection.id("dummy_group"), () -> new ItemStack(Items.AIR));

    // During the loading phase, refrain from accessing suppliers' items (e.g. EXAMPLE_ITEM.get()), they will not be available

    public static <T extends Item> RegistrySupplier<T> item(String name, Supplier<T> item) {
        return ITEMS.register(new Identifier(HexIntrospection.MOD_ID, name), item);
    }

    public static Item.Settings defaultSettings(){
        return new Item.Settings().group(DUMMY_GROUP);
    }

}
