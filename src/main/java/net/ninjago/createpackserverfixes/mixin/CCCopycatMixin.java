package net.ninjago.createpackserverfixes.mixin;

import com.copycatsplus.copycats.foundation.copycat.CCCopycatBlock;
import com.simibubi.create.content.decoration.copycat.CopycatBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.ninjago.createpackserverfixes.CreatePackServerFixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CCCopycatBlock.class)
public class CCCopycatMixin {
    @Inject(method = "onRemove", at = @At(value = "HEAD"), cancellable = true)
    private void createpackserverfixes$onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving, CallbackInfo ci) {
        CreatePackServerFixes.LOGGER.info("Copycat+ block broken!");

        CCCopycatBlock thiz = (CCCopycatBlock) (Object) this;
        BlockEntity blockEntity = thiz.getBlockEntity(pLevel, pPos);

        if (blockEntity != null) {
            if (blockEntity.getPersistentData().getBoolean("transitioning")) {
                pLevel.removeBlockEntity(pPos);
                ci.cancel();
            }
        }
    }
}
