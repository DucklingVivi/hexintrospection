package io.duckling.hexintrospection;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;

public class IntrospectionAbstractions {
    /**
     * This explanation is mostly from Architectury's template project.
     * One thing I would like to add for those familiar with Hex Casting's layout:
     * this is a somewhat simpler and more intuitive (imo) way to accomplish what they accomplish with Xplat abstractions.
     * <p>
     * We can use {@link Platform#getConfigFolder()} but this is just an example of {@link ExpectPlatform}.
     * <p>
     * This must be a <b>public static</b> method. The platform-implemented solution must be placed under a
     * platform sub-package, with its class suffixed with {@code Impl}.
     * <p>
     * Example:
     * <p>
     * Expect: io.duckling.hexintrospection.IntrospectionAbstractions#getConfigDirectory()
     * <p>
     * Actual Fabric: net.hexdummy.fabric.DummyAbstractionsImpl#getConfigDirectory()
     * <p>
     * Actual Forge: net.hexdummy.forge.DummyAbstractionsImpl#getConfigDirectory()
     * <p>
     * <a href="https://plugins.jetbrains.com/plugin/16210-architectury">You should also get the IntelliJ plugin to help with @ExpectPlatform.</a>
     */
    @ExpectPlatform
    public static void onWorldLoad(MinecraftServer server, DynamicWorld world){
        throw new AssertionError();
    }
}
