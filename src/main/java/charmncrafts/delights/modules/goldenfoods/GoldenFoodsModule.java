package charmncrafts.delights.modules.goldenfoods;

import charmncrafts.delights.CharmNCraftsCulinaryDelights;
import charmncrafts.delights.modules.goldenfoods.blocks.GoldenCakes;
import charmncrafts.delights.modules.goldenfoods.items.GoldenCakeItems;
import charmncrafts.delights.modules.goldenfoods.items.GoldenFoods;
import charmncrafts.delights.modules.goldenfoods.groups.GoldenFoodsItemGroup;

/**
 * Golden Foods Module
 *
 * This module adds golden variants of various Minecraft foods with enhanced effects:
 * - 21 golden food items (fruits, meats, breads, soups, drinks)
 * - 2 golden cake blocks (regular and enchanted)
 * - Custom creative tab for all golden foods
 */
public class GoldenFoodsModule {

    public static void initialize() {
        CharmNCraftsCulinaryDelights.LOGGER.info("Initializing Golden Foods module...");

        // Register golden food items
        GoldenFoods.initialize();
        CharmNCraftsCulinaryDelights.LOGGER.info("  - Golden food items registered");

        // Register golden cake blocks
        GoldenCakes.initialize();
        CharmNCraftsCulinaryDelights.LOGGER.info("  - Golden cake blocks registered");

        // Register golden cake items
        GoldenCakeItems.initialize();
        CharmNCraftsCulinaryDelights.LOGGER.info("  - Golden cake items registered");

        // Create custom creative tab
        GoldenFoodsItemGroup.registerItemGroups();
        CharmNCraftsCulinaryDelights.LOGGER.info("  - Golden Foods creative tab created");

        CharmNCraftsCulinaryDelights.LOGGER.info("Golden Foods module loaded successfully!");
    }
}
