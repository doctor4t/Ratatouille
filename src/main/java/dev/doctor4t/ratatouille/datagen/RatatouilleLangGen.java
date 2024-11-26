package dev.doctor4t.ratatouille.datagen;

import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class RatatouilleLangGen extends FabricLanguageProvider {

    protected RatatouilleLangGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add(RatatouilleBlocks.RAT_MAID_PLUSH, "Rat Maid Plush");
        builder.add(RatatouilleBlocks.FOLLY_PLUSH, "Folly Plush");
        builder.add(RatatouilleBlocks.MAUVE_PLUSH, "Mauve Plush");
        builder.add("options.plush_on_head_cosmetics", "Plush on Head Cosmetics");
        builder.add("options.plush_on_head_cosmetics.plush", "Plush on Head Cosmetics");
    }
}
