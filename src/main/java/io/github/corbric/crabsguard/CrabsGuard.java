package io.github.corbric.crabsguard;

import io.github.corbric.crabsguard.config.CrabsGuardConfig;
import io.github.corbric.crabsguard.world.WorldManager;
import net.fabricmc.api.ModInitializer;
import net.swifthq.swiftapi.config.ConfigManager;

import java.util.Optional;

public class CrabsGuard implements ModInitializer {

	public WorldManager worldManager;
	public CrabsGuardConfig config;

	@Override
	public void onInitialize() {
		initializeConfig();
		initializeManagers();
	}

	private void initializeManagers() {
		worldManager = new WorldManager(this);
	}

	private void initializeConfig() {
		Optional<CrabsGuardConfig> optional = ConfigManager.read("config", CrabsGuardConfig.class);
		config = optional.orElseGet(CrabsGuardConfig::new);
		ConfigManager.write("config", config);
	}


}
