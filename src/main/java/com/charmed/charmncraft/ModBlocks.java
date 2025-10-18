package com.charmed.charmncraft;

import com.charmed.charmncraft.blocks.BoboChickenBlock;
import com.charmed.charmncraft.blocks.ConfigurableFoodBlock;
import com.charmed.charmncraft.blocks.DeepFryingPanBlock;
import com.charmed.charmncraft.blocks.EndstoneStoveBlock;
import com.charmed.charmncraft.blocks.FriedDumplingBlock;
import com.charmed.charmncraft.blocks.SpringRollMedleyBlock;
import com.charmed.charmncraft.blocks.StuffedShulkerBlock;
import com.charmed.charmncraft.blocks.SweetRiceBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.block.piston.PistonBehavior;

/**
 * Central registry for all custom blocks in CharmNCraft.
 * All blocks are registered to the "charmncraft" namespace.
 * 
 * Each block is automatically registered as a BlockItem for inventory/creative tabs.
 * 
 * Texture Path: assets/charmncraft/textures/block/
 * Model Path: assets/charmncraft/models/block/
 * Blockstate Path: assets/charmncraft/blockstates/
 */
public class ModBlocks {
    // ==================== GOLDEN FOODS BLOCKS ====================
    public static final Block GOLDEN_CAKE = registerBlock("golden_cake",
            new ConfigurableFoodBlock.Builder(AbstractBlock.Settings.copy(Blocks.CAKE), "golden_cake")
                    .maxBites(7)
                    .shrinkingShape()
                    .dimensions(14.0, 8.0, 14.0)
                    .nutrition(2, 0.1F)
                    .build());

    // ==================== ENDER'S DELIGHT BLOCKS ====================
    public static final Block ENDSTONE_STOVE = registerBlock("endstone_stove",
            new EndstoneStoveBlock(AbstractBlock.Settings.copy(Blocks.STONE)
                    .luminance(state -> state.get(EndstoneStoveBlock.LIT) ? 13 : 0)));
    public static final Block CHORUS_CRATE = registerBlock("chorus_crate",
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block CHORUS_PIE = registerBlock("chorus_pie",
            new ConfigurableFoodBlock.Builder(AbstractBlock.Settings.copy(Blocks.CAKE), "chorus_pie")
                    .maxBites(4)
                    .withFacing()
                    .dimensions(14.0, 8.0, 14.0)
                    .nutrition(2, 0.1F)
                    .build());
    public static final Block STUFFED_SHULKER_BLOCK = registerBlock("stuffed_shulker_block",
            new StuffedShulkerBlock(AbstractBlock.Settings.copy(Blocks.SHULKER_BOX).pistonBehavior(PistonBehavior.DESTROY)));

    // ==================== CASUALNESS DELIGHT BLOCKS ====================
    public static final Block BOBO_CHICKEN = registerBlock("bobo_chicken",
            new BoboChickenBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block CHEESE_WHEEL = registerBlock("cheese_wheel",
            new ConfigurableFoodBlock.Builder(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS), "cheese_wheel")
                    .maxBites(6)
                    .shrinkingShape()
                    .dimensions(14.0, 8.0, 14.0)
                    .nutrition(2, 0.2F)
                    .build());
    public static final Block RAW_CHEESE_WHEEL = registerBlock("raw_cheese_wheel",
            new ConfigurableFoodBlock.Builder(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS), "raw_cheese_wheel")
                    .maxBites(6)
                    .withFacing()
                    .dimensions(14.0, 8.0, 14.0)
                    .nutrition(1, 0.1F)
                    .countsDown()
                    .build());
    public static final Block PAPER_WRAPPED_FISH = registerBlock("paper_wrapped_fish",
            new ConfigurableFoodBlock.Builder(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS), "paper_wrapped_fish")
                    .maxBites(6)
                    .withFacing()
                    .dimensions(14.0, 8.0, 14.0)
                    .nutrition(3, 0.3F)
                    .countsDown()
                    .build());
    public static final Block PLATE_OF_FRIED_DUMPLING = registerBlock("plate_of_fried_dumpling",
            new FriedDumplingBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block QUICHE_LORRAINE = registerBlock("quiche_lorraine",
            new ConfigurableFoodBlock.Builder(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS), "quiche_lorraine")
                    .maxBites(4)
                    .withFacing()
                    .dimensions(14.0, 8.0, 14.0)
                    .nutrition(3, 0.3F)
                    .build());
    public static final Block SPRING_ROLL_MEDLEY = registerBlock("spring_roll_medley",
            new SpringRollMedleyBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block STARGAZY_PIE = registerBlock("stargazy_pie",
            new ConfigurableFoodBlock.Builder(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS), "stargazy_pie")
                    .maxBites(4)
                    .withFacing()
                    .dimensions(14.0, 8.0, 14.0)
                    .nutrition(3, 0.3F)
                    .build());
    public static final Block SWEET_RICE = registerBlock("sweet_rice",
            new SweetRiceBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block DEEP_FRYING_PAN = registerBlock("deep_frying_pan",
            new DeepFryingPanBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
                    .nonOpaque()));

    /**
     * Registers a block and its corresponding BlockItem.
     * This ensures the block appears in inventories and creative tabs.
     * 
     * Best Practice: Always call this instead of directly registering blocks
     * to ensure consistent BlockItem registration.
     * 
     * @param name The block's identifier name (lowercase with underscores)
     * @param block The Block instance to register
     * @return The registered Block
     */
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(CharmNCraft.MOD_ID, name), block);
    }

    /**
     * Registers the BlockItem for a given block.
     * Called automatically by registerBlock().
     * 
     * @param name The block's identifier name
     * @param block The Block to create an item for
     * @return The registered BlockItem
     */
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(CharmNCraft.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    /**
     * Called during mod initialization to log block registration status.
     */
    public static void initialize() {
        CharmNCraft.LOGGER.info("Registering blocks.");
    }
}
