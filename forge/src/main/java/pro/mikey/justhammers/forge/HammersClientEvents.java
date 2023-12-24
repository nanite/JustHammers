package pro.mikey.justhammers.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pro.mikey.justhammers.client.SelectionOutlineRender;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
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
