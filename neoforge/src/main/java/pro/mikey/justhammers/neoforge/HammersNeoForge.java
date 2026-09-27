package pro.mikey.justhammers.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import pro.mikey.justhammers.Hammers;

@Mod(Hammers.MOD_ID)
public class HammersNeoForge {
    public HammersNeoForge(IEventBus modEventBus, ModContainer container) {
        Hammers.init();
    }
}
