package pro.mikey.justhammers;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.nio.file.Path;

public class HammersPlatform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getBlockXpAmount(BlockPos pos, BlockState state, Level level, int fortuneLevel, int silkTouchLevel) {
        throw new AssertionError();
    }
}
