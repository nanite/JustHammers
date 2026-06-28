package pro.mikey.justhammers.fabric;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import pro.mikey.justhammers.client.SelectionOutlineRender;

public class HammersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LevelRenderEvents.COLLECT_SUBMITS.register((event) -> SelectionOutlineRender.render(
                Minecraft.getInstance().level,
                Minecraft.getInstance().gameRenderer.mainCamera(),
                event.poseStack(),
                event.submitNodeCollector()
        ));
    }
}
