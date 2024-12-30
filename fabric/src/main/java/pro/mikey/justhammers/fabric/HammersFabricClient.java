package pro.mikey.justhammers.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import pro.mikey.justhammers.client.SelectionOutlineRender;

public class HammersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.BLOCK_OUTLINE.register(this::renderSelectionOutline);
    }

    private boolean renderSelectionOutline(WorldRenderContext worldRenderContext, WorldRenderContext.BlockOutlineContext blockOutlineContext) {
        SelectionOutlineRender.render(worldRenderContext.world(), worldRenderContext.camera(), worldRenderContext.tickCounter(), worldRenderContext.matrixStack(), worldRenderContext.consumers(), worldRenderContext.gameRenderer(), worldRenderContext.projectionMatrix(), worldRenderContext.gameRenderer().lightTexture(), worldRenderContext.worldRenderer());
        return true;
    }
}
