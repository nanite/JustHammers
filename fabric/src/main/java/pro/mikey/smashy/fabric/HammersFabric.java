package pro.mikey.smashy.fabric;

import pro.mikey.smashy.Hammers;
import net.fabricmc.api.ModInitializer;

public class HammersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Hammers.init();
    }
}
