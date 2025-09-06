package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

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

    @Override
    public void initialize() {
        super.initialize();
        itemRegistrar.initialize();
    }
}
