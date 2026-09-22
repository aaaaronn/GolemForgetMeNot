package com.aarocket.golemforgetmenot;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.fabricmc.loader.api.FabricLoader;

/**
 * Mod Menu entrypoint ("modmenu" in fabric.mod.json).
 * Only ever loaded by Mod Menu itself, so the mod keeps working without it.
 */
public class ModMenuIntegration implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		// The screen is built with Cloth Config: without it, don't offer a config button at all
		if (!FabricLoader.getInstance().isModLoaded("cloth-config")) {
			return ModMenuApi.super.getModConfigScreenFactory();
		}
		return GolemForgetMeNotConfigScreen::create;
	}
}
