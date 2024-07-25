package pro.mikey.justhammers.recipe;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
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

        // How many items would it take to get the current damage to 0?
        var neededRepairItems = (int) Math.ceil((double) currentDamage / SimpleJsonConfig.INSTANCE.durabilityPerRepairItem.get().getAsInt());

        // Clamp the amount of items to the amount of items in the stack
        neededRepairItems = Math.min(neededRepairItems, repairItem.getCount());

        // Remove that amount of items from the stack used to repair the hammer
        repairItem.shrink(neededRepairItems);

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
        var repairAmount = SimpleJsonConfig.INSTANCE.durabilityPerRepairItem.get().getAsInt() * repairItem.getCount();

        var currentDamage = hammer.getDamageValue();

        var newDamage = Math.min(Math.max(0, currentDamage - repairAmount), hammer.getMaxDamage());
        repairedHammer.setDamageValue(newDamage);

        return repairedHammer;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return HammerRecipes.REPAIR_RECIPE_SERIALIZER.get();
    }

    @Nullable
    private Pair<ItemStack, ItemStack> getRepairTargets(CraftingInput recipeInput) {
        // Ensure we have more than 2 slots
        if (recipeInput.width() < 2 || recipeInput.height() < 2) {
            return null;
        }

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

        // Get the repair material
        var repairItem = ((HammerItem) hammer.getItem()).getTier().getRepairIngredient();
        if (repairItem.isEmpty()) {
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

            if (repairItem.test(stack)) {
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
}
