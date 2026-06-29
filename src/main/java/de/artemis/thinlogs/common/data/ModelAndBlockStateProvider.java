package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.common.blockStateProperties.CoreOrientation;
import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ThinLogSet;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModelAndBlockStateProvider extends BlockStateProvider {
    public ModelAndBlockStateProvider(PackOutput packOutput, ExistingFileHelper exFileHelper) {
        super(packOutput, ThinLogs.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (ThinLogSet set : ModBlocks.allSets()) {
            branchBlock(set.thinBlock().get(), set.definition().thinId(), set.definition().sideTexture(), set.definition().endTexture());
            branchBlock(set.strippedThinBlock().get(), set.definition().strippedThinId(), set.definition().strippedSideTexture(), set.definition().strippedEndTexture());
        }
    }

    private void branchBlock(Block block, String modelName, ResourceLocation sideTexture, ResourceLocation endTexture) {
        ModelFile coreVertical = models().withExistingParent("block/" + modelName + "_core_vertical", modLoc("generation/branch_core_vertical"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile coreEastWest = models().withExistingParent("block/" + modelName + "_core_east_west", modLoc("generation/branch_core_east_west"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile coreNorthSouth = models().withExistingParent("block/" + modelName + "_core_north_south", modLoc("generation/branch_core_north_south"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile coreJunction = models().withExistingParent("block/" + modelName + "_core_junction", modLoc("generation/branch_core_junction"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile armNorth = models().withExistingParent("block/" + modelName + "_arm_north", modLoc("generation/branch_arm_north"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile armSouth = models().withExistingParent("block/" + modelName + "_arm_south", modLoc("generation/branch_arm_south"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile armEast = models().withExistingParent("block/" + modelName + "_arm_east", modLoc("generation/branch_arm_east"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile armWest = models().withExistingParent("block/" + modelName + "_arm_west", modLoc("generation/branch_arm_west"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile armUp = models().withExistingParent("block/" + modelName + "_arm_up", modLoc("generation/branch_arm_up"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        ModelFile armDown = models().withExistingParent("block/" + modelName + "_arm_down", modLoc("generation/branch_arm_down"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);
        models().withExistingParent("block/" + modelName + "_inventory", modLoc("generation/branch_inventory"))
                .texture("log_side", sideTexture)
                .texture("log_top", endTexture);

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        builder.part().modelFile(coreVertical).addModel().condition(ModBlockStateProperties.CORE_ORIENTATION, CoreOrientation.VERTICAL).end();
        builder.part().modelFile(coreEastWest).addModel().condition(ModBlockStateProperties.CORE_ORIENTATION, CoreOrientation.EAST_WEST).end();
        builder.part().modelFile(coreNorthSouth).addModel().condition(ModBlockStateProperties.CORE_ORIENTATION, CoreOrientation.NORTH_SOUTH).end();
        builder.part().modelFile(coreJunction).addModel().condition(ModBlockStateProperties.CORE_ORIENTATION, CoreOrientation.JUNCTION).end();
        addArm(builder, armNorth, Direction.NORTH);
        addArm(builder, armSouth, Direction.SOUTH);
        addArm(builder, armEast, Direction.EAST);
        addArm(builder, armWest, Direction.WEST);
        addArm(builder, armUp, Direction.UP);
        addArm(builder, armDown, Direction.DOWN);
    }

    private void addArm(MultiPartBlockStateBuilder builder, ModelFile armModel, Direction direction) {
        builder.part()
                .modelFile(armModel)
                .addModel()
                .condition(ModBlockStateProperties.connection(direction), true)
                .end();
    }
}
