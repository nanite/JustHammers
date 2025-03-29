package pro.mikey.justhammers.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import pro.mikey.justhammers.HammersPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.List;

public class HammersPlatformImpl {
    /**
     * This is our actual method to {@link HammersPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static int getBlockXpAmount(BlockPos pos, BlockState state, Level level, Entity entity, ItemStack tool) {
        return -1;
    }

    public static boolean blockDropsEvent(ServerLevel level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, List<ItemEntity> drops, @Nullable Entity breaker, ItemStack tool) {
        return false;
    }
}
