package pro.mikey.justhammers.hammer;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import pro.mikey.justhammers.HammerItems;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static pro.mikey.justhammers.HammerTags.HAMMER_NO_SMASHY;

public class HammerItem extends PickaxeItem {
    private final int depth;
    private final int radius;
    private TagKey<Block> blocks;

    public HammerItem(Tier tier, int radius, int depth, int level) {
        super(tier, 1, -2.8f, HammerItems.DEFAULT_PROPERTIES.durability(computeDurability(tier, level)));

        this.blocks = BlockTags.MINEABLE_WITH_PICKAXE;
        this.depth = depth;
        this.radius = radius;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("justhammers.tooltip.size", this.radius, this.radius, this.depth).withStyle(ChatFormatting.GRAY));
    }

    private static int computeDurability(Tier tier, int level) {
        return ((tier.getUses() * 2) + (200 * level)) * level;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        if (itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1) {
            return -1f;
        }

        return super.getDestroySpeed(itemStack, blockState);
    }

    // Called on fabric
    @Override
    public boolean isCorrectToolForDrops(BlockState blockState) {
        return actualIsCorrectToolForDrops(blockState);
    }

    // Called on Forge
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return actualIsCorrectToolForDrops(state);
    }

    private boolean actualIsCorrectToolForDrops(BlockState state) {
        int i = this.getTier().getLevel();
        if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        } else if (i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        } else {
            return (i >= 1 || !state.is(BlockTags.NEEDS_STONE_TOOL)) && state.is(this.blocks);
        }
    }

    public void causeAoe(Level level, BlockPos pos, BlockState state, ItemStack hammer, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        if (level.isClientSide || state.getDestroySpeed(level, pos) == 0.0F) {
            return;
        }

        if (livingEntity.isCrouching()) {
            return;
        }

        HitResult pick = livingEntity.pick(20D, 0.0F, false);

        // Not possible?
        if (!(pick instanceof BlockHitResult blockHitResult)) {
            return;
        }

        this.findAndBreakNearBlocks(blockHitResult, pos, hammer, level, livingEntity);

        // If the hammer is within 5% of durability remaining, warn the player
        if (hammer.getDamageValue() >= hammer.getMaxDamage() * 0.95) {
            if (!hammer.getOrCreateTag().contains("has_been_warned")) {
                player.sendSystemMessage(Component.translatable("justhammers.tooltip.durability_warning").withStyle(ChatFormatting.RED), true);
                hammer.getOrCreateTag().putBoolean("has_been_warned", true);
            }
        } else {
            if (hammer.getOrCreateTag().contains("has_been_warned")) {
                hammer.getOrCreateTag().remove("has_been_warned");
            }
        }
    }

    public void findAndBreakNearBlocks(BlockHitResult pick, BlockPos blockPos, ItemStack hammerStack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        var size = (radius / 2);
        var offset = size - 1;

        Direction direction = pick.getDirection();
        var boundingBox = switch (direction) {
            case DOWN, UP -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - (direction == Direction.UP ? depth - 1 : 0), blockPos.getZ() - size, blockPos.getX() + size, blockPos.getY() + (direction == Direction.DOWN ? depth - 1 : 0), blockPos.getZ() + size);
            case NORTH, SOUTH -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - size + offset, blockPos.getZ() - (direction == Direction.SOUTH ? depth - 1 : 0), blockPos.getX() + size, blockPos.getY() + size + offset, blockPos.getZ() + (direction == Direction.NORTH ? depth - 1 : 0));
            case WEST, EAST -> new BoundingBox(blockPos.getX() - (direction == Direction.EAST ? depth - 1 : 0), blockPos.getY() - size + offset, blockPos.getZ() - size, blockPos.getX() + (direction == Direction.WEST ? depth - 1 : 0), blockPos.getY() + size + offset, blockPos.getZ() + size);
        };

        // If the hammer is about to break, Stop. We don't want to break the hammer
        if (hammerStack.getDamageValue() >= hammerStack.getMaxDamage() - 1) {
            return;
        }

        int damage = 0;
        Iterator<BlockPos> iterator = BlockPos.betweenClosedStream(boundingBox).iterator();
        Set<BlockPos> removedPos = new HashSet<>();
        while (iterator.hasNext()) {
            var pos = iterator.next();

            // Prevent the hammer from breaking if the damage is too high
            if ((hammerStack.getDamageValue() + (damage + 1)) >= hammerStack.getMaxDamage() - 1) {
                break;
            }

            BlockState targetState = level.getBlockState(pos);
            if (pos == blockPos || removedPos.contains(pos) || !canDestroy(targetState, level, pos)) {
                continue;
            }

            // Skips any blocks that require a higher tier hammer
            if (!actualIsCorrectToolForDrops(targetState)) {
                continue;
            }

            // Throw event out there and let mods block us breaking this block
            EventResult eventResult = BlockEvent.BREAK.invoker().breakBlock(level, pos, targetState, (ServerPlayer) livingEntity, null);
            if (eventResult.isFalse()) {
                continue;
            }

            removedPos.add(pos);
            level.destroyBlock(pos, false, livingEntity);
            if (!player.isCreative()) {
                boolean correctToolForDrops = hammerStack.isCorrectToolForDrops(targetState);
                if (correctToolForDrops) {
                    targetState.spawnAfterBreak((ServerLevel) level, pos, hammerStack, true);
                    List<ItemStack> drops = Block.getDrops(targetState, (ServerLevel) level, pos, level.getBlockEntity(pos), livingEntity, hammerStack);
                    drops.forEach(e -> Block.popResourceFromFace(level, pos, ((BlockHitResult) pick).getDirection(), e));
                }
            }

            damage ++;
        }

        if (damage != 0 && !player.isCreative()) {
            hammerStack.hurtAndBreak(damage, livingEntity, (livingEntityx) -> {
                livingEntityx.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
    }

    private boolean canDestroy(BlockState targetState, Level level, BlockPos pos) {
        if (targetState.getDestroySpeed(level, pos) <= 0) {
            return false;
        }

        if (targetState.is(HAMMER_NO_SMASHY)) {
            return false;
        }

        return level.getBlockEntity(pos) == null;
    }
}
