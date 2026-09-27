package pro.mikey.justhammers.fabric;

import pro.mikey.justhammers.Hammers;
import net.fabricmc.api.ModInitializer;

public class HammersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Hammers.init();
    }
}
