package io.github.corbric.crabsguard.world;

import io.github.corbric.crabsguard.CrabsGuard;
import net.fabricmc.fabric.impl.base.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
		if(itemStack != null) {
			Item item = itemStack.getItem();
			boolean cancel = false;

			if (item instanceof BlockItem) {
				cancel = true;
			}

			if (cancel) {
				if(!crabsGuard.scheduledFailMessages.contains(player)) {
					crabsGuard.scheduledFailMessages.add(player);
				}
				return ActionResult.FAIL;
			} else {
				return ActionResult.SUCCESS;
			}
		}
		// Its fine to use Air
		return ActionResult.SUCCESS;
	}

	public ActionResult onBlockBreak(ServerPlayerEntity player, BlockPos pos) {
		if(!crabsGuard.scheduledFailMessages.contains(player)) {
			crabsGuard.scheduledFailMessages.add(player);
		}
		player.networkHandler.sendPacket(new BlockUpdateS2CPacket(player.world, pos));
		return ActionResult.FAIL;
	}
}
