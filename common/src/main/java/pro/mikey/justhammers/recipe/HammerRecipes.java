package pro.mikey.justhammers.recipe;

import dev.nanite.library.core.registry.RegistryHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import pro.mikey.justhammers.Hammers;

public class HammerRecipes {
    public static final RegistryHolder<RecipeType<?>, RecipeType<RepairRecipe>> REPAIR_RECIPE =
            Hammers.RECIPE_TYPES.register("hammer_repair", () -> new RecipeType<>() {});

    public static final RegistryHolder<RecipeSerializer<?>, RecipeSerializer<RepairRecipe>> REPAIR_RECIPE_SERIALIZER =
            Hammers.RECIPE_SERIALIZER.register("repair", () -> new RecipeSerializer<>(RepairRecipe.CODEC, RepairRecipe.STREAM_CODEC));

    public static void init() {
    }
}
