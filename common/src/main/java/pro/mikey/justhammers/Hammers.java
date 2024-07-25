package pro.mikey.justhammers;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import pro.mikey.justhammers.config.SimpleJsonConfig;
import pro.mikey.justhammers.recipe.HammerRecipes;

public class Hammers {
    public static final String MOD_ID = "justhammers";

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> TAB = CREATIVE_TABS.register("creative_tab", () -> CreativeTabRegistry.create(
            Component.translatable("itemGroup.justhammers.justhammers_tab"),
            () -> new ItemStack(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER.get())
    ));

    public static void init() {
        SimpleJsonConfig.INSTANCE.load();
        CREATIVE_TABS.register();
        HammerRecipes.init();
        HammerItems.ITEMS.register();
        HammerItems.init();
    }
}
