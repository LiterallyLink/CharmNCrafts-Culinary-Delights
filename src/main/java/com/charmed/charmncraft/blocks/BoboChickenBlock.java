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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

/**
 * Bobo Chicken block that gives Potato Bobo Chicken items when right-clicked.
 * Does not restore hunger directly - instead gives the player an item.
 * On the final serving, drops only a bowl (no food item).
 */
public class BoboChickenBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty SERVINGS = IntProperty.of("servings", 0, 6);
    
    public BoboChickenBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(SERVINGS, 6));
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
        int newServings = currentServings - 1;
        
        world.emitGameEvent(player, GameEvent.EAT, pos);

        if (newServings < 0) {
            // Last serving - drop only a bowl (no food item)
            ItemStack bowl = new ItemStack(Items.BOWL);
            ItemEntity bowlEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, bowl);
            world.spawnEntity(bowlEntity);
            
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(this.getDefaultState()));
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        } else {
            // Not the last serving - give Potato Bobo Chicken item
            ItemStack potatoBoboChicken = new ItemStack(ModItems.POTATO_BOBO_CHICKEN);
            if (!player.getInventory().insertStack(potatoBoboChicken)) {
                player.dropItem(potatoBoboChicken, false);
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
