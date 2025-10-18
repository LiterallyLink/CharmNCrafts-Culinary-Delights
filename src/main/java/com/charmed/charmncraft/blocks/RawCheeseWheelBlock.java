package com.charmed.charmncraft.blocks;

import com.charmed.charmncraft.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * Raw cheese wheel that ferments over time.
 * NOT edible - must fully ferment before becoming edible cheese.
 * 
 * Fermentation stages: 0 (fresh) -> 1 -> 2 -> 3 (converts to cheese_wheel)
 * 
 * Uses random ticks for fermentation (similar to crops).
 * Average time: ~68 seconds per stage, ~4.5 minutes total.
 */
public class RawCheeseWheelBlock extends HorizontalFacingBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty FERMENT = IntProperty.of("ferment", 0, 3);
    
    private static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 8.0, 15.0);
    
    public RawCheeseWheelBlock(Settings settings) {
        super(settings.ticksRandomly()); // Enable random ticks for fermentation
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(FERMENT, 0));
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FERMENT);
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(FERMENT, 0); // Always start fresh
    }
    
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    
    /**
     * Random tick handler for fermentation.
     * Increments ferment level over time, converts to cheese_wheel at max.
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int currentFerment = state.get(FERMENT);
        
        if (currentFerment < 3) {
            // Still fermenting - increment by 1
            world.setBlockState(pos, state.with(FERMENT, currentFerment + 1), Block.NOTIFY_ALL);
        } else {
            // Fully fermented - convert to edible cheese wheel
            BlockState cheeseWheel = ModBlocks.CHEESE_WHEEL.getDefaultState();
            // CHEESE_WHEEL doesn't have facing, so we don't preserve it
            world.setBlockState(pos, cheeseWheel, Block.NOTIFY_ALL);
        }
    }
    
    // NO onUse method = not edible at any stage
}
