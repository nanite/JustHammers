package pro.mikey.justhammers.neoforge;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import pro.mikey.justhammers.client.SelectionOutlineRender;

@EventBusSubscriber(value = Dist.CLIENT)
public class HammersClientEvents {
    @SubscribeEvent
    public static void onWorldRenderLast(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
            return;
        }

        Minecraft instance = Minecraft.getInstance();
        SelectionOutlineRender.render(
                instance.level,
                event.getCamera(),
                event.getPartialTick(),
                event.getPoseStack(),
                instance.renderBuffers().bufferSource(),
                instance.gameRenderer,
                event.getProjectionMatrix(),
                instance.gameRenderer.lightTexture(),
                instance.levelRenderer
         );
    }
}
