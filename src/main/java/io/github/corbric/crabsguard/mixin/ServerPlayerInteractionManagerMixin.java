package io.github.corbric.crabsguard.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

	@Inject(method = "method_6090", at = @At("HEAD")) // Some sortof sword block method?
	private void test(PlayerEntity playerEntity, World world, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
		//TODO: look into this
	}
}
