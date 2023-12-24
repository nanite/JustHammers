package pro.mikey.justhammers.fabric.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pro.mikey.justhammers.hammer.HammerItem;

/**
 * Forge and fabric seem to have different source sets so this is required... Fun
 */
@Debug(export = true)
@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixinFabric {
    @Shadow @Final protected ServerPlayer player;

    @Shadow protected ServerLevel level;

    /**
     * This is used when we're in creative mode, and we're trying to mine a block
     */
    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z", ordinal = 0, shift = At.Shift.BEFORE))
    public void justhammers$beforeDestoryBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = this.player.getMainHandItem();
        if (itemStack.getItem() instanceof HammerItem hammerItem) {
            BlockState blockState = this.level.getBlockState(blockPos);
            hammerItem.causeAoe(this.level, blockPos, blockState, itemStack, this.player);
        }
    }
}
