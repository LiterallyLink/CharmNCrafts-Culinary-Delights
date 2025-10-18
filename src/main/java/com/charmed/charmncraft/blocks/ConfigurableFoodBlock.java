package com.charmed.charmncraft.blocks;

import com.charmed.charmncraft.CharmNCraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import java.io.InputStream;

/**
 * A highly configurable food block that can handle any combination of:
 * - Multiple bites/servings (configurable count)
 * - Optional horizontal facing
 * - Shrinking OR constant hitbox
 * - Custom nutrition values
 * - Custom hitbox dimensions
 * - Counts up (bites) or down (servings)
 * 
 * This class replaces the need for 5+ separate food block classes.
 * 
 * @author CharmNCraft Development Team
 */
public class ConfigurableFoodBlock extends Block {
    public static final IntProperty BITES = Properties.BITES;
    public static final IntProperty SERVINGS = IntProperty.of("servings", 0, 6);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    
    private final int maxBites;
    private final boolean hasFacing;
    private final boolean shrinkingShape;
    private final VoxelShape constantShape;
    private final int hungerRestored;
    private final float saturationRestored;
    private final boolean countsDown;
    private final String blockId;
    
    private static final VoxelShape[] VANILLA_CAKE_SHAPES = new VoxelShape[]{
        Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 8.0, 15.0),
        Block.createCuboidShape(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),
        Block.createCuboidShape(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),
        Block.createCuboidShape(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),
        Block.createCuboidShape(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),
        Block.createCuboidShape(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),
        Block.createCuboidShape(13.0, 0.0, 1.0, 15.0, 8.0, 15.0),
        VoxelShapes.empty()
    };

    /**
     * Builder pattern for creating configurable food blocks.
     * This makes the constructor calls much more readable.
     */
    public static class Builder {
        private final Settings settings;
        private final String blockId;
        private int maxBites = 7;
        private boolean hasFacing = false;
        private boolean shrinkingShape = false;
        private double width = 14.0;
        private double height = 8.0;
        private double depth = 14.0;
        private int hungerRestored = 2;
        private float saturationRestored = 0.1F;
        private boolean countsDown = false;
        
        public Builder(Settings settings, String blockId) {
            this.settings = settings;
            this.blockId = blockId;
        }
        
        public Builder maxBites(int maxBites) {
            this.maxBites = maxBites;
            return this;
        }
        
        public Builder withFacing() {
            this.hasFacing = true;
            return this;
        }
        
        public Builder shrinkingShape() {
            this.shrinkingShape = true;
            return this;
        }
        
        public Builder dimensions(double width, double height, double depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
            return this;
        }
        
        public Builder nutrition(int hunger, float saturation) {
            this.hungerRestored = hunger;
            this.saturationRestored = saturation;
            return this;
        }
        
        public Builder countsDown() {
            this.countsDown = true;
            return this;
        }
        
        public ConfigurableFoodBlock build() {
            return new ConfigurableFoodBlock(
                settings, blockId, maxBites, hasFacing, shrinkingShape,
                width, height, depth, hungerRestored, saturationRestored, countsDown
            );
        }
    }

    private ConfigurableFoodBlock(Settings settings, String blockId, int maxBites, boolean hasFacing,
                                  boolean shrinkingShape, double width, double height, double depth,
                                  int hungerRestored, float saturationRestored, boolean countsDown) {
        super(settings);
        
        this.blockId = blockId;
        this.maxBites = maxBites;
        this.hasFacing = hasFacing;
        this.shrinkingShape = shrinkingShape;
        this.hungerRestored = hungerRestored;
        this.saturationRestored = saturationRestored;
        this.countsDown = countsDown;
        
        double offsetX = (16.0 - width) / 2.0;
        double offsetZ = (16.0 - depth) / 2.0;
        this.constantShape = Block.createCuboidShape(
            offsetX, 0.0, offsetZ,
            offsetX + width, height, offsetZ + depth
        );
        
        // Set default state - set BOTH properties since both are registered
        // Only the appropriate one will actually be used based on countsDown
        BlockState defaultState = this.stateManager.getDefaultState()
            .with(BITES, 0)  // Bites: always start at 0
            .with(SERVINGS, countsDown ? maxBites : 0);  // Servings: start at max if used, 0 otherwise
        if (hasFacing) {
            defaultState = defaultState.with(FACING, Direction.NORTH);
        }
        this.setDefaultState(defaultState);
        
        validateModelFiles();
    }
    
    /**
     * Validates that all required model files exist for this block.
     * Logs warnings for missing files to help with debugging.
     */
    private void validateModelFiles() {
        String propertyName = countsDown ? "servings" : "bites";
        
        CharmNCraft.LOGGER.info("Validating models for block: {}", blockId);
        
        String blockstatePath = String.format("/assets/charmncraft/blockstates/%s.json", blockId);
        if (!resourceExists(blockstatePath)) {
            CharmNCraft.LOGGER.warn("Missing blockstate file: {}", blockstatePath);
        }
        
        int totalStates = countsDown ? maxBites + 1 : maxBites;
        for (int i = 0; i <= totalStates; i++) {
            String modelPath = String.format("/assets/charmncraft/models/block/%s_%d.json", blockId, i);
            if (!resourceExists(modelPath)) {
                CharmNCraft.LOGGER.warn("Missing model file: {} (for {}={})", modelPath, propertyName, i);
            }
        }
        
        String itemModelPath = String.format("/assets/charmncraft/models/item/%s.json", blockId);
        if (!resourceExists(itemModelPath)) {
            CharmNCraft.LOGGER.warn("Missing item model: {}", itemModelPath);
        }
        
        CharmNCraft.LOGGER.info("Model validation complete for: {}", blockId);
    }
    
    /**
     * Checks if a resource exists in the jar/resources.
     */
    private boolean resourceExists(String path) {
        try {
            InputStream stream = getClass().getResourceAsStream(path);
            if (stream != null) {
                stream.close();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (shrinkingShape && maxBites <= 7) {
            // Use vanilla cake shrinking shapes
            IntProperty countProperty = countsDown ? SERVINGS : BITES;
            int biteValue = state.get(countProperty);
            // If counting down (servings), invert the index
            int shapeIndex = countsDown ? (maxBites - biteValue) : biteValue;
            return VANILLA_CAKE_SHAPES[Math.min(shapeIndex, 7)];
        }
        return constantShape;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        // ALWAYS add all properties to ALL blocks to avoid initialization order issues
        // this.countsDown isn't initialized yet when this method is called!
        builder.add(BITES, SERVINGS, FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState();
        if (hasFacing) {
            state = state.with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }
        return state;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        
        if (world.isClient) {
            // Client-side: Check if player can eat
            if (player.canConsume(false)) {
                return ActionResult.SUCCESS;
            }
            // If can't eat and has empty hand, still consume the action
            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
            // If can't eat and has item, pass to allow other interactions
            return ActionResult.PASS;
        }
        
        // Server-side: Actually eat the food
        return tryEat(world, pos, state, player);
    }

    /**
     * Attempts to feed the player and update the block state.
     * Handles both "bites" (counting up) and "servings" (counting down) modes.
     */
    protected ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        }

        // Feed the player
        player.incrementStat(Stats.EAT_CAKE_SLICE);
        player.getHungerManager().add(hungerRestored, saturationRestored);
        
        // Use the appropriate property based on counting mode
        IntProperty countProperty = countsDown ? SERVINGS : BITES;
        int currentValue = state.get(countProperty);
        world.emitGameEvent(player, GameEvent.EAT, pos);
        
        boolean shouldRemove;
        int newValue;
        
        if (countsDown) {
            // Servings mode: count down from max to 0
            newValue = currentValue - 1;
            shouldRemove = (newValue < 0);
        } else {
            // Bites mode: count up from 0 to max
            newValue = currentValue + 1;
            shouldRemove = (newValue >= maxBites);
        }
        
        if (shouldRemove) {
            // Last bite/serving - remove the block
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(this.getDefaultState()));
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        } else {
            // Still has bites/servings left
            world.setBlockState(pos, state.with(countProperty, newValue), Block.NOTIFY_ALL);
        }
        
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        IntProperty countProperty = countsDown ? SERVINGS : BITES;
        int currentValue = state.get(countProperty);
        int remaining = countsDown ? currentValue : (maxBites - currentValue);
        return Math.max(0, remaining * 2);
    }
    
    /**
     * Gets the block ID for this food block.
     * Used for debugging and logging.
     */
    public String getBlockId() {
        return blockId;
    }
}
