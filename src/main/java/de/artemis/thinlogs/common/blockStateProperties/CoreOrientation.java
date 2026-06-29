package de.artemis.thinlogs.common.blockStateProperties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CoreOrientation implements StringRepresentable {
    VERTICAL("vertical"),
    EAST_WEST("east_west"),
    NORTH_SOUTH("north_south"),
    JUNCTION("junction");

    private final String name;

    CoreOrientation(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
