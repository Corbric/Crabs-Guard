package io.github.corbric.crabsguard;

import io.github.corbric.crabsguard.command.Commands;
import io.github.corbric.crabsguard.config.CrabsGuardConfig;
import io.github.corbric.crabsguard.region.Region;
import io.github.corbric.crabsguard.region.RegionHandler;
import io.github.corbric.crabsguard.world.InteractionHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.swifthq.swiftapi.callbacks.lifecycle.ReloadCallback;
import net.swifthq.swiftapi.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrabsGuard implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("Crabs Guard");
	public static CrabsGuard INSTANCE;

	public InteractionHandler interactionHandler;
	public RegionHandler regionHandler;

	public CrabsGuardConfig baseConfig;
	public List<Region> regionConfig;

	public List<ServerPlayerEntity> scheduledFailMessages = new ArrayList<>();

	private int ticksSinceLastWave = 0;

	public CrabsGuard() {
		INSTANCE = this;
	}

	@Override
	public void onInitialize() {
		Commands.initialize();
		initializeConfig();
		initializeManagers();
		initializeCallbacks();
		ReloadCallback.EVENT.register(() -> {
			save();
			initializeConfig();
			return 0;
		});
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			save();
		});
	}

	private void initializeCallbacks() {
		if (baseConfig.sendFailMessage) {
			ServerTickEvents.END_SERVER_TICK.register(server -> {
				ticksSinceLastWave++;
				if (ticksSinceLastWave > baseConfig.ticksPerDenialWave) {
					for (ServerPlayerEntity player : scheduledFailMessages) {
						player.sendMessage(baseConfig.failUseMessage);
					}
					scheduledFailMessages.clear();
					ticksSinceLastWave = 0;
				}
			});
		}
	}

	private void save() {
		LOGGER.info("Saving Configs...");
		ConfigManager.write("config", baseConfig);
		ConfigManager.write("regions", regionConfig);
	}

	private void initializeManagers() {
		interactionHandler = new InteractionHandler(this);
		regionHandler = new RegionHandler(this);
	}

	public static CrabsGuard getInstance() {
		return INSTANCE;
	}

	private void initializeConfig() {
		Optional<CrabsGuardConfig> optionalCrabsGuardConfig = ConfigManager.read("config", CrabsGuardConfig.class);
		baseConfig = optionalCrabsGuardConfig.orElseGet(CrabsGuardConfig::new);
		ConfigManager.write("config", baseConfig);

		Optional<List<Region>> optionalRegions = ConfigManager.read("regions", List.class);
		regionConfig = optionalRegions.orElseGet(ArrayList::new);
		ConfigManager.write("regions", baseConfig);
	}
}
