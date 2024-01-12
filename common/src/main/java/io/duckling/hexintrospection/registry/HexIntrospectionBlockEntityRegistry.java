package io.duckling.hexintrospection.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.blocks.BlockEntityEyeInfuse;
import io.duckling.hexintrospection.blocks.BlockEyeInfuse;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class HexIntrospectionBlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(HexIntrospection.MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY);

    public static void init() {
        BLOCK_ENTITIES.register();
    }

    // A new creative tab. Notice how it is one of the few things that are not deferred

    // During the loading phase, refrain from accessing suppliers' items (e.g. EXAMPLE_ITEM.get()), they will not be available
    public static final RegistrySupplier<BlockEntityType<BlockEntityEyeInfuse>> EYE_INFUSE = BLOCK_ENTITIES.register("eye_infuse", () -> BlockEntityType.Builder.create(BlockEntityEyeInfuse::new, HexIntrospectionBlockRegistry.EYE_INFUSE.get()).build(null));

}
