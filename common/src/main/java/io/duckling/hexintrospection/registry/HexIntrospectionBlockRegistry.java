package io.duckling.hexintrospection.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.blocks.BlockEyeInfuse;
import io.duckling.hexintrospection.blocks.BlockItemEyeInfuse;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class HexIntrospectionBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(HexIntrospection.MOD_ID, Registry.BLOCK_KEY);

    public static void init() {
        BLOCKS.register();
    }

    // A new creative tab. Notice how it is one of the few things that are not deferred

    // During the loading phase, refrain from accessing suppliers' items (e.g. EXAMPLE_ITEM.get()), they will not be available
    public static final RegistrySupplier<Block> EYE_INFUSE = eyeInfuseBlock("eye_infuse", () -> new BlockEyeInfuse(AbstractBlock.Settings.of(Material.AMETHYST, MapColor.PALE_PURPLE).strength(0.0f,50f).nonOpaque().luminance((f)->15)));

    public static Block.Settings defaultSettings(){
        return Block.Settings.of(Material.STONE).hardness((float)1.3);
    }
    public static <T extends Block> RegistrySupplier<T> block(String name, Supplier<T> block) {
        return block(name, block, HexIntrospectionItemRegistry.defaultSettings());
    }

    public static <T extends Block> RegistrySupplier<T> block(String name, Supplier<T> block, Item.Settings settings) {
        RegistrySupplier<T> blockRegistered = blockNoItem(name, block);
        HexIntrospectionItemRegistry.item(name, () -> new BlockItem(blockRegistered.get(), settings));
        return blockRegistered;
    }

    public static <T extends Block> RegistrySupplier<T> eyeInfuseBlock(String name, Supplier<T> block){
        RegistrySupplier<T> blockRegistered = blockNoItem(name, block);
        HexIntrospectionItemRegistry.item(name, () -> new BlockItemEyeInfuse(blockRegistered.get(),  HexIntrospectionItemRegistry.defaultSettings()));
        return blockRegistered;
    }
    public static <T extends Block> RegistrySupplier<T> blockNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(new Identifier(HexIntrospection.MOD_ID, name), block);
    }



}
