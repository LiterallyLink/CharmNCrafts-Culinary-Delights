package charmncrafts.delights;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import charmncrafts.delights.item.GoldenFoods;
import charmncrafts.delights.item.ModItemGroups;

public class CharmNCraftsCulinaryDelights implements ModInitializer {
	public static final String MOD_ID = "charmncrafts-culinary-delights";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing CharmNCrafts Culinary Delights!");
		
		// Register golden foods
		GoldenFoods.initialize();
		LOGGER.info("Golden Foods registered!");
		
		// Create custom creative tab
		ModItemGroups.registerItemGroups();
		LOGGER.info("Custom creative tab created!");
		
		LOGGER.info("CharmNCrafts Culinary Delights loaded successfully!");
	}
}
