package io.github.corbric.crabsguard;

import io.github.corbric.crabsguard.config.CrabsGuardConfig;
import io.github.corbric.crabsguard.world.WorldManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.swifthq.swiftapi.callbacks.lifecycle.ReloadCallback;
import net.swifthq.swiftapi.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrabsGuard implements ModInitializer {

	public WorldManager worldManager;
	public CrabsGuardConfig config;
	public List<ServerPlayerEntity> scheduledFailMessages = new ArrayList<>();

	private int ticksSinceLastWave = 0;

	@Override
	public void onInitialize() {
		initializeConfig();
		initializeManagers();
		initializeCallbacks();
		ReloadCallback.EVENT.register(() -> {
			initializeConfig();
			return 0;
		});
	}

	private void initializeCallbacks() {
		if (config.sendFailMessage) {
			ServerTickEvents.END_SERVER_TICK.register(server -> {
				ticksSinceLastWave++;
				if (ticksSinceLastWave > config.ticksPerDenialWave) {
					for (ServerPlayerEntity player : scheduledFailMessages) {
						player.sendMessage(config.failUseMessage);
					}
					scheduledFailMessages.clear();
					ticksSinceLastWave = 0;
				}
			});
		}
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
