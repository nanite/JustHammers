package pro.mikey.justhammers.neoforge;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import pro.mikey.justhammers.client.SelectionOutlineRender;

@EventBusSubscriber(value = Dist.CLIENT)
public class HammersClientEvents {
    @SubscribeEvent
    public static void collectCustomGeometryEvent(SubmitCustomGeometryEvent event) {
        Minecraft instance = Minecraft.getInstance();
        SelectionOutlineRender.render(
                instance.level,
                Minecraft.getInstance().gameRenderer.mainCamera(),
                event.getPoseStack(),
                event.getSubmitNodeCollector()
         );
    }
}
