package net.ninjago.createpackserverfixes.mixin;

import com.simibubi.create.content.decoration.copycat.CopycatBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.util.RelocationUtilKt;

import java.util.HashMap;
import java.util.Map;

@Mixin(RelocationUtilKt.class)
public class VSRelocationUtilMixin {
    @Inject(
//            method = "relocateBlock(Lnet/minecraft/world/level/chunk/LevelChunk;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/chunk/LevelChunk;Lnet/minecraft/core/BlockPos;ZLorg/valkyrienskies/core/api/ships/ServerShip;Lnet/minecraft/world/level/block/Rotation;)V",
            method = "relocateBlock(Lnet/minecraft/world/level/chunk/LevelChunk;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/chunk/LevelChunk;Lnet/minecraft/core/BlockPos;ZLorg/valkyrienskies/core/api/ships/ServerShip;Lnet/minecraft/world/level/block/Rotation;)V",
            at = @At("HEAD"),
            remap = false
    )
    private static void create_pack_server_fixes$relocateBlockMixin(LevelChunk fromChunk, BlockPos from, LevelChunk toChunk, BlockPos _to, boolean doUpdate, ServerShip toShip, Rotation rotation, CallbackInfo ci) {
        BlockState state = fromChunk.getBlockState(from);
        if (state.hasBlockEntity()) {
            BlockEntity entity = fromChunk.getBlockEntity(from);
            if (entity != null) {
                entity.getPersistentData().putBoolean("transitioning", true);
                entity.setChanged();
            }
        }
    }
}
