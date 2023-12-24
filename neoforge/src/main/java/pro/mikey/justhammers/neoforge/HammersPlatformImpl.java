package pro.mikey.justhammers.neoforge;

import net.neoforged.fml.loading.FMLPaths;
import pro.mikey.justhammers.HammersPlatform;

import java.nio.file.Path;

public class HammersPlatformImpl {
    /**
     * This is our actual method to {@link HammersPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
