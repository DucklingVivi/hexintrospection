package io.duckling.hexintrospection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HexIntrospectionSaveManager extends PersistentState {
    static final String FILE_HEXINTROSPECTION_SAVE = "hexintrospection_save";


    public Map<UUID, MindSphereData> mindSphereData = new HashMap<>();

    public MindSphereData getMindSphereData(UUID uuid){
        if (!mindSphereData.containsKey(uuid)){
            mindSphereData.put(uuid, new MindSphereData());
        }
        return mindSphereData.get(uuid);

    }
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound mindSphereData = new NbtCompound();
        for (Map.Entry<UUID, MindSphereData> entry : this.mindSphereData.entrySet()){
            mindSphereData.put(entry.getKey().toString(), entry.getValue().toNBT());
        }
        nbt.put("mindSphereData", mindSphereData);
        return nbt;

    }
    public static HexIntrospectionSaveManager createFromNbt(NbtCompound tag) {
        HexIntrospectionSaveManager state = new HexIntrospectionSaveManager();
        NbtCompound mindSphereData = tag.getCompound("mindSphereData");
        for (String key : mindSphereData.getKeys()){

            MindSphereData data = MindSphereData.fromNBT(mindSphereData.getCompound(key));
            state.mindSphereData.put(UUID.fromString(key), data);
        }
        return state;
    }

    public static HexIntrospectionSaveManager get(MinecraftServer server){
        ServerWorld world = server.getOverworld();
        if(world == null){
            return null;
        }
        PersistentStateManager persistentStateManager = world.getPersistentStateManager();
        HexIntrospectionSaveManager state = persistentStateManager.getOrCreate(HexIntrospectionSaveManager::createFromNbt, HexIntrospectionSaveManager::new, FILE_HEXINTROSPECTION_SAVE);
        state.markDirty();

        return state;
    }
}
