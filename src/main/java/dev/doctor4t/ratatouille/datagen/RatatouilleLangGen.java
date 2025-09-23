package dev.doctor4t.ratatouille.datagen;

import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RatatouilleLangGen extends FabricLanguageProvider {
    protected RatatouilleLangGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
        RatatouilleItems.registrar.generateLang(wrapperLookup, builder);
        RatatouilleBlocks.registrar.generateLang(wrapperLookup, builder);

        builder.add("subtitles.ratatouille.block.plush_honk", "Plush honks");
        builder.add("options.plush_on_head_cosmetics", "Plush on Head Cosmetics");
        builder.add("options.plush_on_head_cosmetics.plush", "Plush");
        builder.add("tooltip.supporter_only", "Cosmetics are reserved to Ko-Fi and YouTube members only.\nIf you want access to them (and other cool perks), consider supporting!");
    }
}
