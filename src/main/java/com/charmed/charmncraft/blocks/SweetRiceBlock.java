package com.charmed.charmncraft.blocks;

import com.charmed.charmncraft.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

/**
 * Sweet Rice block that requires a bowl to eat.
 * Gives Bowl of Sweet Rice when right-clicked with a bowl.
 * On the final serving, drops only a bowl (no food) when destroyed.
 */
public class SweetRiceBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty SERVINGS = IntProperty.of("servings", 0, 4);
    
    public SweetRiceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(SERVINGS, 4));
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
        ItemStack heldItem = player.getStackInHand(hand);

        if (world.isClient) {
            // Client-side preview
            return heldItem.isOf(Items.BOWL) ? ActionResult.SUCCESS : ActionResult.PASS;
        }

        // Server-side: Check if player has a bowl
        if (!heldItem.isOf(Items.BOWL)) {
            player.sendMessage(Text.literal("You need a bowl to eat this!"), true);
            return ActionResult.FAIL;
        }

        int currentServings = state.get(SERVINGS);
        int newServings = currentServings - 1;
        
        // Consume the bowl
        heldItem.decrement(1);
        
        world.emitGameEvent(player, GameEvent.EAT, pos);

        if (newServings < 0) {
            // Last serving - give back a bowl (no food)
            ItemStack bowl = new ItemStack(Items.BOWL);
            if (!player.getInventory().insertStack(bowl)) {
                ItemEntity bowlEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, bowl);
                world.spawnEntity(bowlEntity);
            }
            
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(this.getDefaultState()));
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        } else {
            // Not the last serving - give Bowl of Sweet Rice
            ItemStack bowlOfSweetRice = new ItemStack(ModItems.BOWL_OF_SWEET_RICE);
            if (!player.getInventory().insertStack(bowlOfSweetRice)) {
                player.dropItem(bowlOfSweetRice, false);
            }
            
            // Update servings count
            world.setBlockState(pos, state.with(SERVINGS, newServings), Block.NOTIFY_ALL);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        int servings = state.get(SERVINGS);
        return Math.max(0, servings * 2);
    }
}
