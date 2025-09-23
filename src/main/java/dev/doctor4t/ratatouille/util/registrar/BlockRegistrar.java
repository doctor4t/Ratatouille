package dev.doctor4t.ratatouille.util.registrar;

import dev.doctor4t.ratatouille.util.TextUtils;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.function.Function;

public class BlockRegistrar extends Registrar<Block> {
    final ItemRegistrar itemRegistrar;

    public BlockRegistrar(String modId) {
        super(modId, Registries.BLOCK);
        itemRegistrar = new ItemRegistrar(namespace);
    }

    public <T extends Block> T createWithItem(String name, T block) {
        return createWithItem(name, block, new Item.Settings());
    }

    public <T extends Block> T createWithItem(String name, T block, Item.Settings settings) {
        return createWithItem(name, block, b -> new BlockItem(b, settings));
    }

    public <T extends Block> T createWithItem(String name, T block, Function<T, BlockItem> itemGenerator) {
        itemRegistrar.create(name, itemGenerator.apply(block));
        return (T) create(name, block);
    }

    public <T extends Block> T createWithItem(String name, T block, RegistryKey<ItemGroup>... itemGroups) {
        return createWithItem(name, block, b -> new BlockItem(b, new Item.Settings()), itemGroups);
    }

    public <T extends Block> T createWithItem(String name, T block, Item.Settings settings, RegistryKey<ItemGroup>... itemGroups) {
        return createWithItem(name, block, b -> new BlockItem(b, settings), itemGroups);
    }

    public <T extends Block> T createWithItem(String name, T block, Function<T, BlockItem> itemGenerator, RegistryKey<ItemGroup>... itemGroups) {
        itemRegistrar.create(name, itemGenerator.apply(block), itemGroups);
        return (T) create(name, block);
    }

    @Override
    public void registerEntries() {
        super.registerEntries();
        itemRegistrar.registerEntries();
    }

    @Override
    public void generateLang(RegistryWrapper.WrapperLookup wrapperLookup, FabricLanguageProvider.TranslationBuilder builder) {
        TO_REGISTER.forEach((t, identifier) -> builder.add(t, TextUtils.formatValueString(identifier.getPath())));
    }
}
