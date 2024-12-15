package dev.doctor4t.ratatouille.datagen;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RatatouilleRecipeGen extends FabricRecipeProvider {
    public RatatouilleRecipeGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                offerRatMaidPlush(this, exporter);
                offerFollyPlush(this, exporter);
                offerMauvePlush(this, exporter);
            }
        };
    }

    @Override
    public String getName() {
        return Ratatouille.MOD_ID;
    }

    public void offerRatMaidPlush(RecipeGenerator generator, RecipeExporter exporter) {
        generator.createShaped(RecipeCategory.DECORATIONS, RatatouilleBlocks.RAT_MAID_PLUSH)
                .input('G', Items.GRAY_WOOL)
                .input('B', Items.BLACK_WOOL)
                .input('P', Items.PINK_WOOL)
                .input('p', Items.PURPLE_WOOL)
                .input('R', Items.RED_WOOL)
                .pattern("PpP")
                .pattern("pGp")
                .pattern("BRB")
                .criterion("has_white_wool", generator.conditionsFromItem(Items.WHITE_WOOL))
                .criterion("has_black_wool", generator.conditionsFromItem(Items.BLACK_WOOL))
                .criterion("has_pink_wool", generator.conditionsFromItem(Items.PINK_WOOL))
                .criterion("has_gray_wool", generator.conditionsFromItem(Items.GRAY_WOOL))
                .criterion("has_red_wool", generator.conditionsFromItem(Items.RED_WOOL))
                .offerTo(exporter);
    }

    public void offerFollyPlush(RecipeGenerator generator, RecipeExporter exporter) {
        generator.createShaped(RecipeCategory.DECORATIONS, RatatouilleBlocks.FOLLY_PLUSH)
                .input('W', Items.WHITE_WOOL)
                .input('B', Items.BLACK_WOOL)
                .input('P', Items.PINK_WOOL)
                .pattern("WWW")
                .pattern("WWW")
                .pattern("BPB")
                .criterion("has_white_wool", generator.conditionsFromItem(Items.WHITE_WOOL))
                .criterion("has_black_wool", generator.conditionsFromItem(Items.BLACK_WOOL))
                .criterion("has_pink_wool", generator.conditionsFromItem(Items.PINK_WOOL))
                .offerTo(exporter);
    }

    public void offerMauvePlush(RecipeGenerator generator, RecipeExporter exporter) {
        generator.createShaped(RecipeCategory.DECORATIONS, RatatouilleBlocks.MAUVE_PLUSH)
                .input('B', Items.BLUE_WOOL)
                .input('P', Items.PINK_WOOL)
                .input('p', Items.PURPLE_WOOL)
                .input('R', Items.RED_WOOL)
                .pattern("PBP")
                .pattern("pPR")
                .pattern("RBR")
                .criterion("has_white_wool", generator.conditionsFromItem(Items.WHITE_WOOL))
                .criterion("has_blue_wool", generator.conditionsFromItem(Items.BLUE_WOOL))
                .criterion("has_pink_wool", generator.conditionsFromItem(Items.PINK_WOOL))
                .criterion("has_red_wool", generator.conditionsFromItem(Items.RED_WOOL))
                .offerTo(exporter);
    }
}