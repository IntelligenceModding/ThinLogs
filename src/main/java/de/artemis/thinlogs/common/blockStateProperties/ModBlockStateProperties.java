package de.artemis.thinlogs.common.blockStateProperties;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Map;

public final class ModBlockStateProperties {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final EnumProperty<AnchorFace> ANCHOR_FACE = EnumProperty.create("anchor_face", AnchorFace.class);
    public static final EnumProperty<CoreOrientation> CORE_ORIENTATION = EnumProperty.create("core_orientation", CoreOrientation.class);

    private static final Map<Direction, BooleanProperty> CONNECTIONS = Map.of(
            Direction.NORTH, NORTH,
            Direction.EAST, EAST,
            Direction.SOUTH, SOUTH,
            Direction.WEST, WEST,
            Direction.UP, UP,
            Direction.DOWN, DOWN
    );

    private ModBlockStateProperties() {
    }

    public static BooleanProperty connection(Direction direction) {
        return CONNECTIONS.get(direction);
    }
}
