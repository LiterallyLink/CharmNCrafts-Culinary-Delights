package com.charmed.charmncraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * A food block that provides multiple servings.
 * Similar to StuffedShulkerBlock but for general food items.
 * Players can eat directly from the block, decreasing servings until depleted.
 */
public class FoodBlockWithServings extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty SERVINGS = IntProperty.of("servings", 0, 6);
    private final int maxServings;
    
    // Shape for the block - similar to cake but slightly taller
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 12.0, 15.0);

    public FoodBlockWithServings(Settings settings, int maxServings) {
        super(settings);
        this.maxServings = maxServings;
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(SERVINGS, maxServings));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, SERVINGS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        int currentServings = state.get(SERVINGS);
        
        if (currentServings > 0) {
            // Feed the player (basic hunger restoration)
            if (!player.isCreative()) {
                player.getHungerManager().add(2, 0.3F);
            }
            
            // Decrease servings
            world.setBlockState(pos, state.with(SERVINGS, currentServings - 1));
            
            return ActionResult.SUCCESS;
        } else {
            // No servings left, break the block
            world.breakBlock(pos, false);
            return ActionResult.SUCCESS;
        }
    }
}
