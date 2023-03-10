package net.satisfy.candlelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.util.CropType;

public class PickCropBlock extends SweetBerryBushBlock {

    private final CropType type;

    public PickCropBlock(Settings settings, CropType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        boolean bl = i == 3;
        if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (i > 1) {
            int x = world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(this.type == CropType.TOMATO ? net.satisfy.candlelight.registry.ObjectRegistry.TOMATO : net.satisfy.candlelight.registry.ObjectRegistry.STRAWBERRY, x + (bl ? 1 : 0)));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlockState(pos, state.with(AGE, 1), 2);
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return switch (this.type) {
            case TOMATO -> new ItemStack(ObjectRegistry.TOMATO);
            case STRAWBERRY -> new ItemStack(ObjectRegistry.STRAWBERRY);
        };
    }
    public CropType getType() {
        return type;
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

    }
}

