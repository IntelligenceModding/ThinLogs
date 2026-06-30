package de.artemis.thinlogs.common.blocks;

import de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ThinLogGeometry {
    static final Cuboid CORE = new Cuboid(5.0F, 5.0F, 5.0F, 11.0F, 11.0F, 11.0F);
    private static final Map<Direction, Cuboid> ARMS = new EnumMap<>(Direction.class);
    private static final VoxelShape[] SHAPE_CACHE = new VoxelShape[64];

    static {
        ARMS.put(Direction.NORTH, new Cuboid(5.0F, 5.0F, 0.0F, 11.0F, 11.0F, 5.0F));
        ARMS.put(Direction.SOUTH, new Cuboid(5.0F, 5.0F, 11.0F, 11.0F, 11.0F, 16.0F));
        ARMS.put(Direction.WEST, new Cuboid(0.0F, 5.0F, 5.0F, 5.0F, 11.0F, 11.0F));
        ARMS.put(Direction.EAST, new Cuboid(11.0F, 5.0F, 5.0F, 16.0F, 11.0F, 11.0F));
        ARMS.put(Direction.DOWN, new Cuboid(5.0F, 0.0F, 5.0F, 11.0F, 5.0F, 11.0F));
        ARMS.put(Direction.UP, new Cuboid(5.0F, 11.0F, 5.0F, 11.0F, 16.0F, 11.0F));
    }

    private ThinLogGeometry() {
    }

    public static List<Cuboid> cuboids(BlockState state) {
        List<Cuboid> cuboids = new ArrayList<>();
        cuboids.add(CORE);

        for (Direction direction : Direction.values()) {
            if (state.getValue(ModBlockStateProperties.connection(direction))) {
                cuboids.add(ARMS.get(direction));
            }
        }

        return cuboids;
    }

    public static VoxelShape shape(BlockState state) {
        int mask = connectionMask(state);
        VoxelShape cached = SHAPE_CACHE[mask];
        if (cached != null) {
            return cached;
        }

        VoxelShape shape = CORE.toShape();
        for (Direction direction : Direction.values()) {
            if ((mask & (1 << direction.get3DDataValue())) != 0) {
                shape = Shapes.join(shape, ARMS.get(direction).toShape(), BooleanOp.OR);
            }
        }

        SHAPE_CACHE[mask] = shape;
        return shape;
    }

    public static VoxelShape surfaceOverlayShape(BlockState state, float heightPixels) {
        VoxelShape shape = Shapes.empty();
        for (Cuboid cuboid : cuboids(state)) {
            shape = Shapes.join(
                    shape,
                    Block.box(
                            cuboid.minX(),
                            cuboid.maxY(),
                            cuboid.minZ(),
                            cuboid.maxX(),
                            Math.min(16.0F, cuboid.maxY() + heightPixels),
                            cuboid.maxZ()
                    ),
                    BooleanOp.OR
            );
        }
        return shape;
    }

    static int connectionMask(BlockState state) {
        int mask = 0;
        for (Direction direction : Direction.values()) {
            if (state.getValue(ModBlockStateProperties.connection(direction))) {
                mask |= 1 << direction.get3DDataValue();
            }
        }
        return mask;
    }

    public record Cuboid(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        VoxelShape toShape() {
            return Block.box(minX, minY, minZ, maxX, maxY, maxZ);
        }
    }
}
