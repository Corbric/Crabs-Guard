package io.github.corbric.crabsguard.world;

import io.github.corbric.crabsguard.CrabsGuard;
import net.fabricmc.fabric.impl.base.util.ActionResult;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.swifthq.swiftapi.callbacks.world.BlockBreakCallback;

/**
 * Used to manage interactions within the world
 */
public class WorldManager {

	private final CrabsGuard crabsGuard;

	public WorldManager(CrabsGuard crabsGuard) {
		this.crabsGuard = crabsGuard;
		BlockBreakCallback.EVENT.register(this::onBlockBreak);
	}

	public ActionResult onBlockBreak(ServerPlayerEntity player, BlockPos pos) {
		player.sendMessage(crabsGuard.config.failUseMessage);
		player.networkHandler.sendPacket(new BlockUpdateS2CPacket(player.world, pos));
		return ActionResult.FAIL;
	}
}
