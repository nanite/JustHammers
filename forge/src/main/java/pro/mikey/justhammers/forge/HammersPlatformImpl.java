package pro.mikey.justhammers.forge;

import pro.mikey.justhammers.HammersPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class HammersPlatformImpl {
    /**
     * This is our actual method to {@link HammersPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
