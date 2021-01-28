package io.github.corbric.crabsguard.world;

import io.github.corbric.crabsguard.CrabsGuard;
import io.github.corbric.crabsguard.region.Region;
import net.fabricmc.fabric.impl.base.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.swifthq.swiftapi.callbacks.entity.player.ItemInteractCallback;
import net.swifthq.swiftapi.callbacks.world.BlockBreakCallback;

import java.util.List;

/**
 * Used to manage interactions within the world
 */
public class InteractionHandler {

	private final CrabsGuard crabsGuard;

	public InteractionHandler(CrabsGuard crabsGuard) {
		this.crabsGuard = crabsGuard;
		BlockBreakCallback.EVENT.register(this::onBlockBreak);
		ItemInteractCallback.EVENT.register(this::onItemUse);
	}

	private ActionResult onItemUse(PlayerEntity playerEntity, BlockPos pos, ItemStack itemStack) {
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
		return ActionResult.FAIL;
	}
}
