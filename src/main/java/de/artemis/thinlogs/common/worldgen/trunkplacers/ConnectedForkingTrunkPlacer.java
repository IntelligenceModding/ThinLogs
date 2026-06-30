package de.artemis.thinlogs.common.worldgen.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.artemis.thinlogs.common.registration.ModTrunkPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class ConnectedForkingTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<ConnectedForkingTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> trunkPlacerParts(instance).apply(instance, ConnectedForkingTrunkPlacer::new)
    );

    public ConnectedForkingTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.CONNECTED_FORKING.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int freeTreeHeight,
            BlockPos pos,
            TreeConfiguration config
    ) {
        setDirtAt(level, blockSetter, random, pos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = Lists.newArrayList();
        Direction primaryDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int bendStart = freeTreeHeight - random.nextInt(4) - 1;
        int bendSteps = 3 - random.nextInt(3);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int x = pos.getX();
        int z = pos.getZ();
        OptionalInt topY = OptionalInt.empty();

        for (int step = 0; step < freeTreeHeight; step++) {
            int y = pos.getY() + step;
            if (step >= bendStart && bendSteps > 0) {
                x += primaryDirection.getStepX();
                z += primaryDirection.getStepZ();
                placeConnector(level, blockSetter, random, mutablePos.set(x, y - 1, z), config);
                bendSteps--;
            }

            if (this.placeLog(level, blockSetter, random, mutablePos.set(x, y, z), config)) {
                topY = OptionalInt.of(y + 1);
            }
        }

        if (topY.isPresent()) {
            attachments.add(new FoliagePlacer.FoliageAttachment(new BlockPos(x, topY.getAsInt(), z), 1, false));
        }

        Direction secondaryDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        if (secondaryDirection != primaryDirection) {
            int branchStart = bendStart - random.nextInt(2) - 1;
            int branchSteps = 1 + random.nextInt(3);
            x = pos.getX();
            z = pos.getZ();
            OptionalInt branchTopY = OptionalInt.empty();

            for (int step = Math.max(1, branchStart); step < freeTreeHeight && branchSteps > 0; step++) {
                int y = pos.getY() + step;
                x += secondaryDirection.getStepX();
                z += secondaryDirection.getStepZ();
                placeConnector(level, blockSetter, random, mutablePos.set(x, y - 1, z), config);
                if (this.placeLog(level, blockSetter, random, mutablePos.set(x, y, z), config)) {
                    branchTopY = OptionalInt.of(y + 1);
                }
                branchSteps--;
            }

            if (branchTopY.isPresent()) {
                attachments.add(new FoliagePlacer.FoliageAttachment(new BlockPos(x, branchTopY.getAsInt(), z), 0, false));
            }
        }

        return attachments;
    }

    private void placeConnector(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            BlockPos connectorPos,
            TreeConfiguration config
    ) {
        this.placeLog(level, blockSetter, random, connectorPos, config);
    }
}
