package pro.mikey.justhammers.neoforge.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pro.mikey.justhammers.HammerItem;

/**
 * Forge and fabric seem to have different source sets so this is required... Fun
 */
@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixinNeoForge {
    @Shadow @Final protected ServerPlayer player;

    @Shadow protected ServerLevel level;

    /**
     * This is used when we're in creative mode, and we're trying to mine a block
     */
    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayerGameMode;removeBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;ZLnet/minecraft/world/item/ItemStack;)Z", ordinal = 0, shift = At.Shift.BEFORE))
    public void justhammers$beforeRemoveBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        this.hammerMineBlock(blockPos);
    }

    /**
     * This is used when we're in survival mode, and we're trying to mine a block
     */
    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;mineBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)V", ordinal = 0, shift = At.Shift.BEFORE))
    public void justhammers$beforeMineBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        this.hammerMineBlock(blockPos);
    }

    private void hammerMineBlock(BlockPos blockPos) {
        ItemStack itemStack = this.player.getMainHandItem();
        if (itemStack.getItem() instanceof HammerItem hammerItem) {
            BlockState blockState = this.level.getBlockState(blockPos);
            hammerItem.causeAoe(this.level, blockPos, blockState, itemStack, this.player);
        }
    }
}
