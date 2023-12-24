package pro.mikey.justhammers;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class Hammers {
    public static final String MOD_ID = "justhammers";

    public static final CreativeModeTab CREATIVE_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "justhammers_tab"), () ->
            new ItemStack(HammerItems.DIAMOND_FIVE_DESTROY_HAMMER.get()));

    public static void init() {
        HammerItems.ITEMS.register();
        HammerItems.init();

        
    }
}
