package pro.mikey.justhammers.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import pro.mikey.justhammers.Hammers;
import net.fabricmc.api.ModInitializer;
import pro.mikey.justhammers.client.SelectionOutlineRender;

public class HammersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Hammers.init();
    }
}
