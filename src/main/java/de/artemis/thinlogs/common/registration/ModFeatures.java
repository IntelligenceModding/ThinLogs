package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.common.worldgen.feature.ThinTreeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class ModFeatures {
    public static final DeferredHolder<com.mojang.serialization.MapCodec<? extends Feature>, com.mojang.serialization.MapCodec<ThinTreeFeature>> THIN_TREE = Registration.FEATURES.register(
            "thin_tree",
            () -> ThinTreeFeature.CODEC
    );

    private ModFeatures() {
    }

    public static void register() {
    }
}
