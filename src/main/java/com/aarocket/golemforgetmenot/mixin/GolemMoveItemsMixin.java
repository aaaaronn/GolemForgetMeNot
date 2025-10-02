package com.aarocket.golemforgetmenot.mixin;

import com.aarocket.golemforgetmenot.GolemForgetMeNotConfig;
import net.minecraft.entity.ai.brain.task.MoveItemsTask;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

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
}