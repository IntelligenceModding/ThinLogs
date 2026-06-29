package de.artemis.thinlogs.common.blockStateProperties;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum AnchorFace implements StringRepresentable {
    NONE("none", null),
    DOWN("down", Direction.DOWN),
    UP("up", Direction.UP),
    NORTH("north", Direction.NORTH),
    SOUTH("south", Direction.SOUTH),
    WEST("west", Direction.WEST),
    EAST("east", Direction.EAST);

    private final String name;
    private final Direction direction;

    AnchorFace(String name, Direction direction) {
        this.name = name;
        this.direction = direction;
    }

    public static AnchorFace fromDirection(Direction direction) {
        return switch (direction) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }

    public Direction direction() {
        return direction;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
