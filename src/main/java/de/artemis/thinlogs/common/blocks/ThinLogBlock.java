package de.artemis.thinlogs.common.blocks;

import de.artemis.thinlogs.common.blockStateProperties.AnchorFace;
import de.artemis.thinlogs.common.blockStateProperties.CoreOrientation;
import de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ThinLogBlock extends Block implements EntityBlock {
    private final boolean stripped;
    @Nullable
    private final Supplier<Block> strippedVariant;

    public ThinLogBlock(Properties properties, boolean stripped, @Nullable Supplier<Block> strippedVariant) {
        super(properties.noOcclusion().pushReaction(PushReaction.NORMAL));
        this.stripped = stripped;
        this.strippedVariant = strippedVariant;
        registerDefaultState(defaultBlockState()
                .setValue(ModBlockStateProperties.NORTH, false)
                .setValue(ModBlockStateProperties.EAST, false)
                .setValue(ModBlockStateProperties.SOUTH, false)
                .setValue(ModBlockStateProperties.WEST, false)
                .setValue(ModBlockStateProperties.UP, false)
                .setValue(ModBlockStateProperties.DOWN, false)
                .setValue(ModBlockStateProperties.ANCHOR_FACE, AnchorFace.NONE)
                .setValue(ModBlockStateProperties.CORE_ORIENTATION, CoreOrientation.VERTICAL));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return ThinLogGeometry.shape(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                ModBlockStateProperties.NORTH,
                ModBlockStateProperties.EAST,
                ModBlockStateProperties.SOUTH,
                ModBlockStateProperties.WEST,
                ModBlockStateProperties.UP,
                ModBlockStateProperties.DOWN,
                ModBlockStateProperties.ANCHOR_FACE,
                ModBlockStateProperties.CORE_ORIENTATION
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        LevelAccessor level = context.getLevel();
        Direction anchorDirection = context.getClickedFace().getOpposite();
        BlockPos anchorPos = pos.relative(anchorDirection);
        BlockState anchorState = level.getBlockState(anchorPos);

        BlockState placedState = defaultBlockState();
        if (!canHardConnectTo(anchorState) && isValidAnchor(anchorState, level, anchorPos, anchorDirection)) {
            placedState = placedState.setValue(ModBlockStateProperties.ANCHOR_FACE, AnchorFace.fromDirection(anchorDirection));
        }

        return updateConnections(placedState, level, pos);
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        AnchorFace anchorFace = state.getValue(ModBlockStateProperties.ANCHOR_FACE);
        if (anchorFace.direction() == direction && !isValidAnchor(neighborState, level, neighborPos, direction)) {
            state = state.setValue(ModBlockStateProperties.ANCHOR_FACE, AnchorFace.NONE);
        }
        return updateConnections(state, level, currentPos);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState blockState, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility == ItemAbilities.AXE_STRIP && !stripped && strippedVariant != null) {
            return copyConnections(strippedVariant.get().defaultBlockState(), blockState);
        }
        return null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        ThinLogBlockEntity blockEntity = getThinLogBlockEntity(level, pos);
        if (blockEntity == null || blockEntity.getOverlayState() == null) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            ItemStack drop = ThinLogOverlay.dropStack(blockEntity.getOverlayState());
            if (!player.addItem(drop)) {
                Block.popResource(level, pos, drop);
            }
            blockEntity.clearOverlay();
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (!(itemStack.getItem() instanceof BlockItem blockItem)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        ThinLogBlockEntity blockEntity = getThinLogBlockEntity(level, pos);
        if (blockEntity == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        BlockState candidateOverlay = blockItem.getBlock().defaultBlockState();
        if (!ThinLogOverlay.canApply(blockEntity.getOverlayState(), candidateOverlay)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        BlockState nextOverlay = ThinLogOverlay.nextOverlayState(blockEntity.getOverlayState(), candidateOverlay);
        if (!level.isClientSide) {
            blockEntity.setOverlayState(nextOverlay);
            SoundEvent soundEvent = blockItem.getBlock().defaultBlockState().getSoundType().getPlaceSound();
            level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.isCreative() && !level.isClientSide) {
            ThinLogBlockEntity blockEntity = getThinLogBlockEntity(level, pos);
            if (blockEntity != null && blockEntity.getOverlayState() != null) {
                ThinLogOverlay.drop(level, pos, blockEntity.getOverlayState());
                blockEntity.clearOverlay();
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            ThinLogBlockEntity blockEntity = getThinLogBlockEntity(level, pos);
            if (blockEntity != null && blockEntity.getOverlayState() != null) {
                ThinLogOverlay.drop(level, pos, blockEntity.getOverlayState());
                blockEntity.clearOverlay();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ThinLogBlockEntity(pos, state);
    }

    private static boolean canHardConnectTo(BlockState state) {
        return state.getBlock() instanceof ThinLogBlock || state.is(BlockTags.LOGS);
    }

    private static boolean isValidAnchor(BlockState state, LevelAccessor level, BlockPos neighborPos, Direction direction) {
        return !canHardConnectTo(state) && state.isFaceSturdy(level, neighborPos, direction.getOpposite());
    }

    private static BlockState updateConnections(BlockState state, LevelAccessor level, BlockPos pos) {
        AnchorFace anchorFace = state.getValue(ModBlockStateProperties.ANCHOR_FACE);
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            boolean connected = canHardConnectTo(neighborState)
                    || (anchorFace.direction() == direction && isValidAnchor(neighborState, level, neighborPos, direction));
            state = state.setValue(ModBlockStateProperties.connection(direction), connected);
        }
        return state.setValue(ModBlockStateProperties.CORE_ORIENTATION, resolveCoreOrientation(state));
    }

    private static BlockState copyConnections(BlockState target, BlockState source) {
        for (Direction direction : Direction.values()) {
            target = target.setValue(ModBlockStateProperties.connection(direction), source.getValue(ModBlockStateProperties.connection(direction)));
        }
        return target
                .setValue(ModBlockStateProperties.ANCHOR_FACE, source.getValue(ModBlockStateProperties.ANCHOR_FACE))
                .setValue(ModBlockStateProperties.CORE_ORIENTATION, source.getValue(ModBlockStateProperties.CORE_ORIENTATION));
    }

    private static CoreOrientation resolveCoreOrientation(BlockState state) {
        boolean x = state.getValue(ModBlockStateProperties.EAST) || state.getValue(ModBlockStateProperties.WEST);
        boolean y = state.getValue(ModBlockStateProperties.UP) || state.getValue(ModBlockStateProperties.DOWN);
        boolean z = state.getValue(ModBlockStateProperties.NORTH) || state.getValue(ModBlockStateProperties.SOUTH);

        if (y) {
            return CoreOrientation.VERTICAL;
        }
        if (x && z) {
            return CoreOrientation.JUNCTION;
        }
        if (x) {
            return CoreOrientation.EAST_WEST;
        }
        if (z) {
            return CoreOrientation.NORTH_SOUTH;
        }
        return CoreOrientation.VERTICAL;
    }

    @Nullable
    private static ThinLogBlockEntity getThinLogBlockEntity(Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof ThinLogBlockEntity thinLogBlockEntity ? thinLogBlockEntity : null;
    }
}
