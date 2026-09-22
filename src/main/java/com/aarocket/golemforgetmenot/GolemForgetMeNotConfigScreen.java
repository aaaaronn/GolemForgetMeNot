package com.aarocket.golemforgetmenot;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Cloth Config screen editing the existing {@link GolemForgetMeNotConfig} values.
 * Persistence is left entirely to {@link GolemForgetmeNotConfigLoader}.
 * Must only be referenced when Cloth Config is present (see {@link ModMenuIntegration}).
 */
public final class GolemForgetMeNotConfigScreen {
	// Defaults, mirroring the initial values in GolemForgetMeNotConfig (used by the "reset" buttons)
	private static final int DEFAULT_VISITS_UNTIL_COOLDOWN = 24;
	private static final boolean DEFAULT_COMPLETE_STACKS = true;
	private static final int DEFAULT_HEIGHT_REACH = 3;

	private GolemForgetMeNotConfigScreen() {}

	public static Screen create(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(Component.translatable("golemforgetmenot.config.title"))
				.setSavingRunnable(GolemForgetmeNotConfigLoader::saveConfig);

		ConfigCategory general = builder.getOrCreateCategory(Component.translatable("golemforgetmenot.config.category.general"));
		ConfigEntryBuilder entries = builder.entryBuilder();

		general.addEntry(entries
				.startIntField(Component.translatable("golemforgetmenot.config.visitsUntilCooldown"), GolemForgetMeNotConfig.getVisitsUntilCooldown())
				.setDefaultValue(DEFAULT_VISITS_UNTIL_COOLDOWN)
				.setMin(1)
				.setTooltip(Component.translatable("golemforgetmenot.config.visitsUntilCooldown.tooltip"))
				.setSaveConsumer(GolemForgetMeNotConfig::setVisitsUntilCooldown)
				.build());

		general.addEntry(entries
				.startBooleanToggle(Component.translatable("golemforgetmenot.config.completeStacks"), GolemForgetMeNotConfig.getCompleteStacks())
				.setDefaultValue(DEFAULT_COMPLETE_STACKS)
				.setTooltip(Component.translatable("golemforgetmenot.config.completeStacks.tooltip"))
				.setSaveConsumer(GolemForgetMeNotConfig::setCompleteStacks)
				.build());

		// Range matches the clamping done in GolemForgetMeNotConfig.setHeightReach (2..4)
		general.addEntry(entries
				.startIntSlider(Component.translatable("golemforgetmenot.config.heightReach"), GolemForgetMeNotConfig.getHeightReach(), 2, 4)
				.setDefaultValue(DEFAULT_HEIGHT_REACH)
				.setTooltip(Component.translatable("golemforgetmenot.config.heightReach.tooltip"))
				.setSaveConsumer(GolemForgetMeNotConfig::setHeightReach)
				.build());

		return builder.build();
	}
}
