package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.common.worldgen.trunkplacers.ConnectedBranchingTrunkPlacer;
import de.artemis.thinlogs.common.worldgen.trunkplacers.ConnectedForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class ModTrunkPlacers {
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<ConnectedForkingTrunkPlacer>> CONNECTED_FORKING =
            Registration.TRUNK_PLACER_TYPES.register("connected_forking_trunk_placer", () -> new TrunkPlacerType<>(ConnectedForkingTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<ConnectedBranchingTrunkPlacer>> CONNECTED_BRANCHING =
            Registration.TRUNK_PLACER_TYPES.register("connected_branching_trunk_placer", () -> new TrunkPlacerType<>(ConnectedBranchingTrunkPlacer.CODEC));

    private ModTrunkPlacers() {
    }

    public static void register() {
    }
}
