package pro.mikey.justhammers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.Shapes;
import org.joml.Matrix4f;
import pro.mikey.justhammers.HammerItem;
import pro.mikey.justhammers.HammerTags;

import java.util.Iterator;

public class SelectionOutlineRender {

    public static void render(ClientLevel world, Camera camera, float v, PoseStack poseStack, MultiBufferSource consumers, GameRenderer gameRenderer, Matrix4f matrix4f, LightTexture lightTexture, LevelRenderer levelRenderer) {
        // Get the player
        if (world == null) {
            return;
        }

        var player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        if (player.isCrouching()) {
            return;
        }

        // Get the player's held item
        var heldItem = player.getMainHandItem();
        var offHandItem = player.getOffhandItem();
        if (heldItem.isEmpty() && offHandItem.isEmpty()) {
            return;
        }

        // Is the held item a hammer?
        if (!(heldItem.getItem() instanceof HammerItem) && !(offHandItem.getItem() instanceof HammerItem)) {
            return;
        }

        // Raytrace to get the block we're looking at
        var blockHitResult = Minecraft.getInstance().hitResult;
        if (blockHitResult == null || blockHitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        var item = heldItem.getItem() instanceof HammerItem ? heldItem.getItem() : offHandItem.getItem();
        var hammer = (HammerItem) item;

        // Get the block's position
        var blockPos = ((BlockHitResult) blockHitResult).getBlockPos();
        var direction = ((BlockHitResult) blockHitResult).getDirection();

        // Get the block at the pos
        var block = world.getBlockState(blockPos);
        var incorrectFor = hammer.getTier().getIncorrectBlocksForDrops();
        if (block.is(incorrectFor) || block.is(HammerTags.HAMMER_NO_SMASHY)) {
            return;
        }

        var boundingBox = HammerItem.getAreaOfEffect(blockPos, direction, hammer.getRadius(), hammer.getDepth());

        // Transform the pose stack to the camera's position
        poseStack.pushPose();
        poseStack.translate(-camera.getPosition().x(), -camera.getPosition().y(), -camera.getPosition().z());

        // Render the outline
        Iterator<BlockPos> blockPosStream = BlockPos.betweenClosedStream(boundingBox).iterator();
        while (blockPosStream.hasNext()) {
            BlockPos pos = blockPosStream.next();
            if (pos.equals(blockPos)) {
                continue;
            }

            if (world.getBlockState(pos).isAir()) {
                continue;
            }

            poseStack.pushPose();
            // Shift the pose stack to the block's position
            poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
            LevelRenderer.renderShape(
                    poseStack,
                    consumers.getBuffer(RenderType.lines()),
                    Shapes.block(),
                    // Location
                    0, 0, 0,
                    // Color
                    0.0F, 0.0F, 0.0F, 0.35F);
            poseStack.popPose();
        }

        // Pop the pose stack
        poseStack.popPose();
    }
}
