package io.github.corbric.crabsguard.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.corbric.crabsguard.CrabsGuard;
import io.github.corbric.crabsguard.region.RegionHandler;
import net.fabricmc.fabric.api.command.v1.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import static net.fabricmc.fabric.api.command.v1.CommandManager.argument;
import static net.fabricmc.fabric.api.command.v1.CommandManager.literal;

public class RegionCommand {

	public RegionCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(literal("region")
				.requires(source -> source.hasPermissionLevel(4) && source.isPlayer())
				.then(argument("name", StringArgumentType.string())
				.then(literal("create")
						.executes(context -> {
							String name = context.getArgument("name", String.class);
							CrabsGuard.getInstance().regionConfig.add(CrabsGuard.getInstance().regionHandler.createNewRegion(name));
							context.getSource().sendFeedback(new LiteralText("Region '" + name + "' created!").setStyle(new Style().setColor(Formatting.GREEN)));
							return 0;
						}))
				.then(literal("remove")
						.executes(context -> {
							String name = context.getArgument("name", String.class);
							context.getSource().sendFeedback(new LiteralText("Region '" + name + "' removed!").setStyle(new Style().setColor(Formatting.RED)));
							return 0;
						}))
				.executes(context -> {
					context.getSource().sendFeedback(new LiteralText("Please Specify a sub command").setStyle(new Style().setColor(Formatting.RED)));
					return 0;
				})
		));
	}
}
