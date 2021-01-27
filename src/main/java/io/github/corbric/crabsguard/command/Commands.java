package io.github.corbric.crabsguard.command;

import net.fabricmc.fabric.api.command.v1.DispatcherRegistrationCallback;

public class Commands {
	public static void initialize() {
		DispatcherRegistrationCallback.EVENT.register((commandDispatcher, dedicated) -> {
			new RegionCommand(commandDispatcher);
		});
	}
}
