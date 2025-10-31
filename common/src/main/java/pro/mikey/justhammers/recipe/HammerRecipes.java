package pro.mikey.justhammers.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import pro.mikey.justhammers.utils.DeferredResource;

public class HammerRecipes {
    public static final DeferredResource<RecipeType<?>, RecipeType<RepairRecipe>> REPAIR_RECIPE =
            new DeferredResource<>("hammer_repair", () -> new RecipeType<>() {});

    public static final DeferredResource<RecipeSerializer<?>, RecipeSerializer<RepairRecipe>> REPAIR_RECIPE_SERIALIZER =
            new DeferredResource<>("repair", () -> new SimpleCraftingRecipeSerializer<>(RepairRecipe::new));
}
