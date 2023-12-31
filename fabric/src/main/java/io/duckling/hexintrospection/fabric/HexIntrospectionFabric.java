package io.duckling.hexintrospection.fabric;

import io.duckling.hexintrospection.HexIntrospection;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.WorldEvents;

/**
 * This is your loading entrypoint on fabric(-likes), in case you need to initialize
 * something platform-specific.
 * <br/>
 * Since quilt can load fabric mods, you develop for two platforms in one fell swoop.
 * Feel free to check out the <a href="https://github.com/architectury/architectury-templates">Architectury templates</a>
 * if you want to see how to add quilt-specific code.
 */
public class HexIntrospectionFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HexIntrospection.init();
    }
}
