package io.github.corbric.crabsguard.region;

import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class Region {

	public String name;
	public Map<Identifier, Boolean> properties;
	public List<String> whitelistedPlayers;
}
