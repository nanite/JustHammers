package pro.mikey.justhammers.neoforge;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import pro.mikey.justhammers.HammerItems;
import pro.mikey.justhammers.Hammers;
import pro.mikey.justhammers.recipe.HammerRecipes;
import pro.mikey.justhammers.recipe.RepairRecipe;

@Mod(Hammers.MOD_ID)
public class HammersNeoForge {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Hammers.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Hammers.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Hammers.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Hammers.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_TABS.register("creative_tab",
            () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER.get()))
                .title(Component.translatable("itemGroup.justhammers.justhammers_tab"))
                .displayItems((params, output) -> {
                    for (var item : HammerItems.ITEMS) {
                        output.accept(item.get());
                    }
                })
                .build()
        );

    public HammersNeoForge(IEventBus modEventBus, ModContainer container) {
        Hammers.init();

        CREATIVE_TABS.register(modEventBus);
        for (var item : HammerItems.ITEMS) {
            ITEMS.register(item.location().getPath(), item);
        }

        RECIPE_TYPES.register(HammerRecipes.REPAIR_RECIPE.location().getPath(), HammerRecipes.REPAIR_RECIPE);
        RECIPE_SERIALIZER.register(HammerRecipes.REPAIR_RECIPE_SERIALIZER.location().getPath(), HammerRecipes.REPAIR_RECIPE_SERIALIZER);

        ITEMS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZER.register(modEventBus);
    }
}
