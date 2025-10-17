package com.charmed.charmncraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A food block that can be eaten in multiple bites and has a facing direction.
 * Similar to FoodBlockWithBites but includes horizontal facing property.
 */
public class FoodBlockWithBitesAndFacing extends CakeBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty BITES = Properties.BITES;
    private final int maxBites;

    public FoodBlockWithBitesAndFacing(Settings settings, int maxBites) {
        super(settings);
        this.maxBites = maxBites;
        this.setDefaultState(this.stateManager.getDefaultState().with(BITES, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        
        ActionResult result = super.onUse(state, world, pos, player, hand, hit);
        
        if (!world.isClient) {
            BlockState newState = world.getBlockState(pos);
            // Check if block still exists and if bites reached max
            if (!newState.isAir() && newState.isOf(this)) {
                int bites = newState.get(BITES);
                if (bites >= maxBites) {
                    // Show break particles using the current state (with max bites)
                    world.syncWorldEvent(2001, pos, Block.getRawIdFromState(newState));
                    world.removeBlock(pos, false);
                }
            }
        }
        
        return result;
    }
}
