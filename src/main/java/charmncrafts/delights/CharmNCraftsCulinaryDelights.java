package charmncrafts.delights;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import charmncrafts.delights.modules.goldenfoods.GoldenFoodsModule;

/**
 * CharmNCrafts Culinary Delights
 *
 * An amalgamation mod that combines multiple food-related mods into one organized package.
 * Each original mod is kept as a separate module for easy maintenance and expansion.
 */
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

		LOGGER.info("==================================================");
		LOGGER.info("Initializing CharmNCrafts Culinary Delights!");
		LOGGER.info("==================================================");

		// Initialize all modules
		GoldenFoodsModule.initialize();

		// Future modules can be added here:
		// AnotherModule.initialize();
		// YetAnotherModule.initialize();

		LOGGER.info("==================================================");
		LOGGER.info("CharmNCrafts Culinary Delights loaded successfully!");
		LOGGER.info("==================================================");
	}
}
