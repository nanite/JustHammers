package pro.mikey.justhammers.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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

    public static int getBlockXpAmount(BlockPos pos, BlockState state, Level level, int fortuneLevel, int silkTouchLevel) {
        return -1;
    }
}
