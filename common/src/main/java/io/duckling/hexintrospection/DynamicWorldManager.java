package io.duckling.hexintrospection;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Lifecycle;
import io.duckling.hexintrospection.mixin.MinecraftServerAccess;
import io.duckling.hexintrospection.network.DimensionalSyncPacket;
import net.minecraft.server.MinecraftServer;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.storage.LevelStorage;

import java.util.Optional;


public final class DynamicWorldManager {

    public static final RegistryKey<DimensionType> DEFAULT_DIM_TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(HexIntrospection.MOD_ID, "mindsphere_type"));


    private static DynamicWorldManager instance;

    public static DynamicWorldManager get(MinecraftServer server){

        if (instance == null || instance.server != server) {
            instance = new DynamicWorldManager(server);
        }

        return instance;
    }


    private final MinecraftServer server;
    private final MinecraftServerAccess serverAccess;
    DynamicWorldManager(MinecraftServer server){
        this.server = server;
        this.serverAccess = (MinecraftServerAccess) server;
    }

    public DynamicWorldHandle getOrOpenDynamicWorld(Identifier key){
        RegistryKey<World> worldKey = RegistryKey.of(Registry.WORLD_KEY, key);
        ServerWorld world = this.server.getWorld(worldKey);
        if(world == null){
            world = this.add(worldKey);
        }else{
            //TODO implement
        }
        return new DynamicWorldHandle(this, world);
    }
    DynamicWorld add(RegistryKey<World> registryKey) {
        RegistryEntry<DimensionType> key = server.getRegistryManager().get(Registry.DIMENSION_TYPE_KEY).getEntry(DEFAULT_DIM_TYPE).get();
        Registry<StructureSet> set = server.getRegistryManager().get(Registry.STRUCTURE_SET_KEY);
        Optional<RegistryEntry<Biome>> biome = server.getRegistryManager().get(Registry.BIOME_KEY).getEntry(RegistryKey.of(Registry.BIOME_KEY, Identifier.of("hexintrospection","mindspherebiome")));
        DimensionOptions options = new DimensionOptions(key, new DynamicWorldChunkGenerator(set,biome.get()));



        SimpleRegistry<DimensionOptions> dimensionsRegistry = getDimensionsRegistry(this.server);
        boolean isFrozen = ((RemoveFromRegistry<?>) dimensionsRegistry).duckling$isFrozen();
        ((RemoveFromRegistry<?>) dimensionsRegistry).duckling$setFrozen(false);
        dimensionsRegistry.add(RegistryKey.of(Registry.DIMENSION_KEY, registryKey.getValue()), options, Lifecycle.stable());
        ((RemoveFromRegistry<?>) dimensionsRegistry).duckling$setFrozen(isFrozen);



        DynamicWorld world = new DynamicWorld(this.server, registryKey, options);
        HexIntrospection.LOGGER.info(this.serverAccess.getWorlds().size());
        this.serverAccess.getWorlds().put(world.getRegistryKey(), world);
        HexIntrospection.LOGGER.info(this.serverAccess.getWorlds().size());
        onWorldLoad(server,world);
        world.tick(()-> true);

        DimensionalSyncPacket packet = new DimensionalSyncPacket(ImmutableSet.of(registryKey),ImmutableSet.of());
        packet.sendToPlayers(server.getPlayerManager().getPlayerList());

        return world;
    }


    private static void onWorldLoad(MinecraftServer server, DynamicWorld world) {
        IntrospectionAbstractions.onWorldLoad(server, world);
    }
    private static SimpleRegistry<DimensionOptions> getDimensionsRegistry(MinecraftServer server) {
        GeneratorOptions generatorOptions = server.getSaveProperties().getGeneratorOptions();
        return (SimpleRegistry<DimensionOptions>) generatorOptions.getDimensions();
    }

}
