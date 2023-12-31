package io.duckling.hexintrospection;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import io.duckling.hexintrospection.mixin.DimensionEffectsMixin;
import io.duckling.hexintrospection.network.Packets;
import io.duckling.hexintrospection.registry.HexIntrospectionDimensionRegistry;
import io.duckling.hexintrospection.registry.HexIntrospectionIotaTypeRegistry;
import io.duckling.hexintrospection.registry.HexIntrospectionItemRegistry;
import io.duckling.hexintrospection.registry.HexIntrospectionPatternRegistry;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is effectively the loading entrypoint for most of your code, at least
 * if you are using Architectury as intended.
 */
public class HexIntrospection {




    public static final String MOD_ID = "hexintrospection";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        LOGGER.info("Hex Dummy says hello!");

        HexIntrospectionItemRegistry.init();
        HexIntrospectionIotaTypeRegistry.init();
        HexIntrospectionPatternRegistry.init();
        HexIntrospectionDimensionRegistry.init();
        HexIntrospectionEvents.init();
        Packets.init();
        HexIntrospectionClient.init();
        

    }
    /**
     * Shortcut for identifiers specific to this mod.
     */
    public static Identifier id(String string) {
        return new Identifier(MOD_ID, string);
    }
}
