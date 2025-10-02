package com.aarocket.golemforgetmenot;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GolemForgetMeNot implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("GolemForgetMeNot");

	@Override
	public void onInitialize() {
		GolemForgetmeNotConfigLoader.loadConfig();
	}
}