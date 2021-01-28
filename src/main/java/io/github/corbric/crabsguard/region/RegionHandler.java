package io.github.corbric.crabsguard.region;

import io.github.corbric.crabsguard.CrabsGuard;

public class RegionHandler {

	public final CrabsGuard crabsGuard;

	public RegionHandler(CrabsGuard crabsGuard) {
		this.crabsGuard = crabsGuard;
	}

	public Region createNewRegion(String name) {
		return new Region(); //TODO:
	}
}
