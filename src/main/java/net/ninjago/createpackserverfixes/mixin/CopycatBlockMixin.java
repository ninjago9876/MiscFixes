package net.ninjago.createpackserverfixes.mixin;

import com.simibubi.create.content.decoration.copycat.CopycatBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(CopycatBlock.class)
public abstract class CopycatBlockMixin {
    @Redirect(
            method = "onRemove(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/decoration/copycat/CopycatBlock;withBlockEntityDo(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Ljava/util/function/Consumer;)V"
            )
    )
    private void onRemove(CopycatBlock instance, BlockGetter blockGetter, BlockPos blockPos, Consumer consumer) {
        CopycatBlock thiz = (CopycatBlock) (Object) this;
        BlockEntity blockEntity = instance.getBlockEntity(blockGetter, blockPos);

        if (blockEntity != null) {
            if (!blockEntity.getPersistentData().getBoolean("transitioning")) {
                thiz.withBlockEntityDo(blockGetter, blockPos, consumer);
            }
        }
    }
}
