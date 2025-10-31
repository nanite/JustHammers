package pro.mikey.justhammers;

import net.minecraft.resources.ResourceLocation;
import pro.mikey.justhammers.config.SimpleJsonConfig;
import pro.mikey.justhammers.recipe.HammerRecipes;
import pro.mikey.justhammers.utils.XPlatShim;

import java.util.ServiceLoader;

public class Hammers {
    public static final String MOD_ID = "justhammers";

    public static final XPlatShim XPLAT = ServiceLoader.load(XPlatShim.class).findFirst().orElseThrow();

    public static void init() {
        SimpleJsonConfig.INSTANCE.load();
        HammerItems.init();
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
