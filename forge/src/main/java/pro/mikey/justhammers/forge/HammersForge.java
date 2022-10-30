package pro.mikey.justhammers.forge;

import dev.architectury.platform.forge.EventBuses;
import pro.mikey.justhammers.Hammers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Hammers.MOD_ID)
public class HammersForge {
    public HammersForge() {
        EventBuses.registerModEventBus(Hammers.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Hammers.init();
    }
}
