package pro.mikey.smashy.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import pro.mikey.smashy.Hammers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Hammers.MOD_ID)
public class HammersForge {
    public HammersForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Hammers.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Hammers.init();
    }
}
