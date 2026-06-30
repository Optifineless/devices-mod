package com.ultreon.devices.debug;

import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The DebugUtils class provides utility methods for debugging purposes.
 */
public class DebugUtils {
    public static void dump(DumpType type, ResourceLocation resource, DumpWriter dumpFunc) throws IOException {
        if (!Platform.isDevelopmentEnvironment()) return;

        Path outputFile = Platform.getGameFolder()
                .resolve("debug")
                .resolve("dump")
                .resolve(type.name().toLowerCase())
                .resolve(resource.getNamespace())
                .resolve(resource.getPath());
        Files.createDirectories(outputFile.getParent());
        try (OutputStream stream = Files.newOutputStream(outputFile)) {
            dumpFunc.dump(stream);
        }
    }

    /**
     * The DumpWriter interface is a functional interface
     * that represents a writer capable of dumping data to an OutputStream.
     */
    @FunctionalInterface
    public interface DumpWriter {
        void dump(OutputStream stream) throws IOException;
    }
}
