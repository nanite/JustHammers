package pro.mikey.smashy;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Hammers {
    public static final String MOD_ID = "smashy";

    public static final CreativeModeTab CREATIVE_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "smash_tab"), () ->
            new ItemStack(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER.get()));

    public static void init() {
        HammerItems.ITEMS.register();
        HammerItems.init();
    }
}
