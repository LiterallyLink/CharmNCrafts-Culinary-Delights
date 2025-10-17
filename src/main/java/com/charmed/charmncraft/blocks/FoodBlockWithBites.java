package com.charmed.charmncraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A food block that can be eaten in multiple bites, similar to cake.
 * Each bite removes one layer until the block is consumed.
 */
public class FoodBlockWithBites extends CakeBlock {
    public static final IntProperty BITES = Properties.BITES;
    private final int maxBites;

    public FoodBlockWithBites(Settings settings, int maxBites) {
        super(settings);
        this.maxBites = maxBites;
        this.setDefaultState(this.stateManager.getDefaultState().with(BITES, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ActionResult result = super.onUse(state, world, pos, player, hand, hit);
        BlockState newState = world.getBlockState(pos);
        int bites = newState.get(BITES);
        
        // Break the block when max bites is reached
        if (bites >= maxBites) {
            world.breakBlock(pos, false);
        }
        return result;
    }
}
