package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.block.PlushBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class RatatouilleBlocks {
    protected static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();

    // Plush
    public static final Block RAT_MAID_PLUSH = createWithItem("rat_maid_plush", new PlushBlock(AbstractBlock.Settings.copy(Blocks.GRAY_WOOL).nonOpaque()));
    public static final Block FOLLY_PLUSH = createWithItem("folly_plush", new PlushBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).nonOpaque()));
    public static final Block MAUVE_PLUSH = createWithItem("mauve_plush", new PlushBlock(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL).nonOpaque()));

    protected static <T extends Block> T create(String name, T block) {
        BLOCKS.put(block, Ratatouille.id(name));
        return block;
    }

    protected static <T extends Block> T createWithItem(String name, T block) {
        return createWithItem(name, block, new FabricItemSettings());
    }

    protected static <T extends Block> T createWithItem(String name, T block, FabricItemSettings settings) {
        return createWithItem(name, block, b -> new BlockItem(b, settings));
    }

    protected static <T extends Block> T createWithItem(String name, T block, Function<T, BlockItem> itemGenerator) {
        RatatouilleItems.create(name, itemGenerator.apply(block));
        return RatatouilleBlocks.create(name, block);
    }

    public static void initialize() {
        BLOCKS.forEach((block, id) -> Registry.register(Registries.BLOCK, id, block));
    }

}
