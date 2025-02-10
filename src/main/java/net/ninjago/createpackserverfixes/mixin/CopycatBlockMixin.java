package net.ninjago.createpackserverfixes.mixin;

import com.simibubi.create.content.decoration.copycat.CopycatBlock;
import com.simibubi.create.content.decoration.copycat.CopycatBlockEntity;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(CopycatBlock.class)
public abstract class CopycatBlockMixin {
    @Unique
    boolean create_pack_server_fixes$isTransitioning = true;

    @Unique
    public void create_pack_server_fixes$setIsTransitioning(boolean value) {
        create_pack_server_fixes$isTransitioning = value;
    }

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
            blockEntity.getPersistentData().putBoolean("transitioning", false);
            blockEntity.setChanged();
        }
    }
}
