package dev.doctor4t.ratatouille.block;

import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlushBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = createCuboidShape(3.0, 0.0, 3.0, 13.0, 15.0, 13.0);

    public PlushBlock(Settings settings) {
        super(settings);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            var mid = Vec3d.ofCenter(pos);
            var pitch = 1.2f + world.random.nextFloat() * 0.4f;
            var note = world.getBlockState(pos.down());
            if (note.contains(Properties.NOTE)) {
                pitch = (float) Math.pow(2.0, (double) (note.get(Properties.NOTE) - 12) / 12.0);
            }

            if (world.getBlockEntity(pos) instanceof PlushBlockEntity plushie) plushie.squish(24);
        }
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        if (world.getBlockEntity(pos) instanceof PlushBlockEntity plushie) plushie.squish(4);
        super.spawnBreakParticles(world, player, pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            var mid = Vec3d.ofCenter(pos);
            var pitch = 0.8f + world.random.nextFloat() * 0.4f;
            var note = world.getBlockState(pos.down());
            if (note.contains(Properties.NOTE)) {
                pitch = (float) Math.pow(2.0, (double) (note.get(Properties.NOTE) - 12) / 12.0);
            }

            world.playSound(null, mid.getX(), mid.getY(), mid.getZ(), PlushBlock.getSound(state), SoundCategory.BLOCKS, 1.0f, 1.0f);

            if (world.getBlockEntity(pos) instanceof PlushBlockEntity plushie) plushie.squish(1);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, RatatouilleBlockEntities.PLUSH, PlushBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlushBlockEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public static SoundEvent getSound(BlockState state) {
        SoundEvent ret = SoundEvents.BLOCK_WOOL_HIT;
        if (state.getBlock() == RatatouilleBlocks.RAT_MAID_PLUSH) {
            ret = RatatouilleSounds.BLOCK_RAT_MAID_PLUSH_HONK;
        }
        if (state.getBlock() == RatatouilleBlocks.FOLLY_PLUSH) {
            ret = RatatouilleSounds.BLOCK_FOLLY_PLUSH_HONK;
        }
        if (state.getBlock() == RatatouilleBlocks.MAUVE_PLUSH) {
            ret = RatatouilleSounds.BLOCK_MAUVE_PLUSH_HONK;
        }

        return ret;
    }
}