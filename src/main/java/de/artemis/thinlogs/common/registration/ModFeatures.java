package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.worldgen.feature.ThinTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class ModFeatures {
    public static final DeferredHolder<Feature<?>, ThinTreeFeature> THIN_TREE = Registration.FEATURES.register(
            "thin_tree",
            () -> new ThinTreeFeature(TreeConfiguration.CODEC)
    );

    private ModFeatures() {
    }

    public static void register() {
    }
}
