package pro.mikey.justhammers.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import pro.mikey.justhammers.Hammers;

public class HammerRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Hammers.MOD_ID, Registries.RECIPE_TYPE);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(Hammers.MOD_ID, Registries.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeType<RepairRecipe>> REPAIR_RECIPE = RECIPE_TYPES.register("repair", () -> RecipeType.register(ResourceLocation.fromNamespaceAndPath(Hammers.MOD_ID, "hammer_repair").toString()));
    public static final RegistrySupplier<RecipeSerializer<RepairRecipe>> REPAIR_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("repair", () -> new SimpleCraftingRecipeSerializer<>(RepairRecipe::new));

    public static void init() {
        RECIPE_TYPES.register();
        RECIPE_SERIALIZER.register();
    }
}
