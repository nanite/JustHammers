package pro.mikey.justhammers.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
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

    public static int getBlockXpAmount(BlockPos pos, BlockState state, Level level, int fortuneLevel, int silkTouchLevel) {
        return state.getExpDrop(level, level.random, pos, fortuneLevel, silkTouchLevel);
    }
}
