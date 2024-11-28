package dev.doctor4t.ratatouille.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class RatatouilleDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();
        pack.addProvider(RatatouilleModelGen::new);
        pack.addProvider(RatatouilleBlockTagGen::new);
        pack.addProvider(RatatouilleLangGen::new);
        pack.addProvider(RatatouilleBlockLootTableGen::new);
        pack.addProvider(RatatouilleRecipeGen::new);
    }
}
