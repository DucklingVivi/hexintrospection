package io.duckling.hexintrospection.forge;

import dev.architectury.platform.forge.EventBuses;
import io.duckling.hexintrospection.HexIntrospection;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(HexIntrospection.MOD_ID)
public class HexIntrospectionForge {
    public HexIntrospectionForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(HexIntrospection.MOD_ID, bus);
        bus.addListener(HexIntrospectionClientForge::init);
        HexIntrospection.init();
    }
}
