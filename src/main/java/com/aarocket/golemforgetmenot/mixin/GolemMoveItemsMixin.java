package com.aarocket.golemforgetmenot.mixin;

import com.aarocket.golemforgetmenot.GolemForgetMeNotConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.entity.ai.brain.task.MoveItemsTask;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(MoveItemsTask.class)
public class GolemMoveItemsMixin {
	// change the limit, basically modify all instances of the constant 10 in the markVisited function to instead check with the modifyVisits function
	@ModifyConstant(
			method = "markVisited(Lnet/minecraft/entity/mob/PathAwareEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			constant = @Constant(intValue = 10)
	)
	private int modifyVisits(int original) {
		return GolemForgetMeNotConfig.getVisitsUntilCooldown();
	}

	// if configuration asks for it, ignore empty slots during first pass of insertion, focussing on completing existing stacks first
    @ModifyExpressionValue(
        method = "insertStack(Lnet/minecraft/entity/mob/PathAwareEntity;Lnet/minecraft/inventory/Inventory;)Lnet/minecraft/item/ItemStack;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0)
    )
    private static boolean modifyEmptyCheck(boolean original) {
        // Only treat it as empty if we're NOT in complete-stacks mode
        return original && !GolemForgetMeNotConfig.getCompleteStacks();
    }
	
	// if configuration asks for it, only insert in empty slots after trying to complete existing stacks
    @Inject(
        method = "insertStack(Lnet/minecraft/entity/mob/PathAwareEntity;Lnet/minecraft/inventory/Inventory;)Lnet/minecraft/item/ItemStack;",
        at = @At("RETURN"),
        cancellable = true
    )
    private static void addSecondPass(PathAwareEntity entity, Inventory inventory, CallbackInfoReturnable<ItemStack> cir) {
        if (!GolemForgetMeNotConfig.getCompleteStacks()) return;
        ItemStack itemStack = cir.getReturnValue();
        if (itemStack.isEmpty()) return;
        int i = 0;
        for (Iterator<ItemStack> it = inventory.iterator(); it.hasNext(); ++i) {
            ItemStack itemStack2 = it.next();
            if (itemStack2.isEmpty()) {
                inventory.setStack(i, itemStack);
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }
        }
        // If we reach here, itemStack still not inserted, return it as-is
        cir.setReturnValue(itemStack);
    }

}