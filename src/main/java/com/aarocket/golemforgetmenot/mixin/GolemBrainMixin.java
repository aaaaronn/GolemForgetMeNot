package com.aarocket.golemforgetmenot.mixin;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.ai.brain.task.MoveItemsTask;
import net.minecraft.entity.passive.CopperGolemBrain;
import net.minecraft.registry.tag.BlockTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CopperGolemBrain.class)
public class GolemBrainMixin {

    @Shadow @Final @Mutable
    private static Predicate<BlockState> OUTPUT_CHEST_PREDICATE;

    static {
        OUTPUT_CHEST_PREDICATE = (state) -> {
            return state.isOf(Blocks.CHEST) ||
                    state.isOf(Blocks.TRAPPED_CHEST) ||
                    state.isOf(Blocks.BARREL) ||
                    state.isIn(BlockTags.SHULKER_BOXES);
        };
    }


    @Inject(
            method = "createStoragePredicate",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void editStoragePredicate(CallbackInfoReturnable<Predicate<MoveItemsTask.Storage>> cir) {
        return (storage) -> {
            BlockEntity blockEntity = storage.blockEntity();
            if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                return !chestBlockEntity.getViewingUsers().isEmpty();
            } else {
                return false;
            }
        };
    }
}
