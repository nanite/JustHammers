package pro.mikey.justhammers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pro.mikey.justhammers.hammer.HammerItem;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
    @Shadow @Final protected ServerPlayer player;

    @Shadow protected ServerLevel level;

    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayerGameMode;isCreative()Z", ordinal = 0, shift = At.Shift.BEFORE))
    public void onCreativeRejectOfMineFollowThrough(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = this.player.getMainHandItem();
        if (itemStack.getItem() instanceof HammerItem) {
            BlockState blockState = this.level.getBlockState(blockPos);
            itemStack.mineBlock(this.level, blockState, blockPos, this.player);
        }
    }
}
