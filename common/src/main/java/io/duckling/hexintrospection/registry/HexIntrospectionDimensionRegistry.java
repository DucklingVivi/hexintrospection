package io.duckling.hexintrospection.registry;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.registry.registries.options.RegistrarOption;
import io.duckling.hexintrospection.DynamicWorldChunkGenerator;
import io.duckling.hexintrospection.HexIntrospection;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.commons.codec.binary.Hex;

import java.util.function.Supplier;

public class HexIntrospectionDimensionRegistry {

    public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNKGENERATORS = DeferredRegister.create(HexIntrospection.MOD_ID, Registry.CHUNK_GENERATOR_KEY);

    public static final RegistrySupplier<Codec<DynamicWorldChunkGenerator>> EXAMPLE_ITEM = CHUNKGENERATORS.register("chunkgen", () -> DynamicWorldChunkGenerator.CODEC);

    public static void init(){
        CHUNKGENERATORS.register();
        //Registry.register(Registry.CHUNK_GENERATOR, new Identifier(HexIntrospection.MOD_ID,"chunkgen"), DynamicWorldChunkGenerator.CODEC)
    }


}
