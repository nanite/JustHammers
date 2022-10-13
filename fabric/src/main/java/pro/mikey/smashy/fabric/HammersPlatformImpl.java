package pro.mikey.smashy.fabric;

import pro.mikey.smashy.HammersPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class HammersPlatformImpl {
    /**
     * This is our actual method to {@link HammersPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
