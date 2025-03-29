package pro.mikey.justhammers.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import org.jetbrains.annotations.Nullable;
import pro.mikey.justhammers.HammersPlatform;

import java.nio.file.Path;
import java.util.List;

public class HammersPlatformImpl {
    /**
     * This is our actual method to {@link HammersPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static boolean blockDropsEvent(ServerLevel level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, List<ItemEntity> drops, @Nullable Entity breaker, ItemStack tool) {
        return NeoForge.EVENT_BUS.post(new BlockDropsEvent(level, pos, state, blockEntity, drops, breaker, tool)).isCanceled();
    }

    public static int getBlockXpAmount(BlockPos pos, BlockState state, Level level, Entity entity, ItemStack tool) {
        return state.getExpDrop(level, pos, level.getBlockEntity(pos), entity, tool);
    }
}
