package de.artemis.thinlogs.common.blocks;

import de.artemis.thinlogs.common.ModUtil;
import de.artemis.thinlogs.common.blockStateProperties.AppliedOnThinLogBlock;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties.APPLIED_ON_THIN_LOG_BLOCK;

public class ThinLogBlock extends RotatedPillarBlock {
    protected final boolean stripped;
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_CARPET = Stream.of(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(12.0D, 0.0D, 4.0D, 16.0D, 1.0D, 12.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 4.0D), Block.box(0.0D, 0.0D, 12.0D, 16.0D, 1.0D, 16.0D), Block.box(0.0D, 0.0D, 4.0D, 4.0D, 1.0D, 12.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_1 = Stream.of(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(12.0D, 0.0D, 4.0D, 16.0D, 2.0D, 12.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 4.0D), Block.box(0.0D, 0.0D, 12.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 4.0D, 4.0D, 2.0D, 12.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_2 = Stream.of(Block.box(0.0D, 0.0D, 4.0D, 4.0D, 4.0D, 12.0D), Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(12.0D, 0.0D, 4.0D, 16.0D, 4.0D, 12.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 4.0D), Block.box(0.0D, 0.0D, 12.0D, 16.0D, 4.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_3 = Stream.of(Block.box(0.0D, 0.0D, 4.0D, 4.0D, 6.0D, 12.0D), Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(12.0D, 0.0D, 4.0D, 16.0D, 6.0D, 12.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 4.0D), Block.box(0.0D, 0.0D, 12.0D, 16.0D, 6.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_4 = Stream.of(Block.box(0.0D, 0.0D, 4.0D, 4.0D, 8.0D, 12.0D), Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(12.0D, 0.0D, 4.0D, 16.0D, 8.0D, 12.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 4.0D), Block.box(0.0D, 0.0D, 12.0D, 16.0D, 8.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_5 = Stream.of(Block.box(0.0D, 0.0D, 4.0D, 4.0D, 10.0D, 12.0D), Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(12.0D, 0.0D, 4.0D, 16.0D, 10.0D, 12.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 4.0D), Block.box(0.0D, 0.0D, 12.0D, 16.0D, 10.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_6 = Stream.of(Block.box(0.0D, 0.0D, 4.0D, 4.0D, 12.0D, 12.0D), Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(12.0D, 0.0D, 4.0D, 16.0D, 12.0D, 12.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 4.0D), Block.box(0.0D, 0.0D, 12.0D, 16.0D, 12.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_7 = Stream.of(Block.box(0.0D, 0.0D, 4.0D, 4.0D, 14.0D, 12.0D), Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.box(12.0D, 0.0D, 4.0D, 16.0D, 14.0D, 12.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 4.0D), Block.box(0.0D, 0.0D, 12.0D, 16.0D, 14.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_CARPET = Shapes.join(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D), BooleanOp.OR);
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_SNOW_LAYER_1 = Shapes.join(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D), BooleanOp.OR);
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_SNOW_LAYER_2 = Shapes.join(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D), BooleanOp.OR);
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_SNOW_LAYER_3 = Stream.of(Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D), Block.box(12.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 4.0D, 6.0D, 16.0D), Block.box(4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_SNOW_LAYER_4 = Stream.of(Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D), Block.box(12.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 4.0D, 8.0D, 16.0D), Block.box(4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_SNOW_LAYER_5 = Stream.of(Block.box(0.0D, 0.0D, 0.0D, 4.0D, 10.0D, 16.0D), Block.box(4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 16.0D), Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D), Block.box(12.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_SNOW_LAYER_6 = Stream.of(Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D), Block.box(12.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 4.0D, 12.0D, 16.0D), Block.box(4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_SNOW_LAYER_7 = Stream.of(Block.box(0.0D, 0.0D, 0.0D, 4.0D, 14.0D, 16.0D), Block.box(4.0D, 0.0D, 0.0D, 12.0D, 4.0D, 16.0D), Block.box(4.0D, 12.0D, 0.0D, 12.0D, 14.0D, 16.0D), Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D), Block.box(12.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape VOXEL_SHAPE_VERTICAL_FULL_BLOCK = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public ThinLogBlock(Properties properties, boolean stripped) {
        super(properties.noOcclusion());
        this.stripped = stripped;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(AXIS)) {
            case X -> switch (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK)) {
                case MOSS_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case WHITE_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case ORANGE_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case MAGENTA_CARPET ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case LIGHT_BLUE_CARPET ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case YELLOW_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case LIME_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case PINK_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case GRAY_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case LIGHT_GRAY_CARPET ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case CYAN_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case PURPLE_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case BLUE_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case BROWN_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case GREEN_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case RED_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case BLACK_CARPET -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_CARPET);
                case OAK_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case BIRCH_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case SPRUCE_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case DARK_OAK_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case ACACIA_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case JUNGLE_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case MANGROVE_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case AZALEA_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case FLOWERING_AZALEA_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case CHERRY_LEAVES ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case SNOW_LAYER_1 ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_SNOW_LAYER_1);
                case SNOW_LAYER_2 ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_SNOW_LAYER_2);
                case SNOW_LAYER_3 ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_SNOW_LAYER_3);
                case SNOW_LAYER_4 ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_SNOW_LAYER_4);
                case SNOW_LAYER_5 ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_SNOW_LAYER_5);
                case SNOW_LAYER_6 ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_SNOW_LAYER_6);
                case SNOW_LAYER_7 ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_SNOW_LAYER_7);
                case SNOW_LAYER_8 ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case SNOW_BLOCK ->
                        ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL_FULL_BLOCK);
                case DEFAULT -> ModUtil.rotateShape(Direction.NORTH, Direction.EAST, VOXEL_SHAPE_VERTICAL);
            };
            case Y -> switch (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK)) {
                case MOSS_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case WHITE_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case ORANGE_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case MAGENTA_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case LIGHT_BLUE_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case YELLOW_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case LIME_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case PINK_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case GRAY_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case LIGHT_GRAY_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case CYAN_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case PURPLE_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case BLUE_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case BROWN_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case GREEN_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case RED_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case BLACK_CARPET -> VOXEL_SHAPE_HORIZONTAL_CARPET;
                case OAK_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case BIRCH_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case SPRUCE_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case DARK_OAK_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case ACACIA_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case JUNGLE_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case MANGROVE_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case AZALEA_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case FLOWERING_AZALEA_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case CHERRY_LEAVES -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case SNOW_LAYER_1 -> VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_1;
                case SNOW_LAYER_2 -> VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_2;
                case SNOW_LAYER_3 -> VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_3;
                case SNOW_LAYER_4 -> VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_4;
                case SNOW_LAYER_5 -> VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_5;
                case SNOW_LAYER_6 -> VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_6;
                case SNOW_LAYER_7 -> VOXEL_SHAPE_HORIZONTAL_SNOW_LAYER_7;
                case SNOW_LAYER_8 -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case SNOW_BLOCK -> VOXEL_SHAPE_HORIZONTAL_FULL_BLOCK;
                case DEFAULT -> VOXEL_SHAPE_HORIZONTAL;
            };
            case Z -> switch (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK)) {
                case MOSS_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case WHITE_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case ORANGE_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case MAGENTA_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case LIGHT_BLUE_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case YELLOW_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case LIME_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case PINK_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case GRAY_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case LIGHT_GRAY_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case CYAN_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case PURPLE_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case BLUE_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case BROWN_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case GREEN_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case RED_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case BLACK_CARPET -> VOXEL_SHAPE_VERTICAL_CARPET;
                case OAK_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case BIRCH_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case SPRUCE_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case DARK_OAK_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case ACACIA_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case JUNGLE_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case MANGROVE_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case AZALEA_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case FLOWERING_AZALEA_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case CHERRY_LEAVES -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case SNOW_LAYER_1 -> VOXEL_SHAPE_VERTICAL_SNOW_LAYER_1;
                case SNOW_LAYER_2 -> VOXEL_SHAPE_VERTICAL_SNOW_LAYER_2;
                case SNOW_LAYER_3 -> VOXEL_SHAPE_VERTICAL_SNOW_LAYER_3;
                case SNOW_LAYER_4 -> VOXEL_SHAPE_VERTICAL_SNOW_LAYER_4;
                case SNOW_LAYER_5 -> VOXEL_SHAPE_VERTICAL_SNOW_LAYER_5;
                case SNOW_LAYER_6 -> VOXEL_SHAPE_VERTICAL_SNOW_LAYER_6;
                case SNOW_LAYER_7 -> VOXEL_SHAPE_VERTICAL_SNOW_LAYER_7;
                case SNOW_LAYER_8 -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case SNOW_BLOCK -> VOXEL_SHAPE_VERTICAL_FULL_BLOCK;
                case DEFAULT -> VOXEL_SHAPE_VERTICAL;
            };
        };
    }

    //Stripping
    @Override
    public @Nullable BlockState getToolModifiedState(BlockState blockState, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction == ToolActions.AXE_STRIP && !stripped) {
            return ModBlocks.THIN_STRIPPED_OAK_LOG.get().defaultBlockState().setValue(AXIS, blockState.getValue(AXIS)).setValue(APPLIED_ON_THIN_LOG_BLOCK, blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK));
        }
        return null;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        interactionHand = InteractionHand.MAIN_HAND;
        ItemStack itemStackInHand = player.getItemInHand(interactionHand);
        Block appliedBlock = Block.byItem(blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).getItem().get());

        // Removing an applied Block
        if (itemStackInHand.isEmpty() && !blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.DEFAULT)) {
            System.out.println("1");
            level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.DEFAULT), 3);
            level.playSound(player, blockPos, appliedBlock.getSoundType(blockState).getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!player.isCreative() && !level.isClientSide) {
                if (player.canTakeItem(new ItemStack(Block.byItem(blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).getItem().get())))) {
                    if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_2)) {
                        for (int i = 0; i < 2; i++) {
                            if (!player.getInventory().add(new ItemStack(appliedBlock))) {
                                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_3)) {
                        for (int i = 0; i < 3; i++) {
                            if (!player.getInventory().add(new ItemStack(appliedBlock))) {
                                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_4)) {
                        for (int i = 0; i < 4; i++) {
                            if (!player.getInventory().add(new ItemStack(appliedBlock))) {
                                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_5)) {
                        for (int i = 0; i < 5; i++) {
                            if (!player.getInventory().add(new ItemStack(appliedBlock))) {
                                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_6)) {
                        for (int i = 0; i < 6; i++) {
                            if (!player.getInventory().add(new ItemStack(appliedBlock))) {
                                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_7)) {
                        for (int i = 0; i < 7; i++) {
                            if (!player.getInventory().add(new ItemStack(appliedBlock))) {
                                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_8)) {
                        for (int i = 0; i < 8; i++) {
                            if (!player.getInventory().add(new ItemStack(appliedBlock))) {
                                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        }
                    } else {
                        player.addItem(new ItemStack(Block.byItem(blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).getItem().get())));

                    }
                }
            }

            return InteractionResult.SUCCESS;
        }

        // Applying a block to the log
        if (itemStackInHand.is(ModTags.Item.CAN_BE_APPLIED_ON_THIN_LOGS)) {
            System.out.println("2");
            boolean appliedBlockIsDefault = blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.DEFAULT);
            boolean success = false;

            if (itemStackInHand.is(Blocks.MOSS_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.MOSS_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.WHITE_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.WHITE_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.ORANGE_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.ORANGE_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.MAGENTA_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.MAGENTA_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.LIGHT_BLUE_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.LIGHT_BLUE_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.YELLOW_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.YELLOW_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.LIME_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.LIME_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.PINK_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.PINK_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.GRAY_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.GRAY_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.LIGHT_GRAY_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.LIGHT_GRAY_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.CYAN_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.CYAN_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.PURPLE_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.PURPLE_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.BLUE_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.BLUE_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.BROWN_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.BROWN_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.GREEN_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.GREEN_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.RED_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.RED_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.BLACK_CARPET.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.BLACK_CARPET), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.OAK_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.OAK_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.BIRCH_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.BIRCH_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SPRUCE_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SPRUCE_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.DARK_OAK_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.DARK_OAK_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.ACACIA_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.ACACIA_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.JUNGLE_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.JUNGLE_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.MANGROVE_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.MANGROVE_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.AZALEA_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.AZALEA_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.FLOWERING_AZALEA_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.FLOWERING_AZALEA_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.CHERRY_LEAVES.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.CHERRY_LEAVES), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW_BLOCK.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_BLOCK), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW.asItem()) && appliedBlockIsDefault) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_LAYER_1), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW.asItem()) && blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_1)) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_LAYER_2), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW.asItem()) && blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_2)) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_LAYER_3), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW.asItem()) && blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_3)) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_LAYER_4), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW.asItem()) && blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_4)) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_LAYER_5), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW.asItem()) && blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_5)) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_LAYER_6), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW.asItem()) && blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_6)) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_LAYER_7), 3);
                success = true;
            } else if (itemStackInHand.is(Blocks.SNOW.asItem()) && blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_7)) {
                level.setBlock(blockPos, blockState.setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.SNOW_LAYER_8), 3);
                success = true;
            }

            if (itemStackInHand.getItem() instanceof BlockItem blockItem && success) {
                SoundEvent soundEvent = blockItem.getBlock().getSoundType(blockState).getPlaceSound();
                level.playSound(player, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!player.isCreative()) {
                    itemStackInHand.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockState, Level level, BlockPos blockPos, Player player, boolean willHarvest, FluidState fluid) {
        level.playSound(player, blockPos, Block.byItem(blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).getItem().get()).getSoundType(blockState).getBreakSound(), SoundSource.BLOCKS, 0.5F, 1.0F);
        Block appliedBlock = Block.byItem(blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).getItem().get());

        if (!player.isCreative() && !level.isClientSide) {
            if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_2)) {
                for (int i = 0; i < 2; i++) {
                    ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);
                }
            } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_3)) {
                for (int i = 0; i < 3; i++) {
                    ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);

                }
            } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_4)) {
                for (int i = 0; i < 4; i++) {
                    ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);

                }
            } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_5)) {
                for (int i = 0; i < 5; i++) {
                    ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);

                }
            } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_6)) {
                for (int i = 0; i < 6; i++) {
                    ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);

                }
            } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_7)) {
                for (int i = 0; i < 7; i++) {
                    ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);

                }
            } else if (blockState.getValue(APPLIED_ON_THIN_LOG_BLOCK).equals(AppliedOnThinLogBlock.SNOW_LAYER_8)) {
                for (int i = 0; i < 8; i++) {
                    ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);

                }
            } else {
                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, new ItemStack(appliedBlock));
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }

        return super.onDestroyedByPlayer(blockState, level, blockPos, player, willHarvest, fluid);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(AXIS, pContext.getClickedFace().getAxis()).setValue(APPLIED_ON_THIN_LOG_BLOCK, AppliedOnThinLogBlock.DEFAULT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS).add(APPLIED_ON_THIN_LOG_BLOCK);
    }
}
