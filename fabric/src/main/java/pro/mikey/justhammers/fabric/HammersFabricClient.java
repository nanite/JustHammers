package pro.mikey.justhammers.fabric;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import pro.mikey.justhammers.client.SelectionOutlineRender;

public class HammersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((context, outlineRenderState) -> {
            ClientLevel world = Minecraft.getInstance().level;
            SelectionOutlineRender.render(
                    world,
                    Minecraft.getInstance().gameRenderer.getMainCamera(),
                    context.matrices(),
                    context.consumers()
            );

            return true;
        });
    }
}
