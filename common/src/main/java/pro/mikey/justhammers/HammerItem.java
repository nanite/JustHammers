package pro.mikey.justhammers;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.mikey.justhammers.config.SimpleJsonConfig;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static pro.mikey.justhammers.HammerTags.HAMMER_NO_SMASHY;

public class HammerItem extends PickaxeItem {
    private final int depth;
    private final int radius;

    public HammerItem(Tier tier, int radius, int depth, int level) {
        super(new WrappedTier(tier, computeDurability(tier, level)),
                new Item.Properties().arch$tab(Hammers.TAB)
                        .durability(computeDurability(tier, level))
                        .attributes(PickaxeItem.createAttributes(tier, 1, -2.8f))
                        .component(DataComponents.TOOL, tier.createToolProperties(BlockTags.MINEABLE_WITH_PICKAXE))
        );

        this.depth = depth;
        this.radius = radius;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("justhammers.tooltip.size", this.radius, this.radius, this.depth).withStyle(ChatFormatting.GRAY));

        if (SimpleJsonConfig.INSTANCE.disabledDurabilityTooltip.get().getAsBoolean()) {
            return;
        }

        int damage = Math.max(0, itemStack.getDamageValue());
        int maxDamage = itemStack.getMaxDamage();
        int durabilityPercentage = (int) (((float) (maxDamage - damage) / (float) maxDamage) * 100);

        // Start the color at green and go to red as the durability decreases
        var color = ChatFormatting.GREEN;
        if (durabilityPercentage <= 50) {
            if (durabilityPercentage <= 25) {
                color = ChatFormatting.RED;
            } else {
                color = ChatFormatting.YELLOW;
            }
        }

        int remaining = Math.max(0, durabilityPercentage / 20);
        var percentComponent = Component.literal(prettyDurability(damage) + "/" + prettyDurability(maxDamage) + " ")
                .append(Component.literal("*".repeat(remaining)).withStyle(color))
                .append(Component.literal("*".repeat(5 - remaining)).withStyle(ChatFormatting.GRAY))
                .append(Component.literal(" (" + durabilityPercentage + "%)").withStyle(ChatFormatting.GRAY));

        list.add(percentComponent);
    }

    private static String prettyDurability(int durability) {
        String[] units = {"", "k", "m"};
        double displayDurability = durability;

        int unitIndex = durability > 0 ? (int) (Math.log10(durability) / 3) : 0;
        if (unitIndex >= units.length) {
            unitIndex = units.length - 1;
        }

        displayDurability /= Math.pow(1000, unitIndex);

        var output = String.format("%.2f", displayDurability);
        // Remove trailing .00
        if (output.endsWith(".00")) {
            output = output.substring(0, output.length() - 3);
        }

        return output + units[unitIndex];
    }

    private static int computeDurability(Tier tier, int level) {
        var baseModified = 0;
        if (level > 1) {
            // If we're above level 1 then the durability should be AT LEAST the durability of the netherite hammer
            baseModified = Tiers.NETHERITE.getUses();
        }
        return baseModified + ((int) (tier.getUses() * 2.5F) + (200 * level)) * level;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        if (itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1) {
            return -1f;
        }

        return super.getDestroySpeed(itemStack, blockState);
    }

    public void causeAoe(Level level, BlockPos pos, BlockState state, ItemStack hammer, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer)) return;

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
    }

    public void findAndBreakNearBlocks(BlockHitResult pick, BlockPos blockPos, ItemStack hammerStack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer player)) return;

        Direction direction = pick.getDirection();
        var boundingBox = getAreaOfEffect(blockPos, direction, radius, depth);

        // If the hammer is about to break, Stop. We don't want to break the hammer
        if (!player.isCreative() && (hammerStack.getDamageValue() >= hammerStack.getMaxDamage() - 1)) {
            return;
        }

        int damage = 0;
        Iterator<BlockPos> iterator = BlockPos.betweenClosedStream(boundingBox).iterator();
        Set<BlockPos> removedPos = new HashSet<>();
        while (iterator.hasNext()) {
            var pos = iterator.next();

            // Don't mess with the originally broken block
            if (pick.getBlockPos().equals(pos)) {
                continue;
            }

            // Prevent the hammer from breaking if the damage is too high
            if (!player.isCreative() && (hammerStack.getDamageValue() + (damage + 1)) >= hammerStack.getMaxDamage() - 1) {
                break;
            }

            BlockState targetState = level.getBlockState(pos);
            if (pos == blockPos || removedPos.contains(pos) || !canDestroy(targetState, level, pos)) {
                continue;
            }

            // Skips any blocks that require a higher tier hammer
            var incorrectBlocksForDrops = this.getTier().getIncorrectBlocksForDrops();
            if (targetState.is(incorrectBlocksForDrops)) {
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
                boolean correctToolForDrops = player.hasCorrectToolForDrops(targetState);
                if (correctToolForDrops) {
                    targetState.spawnAfterBreak((ServerLevel) level, pos, hammerStack, true);
                    List<ItemStack> drops = Block.getDrops(targetState, (ServerLevel) level, pos, level.getBlockEntity(pos), livingEntity, hammerStack);
                    drops.forEach(e -> Block.popResourceFromFace(level, pos, pick.getDirection(), e));
                }
            }

            damage ++;
        }

        if (damage != 0 && !player.isCreative()) {
            hammerStack.hurtAndBreak(damage, livingEntity, EquipmentSlot.MAINHAND);
        }
    }

    public static BoundingBox getAreaOfEffect(BlockPos blockPos, Direction direction, int radius, int depth) {
        var size = (radius / 2);
        var offset = size - 1;

        return switch (direction) {
            case DOWN, UP -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - (direction == Direction.UP ? depth - 1 : 0), blockPos.getZ() - size, blockPos.getX() + size, blockPos.getY() + (direction == Direction.DOWN ? depth - 1 : 0), blockPos.getZ() + size);
            case NORTH, SOUTH -> new BoundingBox(blockPos.getX() - size, blockPos.getY() - size + offset, blockPos.getZ() - (direction == Direction.SOUTH ? depth - 1 : 0), blockPos.getX() + size, blockPos.getY() + size + offset, blockPos.getZ() + (direction == Direction.NORTH ? depth - 1 : 0));
            case WEST, EAST -> new BoundingBox(blockPos.getX() - (direction == Direction.EAST ? depth - 1 : 0), blockPos.getY() - size + offset, blockPos.getZ() - size, blockPos.getX() + (direction == Direction.WEST ? depth - 1 : 0), blockPos.getY() + size + offset, blockPos.getZ() + size);
        };
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

    public int getDepth() {
        return depth;
    }

    public int getRadius() {
        return radius;
    }

    private record WrappedTier(
            Tier tier,
            int durability
    ) implements Tier {
        @Override
        public int getUses() {
            return durability;
        }

        @Override
        public float getSpeed() {
            return tier.getSpeed();
        }

        @Override
        public float getAttackDamageBonus() {
            return tier.getAttackDamageBonus();
        }

        @Override
        public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
            return tier.getIncorrectBlocksForDrops();
        }

        @Override
        public int getEnchantmentValue() {
            return tier.getEnchantmentValue();
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return tier.getRepairIngredient();
        }
    }
}
