package pro.mikey.justhammers.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import pro.mikey.justhammers.Hammers;

public class HammerRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Hammers.MOD_ID, Registries.RECIPE_TYPE);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(Hammers.MOD_ID, Registries.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeType<RepairRecipe>> REPAIR_RECIPE = RECIPE_TYPES.register("hammer_repair", () -> new RecipeType<>() {});
    public static final RegistrySupplier<RecipeSerializer<RepairRecipe>> REPAIR_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("repair", () -> new CustomRecipe.Serializer<>(RepairRecipe::new));

    public static void init() {
        RECIPE_TYPES.register();
        RECIPE_SERIALIZER.register();
    }
}
