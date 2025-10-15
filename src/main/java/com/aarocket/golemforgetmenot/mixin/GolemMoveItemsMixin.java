package com.aarocket.golemforgetmenot.mixin;

import com.aarocket.golemforgetmenot.GolemForgetMeNotConfig;
import net.minecraft.entity.ai.brain.task.MoveItemsTask;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MoveItemsTask.class)
public class GolemMoveItemsMixin {
	// Change the limit, basically modify all instances of the constant 10 in the markVisited function to instead check with the modifyVisits function
	@ModifyConstant(
			method = "markVisited",
			constant = @Constant(intValue = 10)
	)
	private int modifyVisits(int original) {
		return GolemForgetMeNotConfig.getVisitsUntilCooldown();
	}


	// extra height when chest searching
	// Default search is 0.5 vertically, increase for every block you want
	@ModifyConstant(
			method = "isWithinRange",
			constant = @Constant(doubleValue = 0.5)
	)
	private double modifyWithinRange(double original)
	{
		return 0.5  + (GolemForgetMeNotConfig.getHeightReach() - 2);
	}


	// Sometimes higher chests werent being detected, shift golem position only for raycast to make it easier to see higher chests
	// this kinda breaks with anything higher than 4
	@ModifyVariable(
			method = "isVisible",
			at = @At("HEAD"),
			ordinal = 0
	)
	private Vec3d modifyVecPos(Vec3d original)
	{
		// adjust to middle of chests
		// any value 2 or greater doesnt detect bottom blocks
		return original.add(0,1.5,0);
	}
}