package pro.mikey.justhammers.recipe;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.mikey.justhammers.HammerItem;
import pro.mikey.justhammers.config.SimpleJsonConfig;

/**
 * Allows a hammer to be repaired using the appropriate repair item.
 * <p>
 * I'm not sure if there would have been a better way of doing this.
 */
public class RepairRecipe extends CustomRecipe {
    public RepairRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput recipeInput) {
        var remainingItems = NonNullList.withSize(recipeInput.size(), ItemStack.EMPTY);

        var repairTargets = getRepairTargets(recipeInput);
        if (repairTargets == null) {
            return remainingItems;
        }

        var hammer = repairTargets.getFirst();
        var repairItem = repairTargets.getSecond();

        // How damaged is the hammer?
        var currentDamage = hammer.getDamageValue();

        // Repair is calculated as a percentage of the max damage. If the max damage is 1000 and the repair percentage is 33.33%, then 333 durability is restored.
        // How many items would it take to get the current damage to 0?
        boolean isNetheriteHammer = isRepairItem(hammer, new ItemStack(Items.NETHERITE_INGOT));
        var repairPercentage = isNetheriteHammer ? SimpleJsonConfig.INSTANCE.durabilityRepairPercentageNetherite.get().getAsDouble() : SimpleJsonConfig.INSTANCE.durabilityRepairPercentage.get().getAsDouble();
        var repairAmount = Math.floor((hammer.getMaxDamage() / 100D) * repairPercentage);

        // Figure out how many items are needed to repair the hammer
        var neededRepairItems = Math.max(1, (int) Math.floor((double) currentDamage / repairAmount));

        // Clamp the amount of items to the amount of items in the stack
        neededRepairItems = Math.min(neededRepairItems, repairItem.getCount());

        // Remove that amount of items from the stack used to repair the hammer
        repairItem.shrink(neededRepairItems - 1);

        // Return the remaining items
        return remainingItems;
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        return getRepairTargets(recipeInput) != null;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        var repairTargets = getRepairTargets(recipeInput);

        // This shouldn't be possible, but just in case
        if (repairTargets == null) {
            return ItemStack.EMPTY;
        }

        var hammer = repairTargets.getFirst();
        var repairItem = repairTargets.getSecond();

        var repairedHammer = hammer.copy();
        // Repair is calculated as a percentage of the max damage. This assumes that the max damage is 100% meaning that if the repair percentage is 33.33%, then 33.33% of the max damage is restored.
        boolean isNetheriteHammer = isRepairItem(hammer, new ItemStack(Items.NETHERITE_INGOT));
        var percentage = isNetheriteHammer ? SimpleJsonConfig.INSTANCE.durabilityRepairPercentageNetherite.get().getAsDouble() : SimpleJsonConfig.INSTANCE.durabilityRepairPercentage.get().getAsDouble();
        var repairAmount = Math.floor((hammer.getMaxDamage() / 100D) * percentage);

        var currentDamage = hammer.getDamageValue();

        // How many items would it take to get the current damage to 0 and how many items do we have access to
        var neededRepairItems = Math.max(1, (int) Math.floor((double) currentDamage / repairAmount));
        var availableRepairItems = repairItem.getCount();

        // Clamp the amount of items to the amount of items in the stack
        neededRepairItems = Math.min(neededRepairItems, availableRepairItems);

        // Take the needed repair items from the stack and times it by the repair amount to get the total repair amount
        var repairAmountTotal = neededRepairItems * repairAmount;
        var newDamage = Math.max(0, currentDamage - repairAmountTotal);
        repairedHammer.setDamageValue((int) newDamage);

        return repairedHammer;
    }

    @Override
    public @NotNull RecipeSerializer<RepairRecipe> getSerializer() {
        return HammerRecipes.REPAIR_RECIPE_SERIALIZER.get();
    }

    @Nullable
    private Pair<ItemStack, ItemStack> getRepairTargets(CraftingInput recipeInput) {
        if (recipeInput.ingredientCount() != 2) {
            return null;
        }

        // Find the hammer
        ItemStack hammer = null;

        for (int i = 0; i < recipeInput.size(); i++) {
            var stack = recipeInput.getItem(i);
            var item = stack.getItem();

            if (item instanceof HammerItem) {
                hammer = stack;
            }
        }

        if (hammer == null) {
            return null;
        }

        if (hammer.getDamageValue() == 0) {
            return null;
        }

        // Get the repair material
        Repairable repairable = hammer.get(DataComponents.REPAIRABLE);
        if (repairable == null) {
            return null;
        }

        ItemStack availableRepairItem = null;
        boolean tooManyItems = false;
        for (int i = 0; i < recipeInput.size(); i++) {
            var stack = recipeInput.getItem(i);
            var item = stack.getItem();

            if (item instanceof HammerItem) {
                continue;
            }

            if (repairable.isValidRepairItem(stack)) {
                if (availableRepairItem != null) {
                    tooManyItems = true;
                    break;
                }

                availableRepairItem = stack;
            }
        }

        if (availableRepairItem == null || tooManyItems) {
            return null;
        }

        return Pair.of(hammer, availableRepairItem);
    }

    private static boolean isRepairItem(ItemStack stack, ItemStack testStack) {
        Repairable repairable = stack.get(DataComponents.REPAIRABLE);
        if (repairable == null) {
            return false;
        }

        return repairable.isValidRepairItem(testStack);
    }
}
