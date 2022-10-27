package pro.mikey.justhammers.fabric;

import pro.mikey.justhammers.HammersPlatform;
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
