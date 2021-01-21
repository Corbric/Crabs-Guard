package io.github.corbric.crabsguard.world;

import io.github.corbric.crabsguard.CrabsGuard;
import net.fabricmc.fabric.impl.base.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.swifthq.swiftapi.callbacks.entity.player.PlayerItemInteractCallback;
import net.swifthq.swiftapi.callbacks.world.BlockBreakCallback;

/**
 * Used to manage interactions within the world
 */
public class WorldManager {

	private final CrabsGuard crabsGuard;

	public WorldManager(CrabsGuard crabsGuard) {
		this.crabsGuard = crabsGuard;
		BlockBreakCallback.EVENT.register(this::onBlockBreak);
		PlayerItemInteractCallback.EVENT.register(this::onItemUse);
	}

	private ActionResult onItemUse(PlayerEntity playerEntity, ItemStack itemStack) {
		ServerPlayerEntity player = (ServerPlayerEntity) playerEntity;
		player.sendMessage(crabsGuard.config.failUseMessage);
		return ActionResult.FAIL;
	}

	public ActionResult onBlockBreak(ServerPlayerEntity player, BlockPos pos) {
		player.sendMessage(crabsGuard.config.failUseMessage);
		player.networkHandler.sendPacket(new BlockUpdateS2CPacket(player.world, pos));
		return ActionResult.FAIL;
	}
}
