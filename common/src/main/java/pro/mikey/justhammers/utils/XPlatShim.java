package pro.mikey.justhammers.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;

public interface XPlatShim {
    Path configDir();

    boolean fireBlockDropsEvent(ServerLevel level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, List<ItemEntity> drops, @Nullable Entity breaker, ItemStack tool);

    int getBlockXpAmount(BlockPos pos, BlockState state, Level level, Entity entity, ItemStack tool);

    boolean fireBlockBrokenEvent(ServerLevel level, BlockPos pos, BlockState state, Player player);
}
