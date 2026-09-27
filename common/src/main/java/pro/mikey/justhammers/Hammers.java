package pro.mikey.justhammers;

import dev.nanite.library.core.config.ConfigManager;
import dev.nanite.library.core.registry.NaniteRegistry;
import dev.nanite.library.core.registry.RegistryHolder;
import dev.nanite.library.core.weirdness.CreativeModeTabBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import pro.mikey.justhammers.recipe.HammerRecipes;
import pro.mikey.justhammers.utils.XPlatShim;

import java.util.ServiceLoader;

public class Hammers {
    public static final String MOD_ID = "justhammers";

    public static final XPlatShim XPLAT = ServiceLoader.load(XPlatShim.class).findFirst().orElseThrow();

    private static final NaniteRegistry<CreativeModeTab> CREATIVE_TABS = NaniteRegistry.create(Hammers.MOD_ID, BuiltInRegistries.CREATIVE_MODE_TAB);
    public static final NaniteRegistry<Item> ITEMS = NaniteRegistry.create(Hammers.MOD_ID, BuiltInRegistries.ITEM);

    public static final NaniteRegistry<RecipeType<?>> RECIPE_TYPES = NaniteRegistry.create(Hammers.MOD_ID, BuiltInRegistries.RECIPE_TYPE);
    public static final NaniteRegistry<RecipeSerializer<?>> RECIPE_SERIALIZER = NaniteRegistry.create(Hammers.MOD_ID, BuiltInRegistries.RECIPE_SERIALIZER);

    public static final RegistryHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_TABS.register("creative_tab",
            () -> new CreativeModeTabBuilder("itemGroup.justhammers.justhammers_tab")
                    .icon(() -> new ItemStack(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER.get()))
                    .itemDisplay((params, output) -> {
                        for (var item : ITEMS.entries()) {
                            output.accept(item.get());
                        }
                    })
                    .build()
    );


    public static void init() {
        ConfigManager.register(HammersConfig.CLIENT_CONFIG);
        ConfigManager.register(HammersConfig.COMMON_CONFIG);

        HammerItems.init();
        HammerRecipes.init();

        CREATIVE_TABS.initialize();
        ITEMS.initialize();
        RECIPE_TYPES.initialize();
        RECIPE_SERIALIZER.initialize();
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
