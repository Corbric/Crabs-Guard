package io.github.corbric.crabsguard.config;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CrabsGuardConfig {
	public int configVersion = 1;

	public int ticksPerDenialWave = 10;

	public Text failUseMessage = new LiteralText("Hey!")
			.setStyle(new Style()
					.setBold(true)
					.setColor(Formatting.RED))
			.append(new LiteralText(" Sorry, but you can't do that here.")
					.setStyle(new Style()
							.setColor(Formatting.GRAY)));
}
