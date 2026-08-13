package de.artemis.thinlogs.common.blocks;

import de.artemis.thinlogs.common.blockStateProperties.AnchorFace;
import de.artemis.thinlogs.common.blockStateProperties.CoreOrientation;
import de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ThinLogBlock extends Block implements EntityBlock {
    private static final int BLOCK_ENTITY_SYNC_FLAGS = 3 | 8;
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
        return combinedShape(blockState, blockGetter, blockPos, collisionContext, false);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return combinedShape(blockState, blockGetter, blockPos, collisionContext, true);
    }

    @Override
    public @NotNull VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return combinedShape(blockState, blockGetter, blockPos, CollisionContext.empty(), false);
    }

    @Override
    public @NotNull VoxelShape getBlockSupportShape(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        ThinLogBlockEntity blockEntity = getThinLogBlockEntity(blockGetter, pos);
        if (blockEntity != null && ThinLogOverlay.type(blockEntity.getFoliageOverlayState()) == ThinLogOverlay.OverlayType.LEAVES) {
            return Shapes.block();
        }
        return super.getBlockSupportShape(state, blockGetter, pos);
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
    protected @NotNull BlockState updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos currentPos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        AnchorFace anchorFace = state.getValue(ModBlockStateProperties.ANCHOR_FACE);
        if (level instanceof LevelAccessor levelAccessor) {
            if (anchorFace.direction() == direction && !isValidAnchor(neighborState, levelAccessor, neighborPos, direction)) {
                state = state.setValue(ModBlockStateProperties.ANCHOR_FACE, AnchorFace.NONE);
            }
            state = updateConnections(state, levelAccessor, currentPos);
        }
        if (direction == Direction.DOWN && level instanceof Level actualLevel) {
            dropUnsupportedOverlay(actualLevel, currentPos, state);
        }
        return state;
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
        return InteractionResult.PASS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        InteractionResult connectionResult = tryToggleConnection(itemStack, state, level, pos, player, hitResult);
        if (connectionResult.consumesAction()) {
            return connectionResult;
        }

        if (overlayCandidateState(itemStack) != null) {
            return tryApplyOverlay(itemStack, level, pos, player);
        }

        if (itemStack.getItem() instanceof BlockItem) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        return tryRemoveOverlay(itemStack, level, pos, player);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            ThinLogBlockEntity blockEntity = getOrCreateThinLogBlockEntity(level, pos, state);
            if (blockEntity != null) {
                if (!player.isCreative()) {
                    ThinLogOverlay.drop(level, pos, blockEntity.getSurfaceOverlayState());
                    ThinLogOverlay.drop(level, pos, blockEntity.getFoliageOverlayState());
                }
                blockEntity.clearSurfaceOverlay();
                blockEntity.clearFoliageOverlay();
                updateBelowSnowyState(level, pos, null, null);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected boolean shouldChangedStateKeepBlockEntity(BlockState oldState) {
        return oldState.getBlock() instanceof ThinLogBlock;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
        if (level.getBlockState(pos).getBlock() instanceof ThinLogBlock) {
            return;
        }

        ThinLogBlockEntity blockEntity = getThinLogBlockEntity(level, pos);
        if (blockEntity != null) {
            ThinLogOverlay.drop(level, pos, blockEntity.getSurfaceOverlayState());
            ThinLogOverlay.drop(level, pos, blockEntity.getFoliageOverlayState());
            blockEntity.clearSurfaceOverlay();
            blockEntity.clearFoliageOverlay();
            updateBelowSnowyState(level, pos, null, null);
        }
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
        ThinLogBlockEntity blockEntity = getThinLogBlockEntity(level, pos);
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            boolean connected = canHardConnectTo(neighborState)
                    || (anchorFace.direction() == direction && isValidAnchor(neighborState, level, neighborPos, direction));
            if (blockEntity != null && blockEntity.isConnectionDisabled(direction)) {
                connected = false;
            }
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
        boolean east = state.getValue(ModBlockStateProperties.EAST);
        boolean west = state.getValue(ModBlockStateProperties.WEST);
        boolean up = state.getValue(ModBlockStateProperties.UP);
        boolean down = state.getValue(ModBlockStateProperties.DOWN);
        boolean north = state.getValue(ModBlockStateProperties.NORTH);
        boolean south = state.getValue(ModBlockStateProperties.SOUTH);
        boolean x = east || west;
        boolean y = up || down;
        boolean z = north || south;

        if (y && !x && !z) {
            return CoreOrientation.VERTICAL;
        }
        if (x && !y && !z) {
            return CoreOrientation.EAST_WEST;
        }
        if (z && !x && !y) {
            return CoreOrientation.NORTH_SOUTH;
        }
        return CoreOrientation.JUNCTION;
    }

    private static BlockState resolveOverlayState(BlockState candidateOverlay) {
        if (ThinLogOverlay.type(candidateOverlay) == ThinLogOverlay.OverlayType.LEAVES) {
            if (candidateOverlay.hasProperty(LeavesBlock.PERSISTENT)) {
                candidateOverlay = candidateOverlay.setValue(LeavesBlock.PERSISTENT, true);
            }
            if (candidateOverlay.hasProperty(LeavesBlock.DISTANCE)) {
                candidateOverlay = candidateOverlay.setValue(LeavesBlock.DISTANCE, 1);
            }
        }
        return candidateOverlay;
    }

    private static VoxelShape combinedShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext, boolean collision) {
        VoxelShape shape = ThinLogGeometry.shape(state);
        ThinLogBlockEntity blockEntity = getThinLogBlockEntity(blockGetter, pos);
        if (blockEntity == null) {
            return shape;
        }

        shape = joinOverlayShape(shape, blockEntity.getFoliageOverlayState(), state, blockGetter, pos, collisionContext, collision);
        return joinOverlayShape(shape, blockEntity.getSurfaceOverlayState(), state, blockGetter, pos, collisionContext, collision);
    }

    private static VoxelShape joinOverlayShape(VoxelShape shape, @Nullable BlockState overlayState, BlockState baseState, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext, boolean collision) {
        if (overlayState == null) {
            return shape;
        }

        VoxelShape overlayShape = overlayShapeFor(overlayState, baseState, blockGetter, pos, collisionContext, collision);
        if (overlayShape.isEmpty()) {
            return shape;
        }

        return Shapes.join(shape, overlayShape, BooleanOp.OR);
    }

    private static VoxelShape overlayShapeFor(BlockState overlayState, BlockState baseState, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext, boolean collision) {
        ThinLogOverlay.OverlayType overlayType = ThinLogOverlay.type(overlayState);
        if (overlayType == ThinLogOverlay.OverlayType.NONE) {
            return Shapes.empty();
        }
        if (overlayType == ThinLogOverlay.OverlayType.LEAVES && collision) {
            return Shapes.block();
        }
        return collision
                ? overlayState.getCollisionShape(blockGetter, pos, collisionContext)
                : overlayState.getShape(blockGetter, pos, collisionContext);
    }

    public static InteractionResult tryToggleConnection(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!player.isShiftKeyDown() || !itemStack.canPerformAction(ItemAbilities.AXE_STRIP)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        ThinLogBlockEntity blockEntity = getOrCreateThinLogBlockEntity(level, pos, state);
        if (blockEntity == null) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        Direction direction = resolveConnectionDirection(hitResult, pos);
        if (!canToggleConnection(state, level, pos, blockEntity, direction)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        if (!level.isClientSide()) {
            boolean disabled = !blockEntity.isConnectionDisabled(direction);
            boolean changed = blockEntity.setConnectionDisabled(direction, disabled);

            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            if (neighborState.getBlock() instanceof ThinLogBlock) {
                ThinLogBlockEntity neighborBlockEntity = getOrCreateThinLogBlockEntity(level, neighborPos, neighborState);
                if (neighborBlockEntity != null) {
                    changed |= neighborBlockEntity.setConnectionDisabled(direction.getOpposite(), disabled);
                    updateThinLogState(level, neighborPos, neighborState);
                }
            }

            updateThinLogState(level, pos, state);
            if (changed) {
                syncThinLogBlockEntity(level, pos);
                if (neighborState.getBlock() instanceof ThinLogBlock) {
                    syncThinLogBlockEntity(level, neighborPos);
                }
            }
            level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, disabled ? 0.9F : 1.1F);
        }

        return sidedSuccess(level);
    }

    private static boolean canToggleConnection(BlockState state, Level level, BlockPos pos, ThinLogBlockEntity blockEntity, Direction direction) {
        if (blockEntity.isConnectionDisabled(direction)) {
            return true;
        }

        BlockPos neighborPos = pos.relative(direction);
        BlockState neighborState = level.getBlockState(neighborPos);
        AnchorFace anchorFace = state.getValue(ModBlockStateProperties.ANCHOR_FACE);

        return canHardConnectTo(neighborState)
                || (anchorFace.direction() == direction && isValidAnchor(neighborState, level, neighborPos, direction));
    }

    private static Direction resolveConnectionDirection(BlockHitResult hitResult, BlockPos pos) {
        Vec3 localHit = hitResult.getLocation().subtract(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
        double absX = Math.abs(localHit.x);
        double absY = Math.abs(localHit.y);
        double absZ = Math.abs(localHit.z);

        if (absX > absY && absX > absZ) {
            return localHit.x >= 0.0D ? Direction.EAST : Direction.WEST;
        }
        if (absY > absZ) {
            return localHit.y >= 0.0D ? Direction.UP : Direction.DOWN;
        }
        if (absZ > 0.0D) {
            return localHit.z >= 0.0D ? Direction.SOUTH : Direction.NORTH;
        }
        return hitResult.getDirection();
    }

    private static void updateThinLogState(Level level, BlockPos pos, BlockState state) {
        BlockState updatedState = updateConnections(state, level, pos);
        if (updatedState != state) {
            level.setBlock(pos, updatedState, 3);
        }
    }

    public static void refreshThinLogState(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof ThinLogBlock) {
            updateThinLogState(level, pos, state);
        }
    }

    private static void syncThinLogBlockEntity(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof ThinLogBlock) {
            level.sendBlockUpdated(pos, state, state, BLOCK_ENTITY_SYNC_FLAGS);
        }
    }

    public static InteractionResult tryApplyOverlay(ItemStack itemStack, Level level, BlockPos pos, Player player) {
        BlockState candidateOverlay = overlayCandidateState(itemStack);
        if (candidateOverlay == null) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof ThinLogBlock)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        ThinLogBlockEntity blockEntity = getOrCreateThinLogBlockEntity(level, pos, state);
        if (blockEntity == null) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        BlockState foliageOverlayState = blockEntity.getFoliageOverlayState();
        BlockState surfaceOverlayState = blockEntity.getSurfaceOverlayState();
        ThinLogOverlay.OverlayType overlayType = ThinLogOverlay.type(candidateOverlay);

        if (overlayType == ThinLogOverlay.OverlayType.LEAVES) {
            if (!ThinLogOverlay.canApplyFoliage(foliageOverlayState, surfaceOverlayState, candidateOverlay)) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
        } else if (!ThinLogOverlay.canApplySurface(surfaceOverlayState, candidateOverlay, level, pos, foliageOverlayState)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        if (overlayType == ThinLogOverlay.OverlayType.LEAVES) {
            blockEntity.setFoliageOverlayState(candidateOverlay);
        } else {
            blockEntity.setSurfaceOverlayState(ThinLogOverlay.nextSurfaceState(surfaceOverlayState, candidateOverlay));
        }
        updateBelowSnowyState(level, pos, blockEntity.getFoliageOverlayState(), blockEntity.getSurfaceOverlayState());

        if (!level.isClientSide()) {
            SoundEvent soundEvent = candidateOverlay.getSoundType().getPlaceSound();
            level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        }

        return sidedSuccess(level);
    }

    public static boolean shouldInterceptRightClick(ItemStack itemStack, Player player) {
        if (player.isShiftKeyDown() && itemStack.canPerformAction(ItemAbilities.AXE_STRIP)) {
            return true;
        }
        if (canRemoveOverlayWithItem(itemStack)) {
            return true;
        }
        return overlayCandidateState(itemStack) != null;
    }

    public static boolean canRemoveOverlayWithItem(ItemStack itemStack) {
        return supportsOverlayRemovalInteraction(itemStack);
    }

    public static InteractionResult tryRemoveOverlay(ItemStack itemStack, Level level, BlockPos pos, Player player) {
        if (!supportsOverlayRemovalInteraction(itemStack)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof ThinLogBlock)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        ThinLogBlockEntity blockEntity = getOrCreateThinLogBlockEntity(level, pos, state);
        if (blockEntity == null) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        BlockState removedOverlayState = blockEntity.getSurfaceOverlayState() != null
                ? blockEntity.getSurfaceOverlayState()
                : blockEntity.getFoliageOverlayState();
        if (removedOverlayState == null) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        if (!level.isClientSide()) {
            if (!player.isCreative()) {
                ItemStack drop = ThinLogOverlay.dropStack(removedOverlayState);
                if (!player.addItem(drop)) {
                    Block.popResource(level, pos, drop);
                }
            }
        }

        if (blockEntity.getSurfaceOverlayState() != null) {
            blockEntity.clearSurfaceOverlay();
        } else {
            blockEntity.clearFoliageOverlay();
        }
        updateBelowSnowyState(level, pos, blockEntity.getFoliageOverlayState(), blockEntity.getSurfaceOverlayState());

        if (!level.isClientSide()) {
            SoundEvent soundEvent = removedOverlayState.getSoundType().getBreakSound();
            level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        return sidedSuccess(level);
    }

    private static InteractionResult sidedSuccess(Level level) {
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    private static boolean supportsOverlayRemovalInteraction(ItemStack itemStack) {
        return itemStack.isEmpty() || !(itemStack.getItem() instanceof BlockItem);
    }

    @Nullable
    private static BlockState overlayCandidateState(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BlockItem blockItem)) {
            return null;
        }

        BlockState candidateOverlay = resolveOverlayState(blockItem.getBlock().defaultBlockState());
        return ThinLogOverlay.type(candidateOverlay) == ThinLogOverlay.OverlayType.NONE ? null : candidateOverlay;
    }

    private static void dropUnsupportedOverlay(Level level, BlockPos pos, BlockState state) {
        ThinLogBlockEntity blockEntity = getOrCreateThinLogBlockEntity(level, pos, state);
        if (blockEntity == null) {
            return;
        }

        BlockState foliageOverlayState = blockEntity.getFoliageOverlayState();
        BlockState surfaceOverlayState = blockEntity.getSurfaceOverlayState();
        if (surfaceOverlayState == null || ThinLogOverlay.canSurfaceSurviveAt(surfaceOverlayState, level, pos, foliageOverlayState)) {
            return;
        }

        ThinLogOverlay.drop(level, pos, surfaceOverlayState);
        blockEntity.clearSurfaceOverlay();
        updateBelowSnowyState(level, pos, foliageOverlayState, null);
    }

    private static void updateBelowSnowyState(Level level, BlockPos pos, @Nullable BlockState foliageOverlayState, @Nullable BlockState surfaceOverlayState) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!belowState.hasProperty(SnowyDirtBlock.SNOWY)) {
            return;
        }

        boolean snowy = foliageOverlayState == null && ThinLogOverlay.type(surfaceOverlayState) == ThinLogOverlay.OverlayType.SNOW;
        if (belowState.getValue(SnowyDirtBlock.SNOWY) != snowy) {
            level.setBlock(belowPos, belowState.setValue(SnowyDirtBlock.SNOWY, snowy), 3);
        }
    }

    @Nullable
    private static ThinLogBlockEntity getThinLogBlockEntity(BlockGetter blockGetter, BlockPos pos) {
        BlockEntity blockEntity = blockGetter.getBlockEntity(pos);
        return blockEntity instanceof ThinLogBlockEntity thinLogBlockEntity ? thinLogBlockEntity : null;
    }

    @Nullable
    private static ThinLogBlockEntity getOrCreateThinLogBlockEntity(Level level, BlockPos pos, BlockState state) {
        ThinLogBlockEntity thinLogBlockEntity = getThinLogBlockEntity(level, pos);
        if (thinLogBlockEntity != null) {
            return thinLogBlockEntity;
        }

        if (!(state.getBlock() instanceof ThinLogBlock thinLogBlock)) {
            return null;
        }

        ThinLogBlockEntity created = (ThinLogBlockEntity) thinLogBlock.newBlockEntity(pos, state);
        if (created == null) {
            return null;
        }

        level.setBlockEntity(created);
        return created;
    }

}
