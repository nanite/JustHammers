package pro.mikey.justhammers;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;

public class HammersPlatform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getBlockXpAmount(BlockPos pos, BlockState state, Level level, Entity entity, ItemStack tool) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean blockDropsEvent(ServerLevel level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, List<ItemEntity> drops, @Nullable Entity breaker, ItemStack tool) {
        throw new AssertionError();
    }
}
