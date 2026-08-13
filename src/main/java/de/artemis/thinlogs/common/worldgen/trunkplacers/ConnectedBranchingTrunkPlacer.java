package de.artemis.thinlogs.common.worldgen.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.artemis.thinlogs.common.registration.ModTrunkPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class ConnectedBranchingTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<ConnectedBranchingTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> trunkPlacerParts(instance)
                    .and(instance.group(
                            IntProviders.POSITIVE_CODEC.fieldOf("branch_steps").forGetter(placer -> placer.branchSteps),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("branch_probability").forGetter(placer -> placer.branchProbability),
                            IntProviders.NON_NEGATIVE_CODEC.fieldOf("horizontal_length").forGetter(placer -> placer.horizontalLength)
                    ))
                    .apply(instance, ConnectedBranchingTrunkPlacer::new)
    );

    private final IntProvider branchSteps;
    private final float branchProbability;
    private final IntProvider horizontalLength;

    public ConnectedBranchingTrunkPlacer(
            int baseHeight,
            int heightRandA,
            int heightRandB,
            IntProvider branchSteps,
            float branchProbability,
            IntProvider horizontalLength
    ) {
        super(baseHeight, heightRandA, heightRandB);
        this.branchSteps = branchSteps;
        this.branchProbability = branchProbability;
        this.horizontalLength = horizontalLength;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.CONNECTED_BRANCHING.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int freeTreeHeight,
            BlockPos pos,
            TreeConfiguration config
    ) {
        placeBelowTrunkBlock(level, blockSetter, random, pos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = Lists.newArrayList();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int step = 0; step < freeTreeHeight; step++) {
            int y = pos.getY() + step;
            if (this.placeLog(level, blockSetter, random, mutablePos.set(pos.getX(), y, pos.getZ()), config)
                    && step < freeTreeHeight - 1
                    && random.nextFloat() < this.branchProbability) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                placeBranch(level, blockSetter, random, config, attachments, pos.getX(), y, pos.getZ(), direction);
            }

            if (step == freeTreeHeight - 1) {
                attachments.add(new FoliagePlacer.FoliageAttachment(new BlockPos(pos.getX(), y + 1, pos.getZ()), 0, false));
            }
        }

        return attachments;
    }

    private void placeBranch(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            TreeConfiguration config,
            List<FoliagePlacer.FoliageAttachment> attachments,
            int baseX,
            int baseY,
            int baseZ,
            Direction direction
    ) {
        int horizontalMoves = Math.max(1, this.horizontalLength.sample(random));
        int verticalMoves = Math.max(1, this.branchSteps.sample(random));
        int x = baseX;
        int y = baseY;
        int z = baseZ;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int move = 0; move < horizontalMoves; move++) {
            x += direction.getStepX();
            z += direction.getStepZ();
            if (this.placeLog(level, blockSetter, random, mutablePos.set(x, y, z), config)) {
                attachments.add(new FoliagePlacer.FoliageAttachment(mutablePos.immutable(), 0, false));
            }

            if (move < verticalMoves) {
                y++;
                if (this.placeLog(level, blockSetter, random, mutablePos.set(x, y, z), config)) {
                    attachments.add(new FoliagePlacer.FoliageAttachment(mutablePos.immutable(), 0, false));
                }
            }
        }

        for (int rise = horizontalMoves; rise < verticalMoves; rise++) {
            y++;
            if (this.placeLog(level, blockSetter, random, mutablePos.set(x, y, z), config)) {
                attachments.add(new FoliagePlacer.FoliageAttachment(mutablePos.immutable(), 0, false));
            }
        }

        BlockPos tip = new BlockPos(x, y + 1, z);
        attachments.add(new FoliagePlacer.FoliageAttachment(tip, 0, false));
        if (y - baseY > 1) {
            attachments.add(new FoliagePlacer.FoliageAttachment(tip.below(2), 0, false));
        }
    }
}
