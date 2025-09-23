package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.block.PlushBlock;
import dev.doctor4t.ratatouille.util.registrar.BlockRegistrar;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public interface RatatouilleBlocks {
    BlockRegistrar registrar = new BlockRegistrar(Ratatouille.MOD_ID);

    // Plush
    Block RAT_MAID_PLUSH = registrar.createWithItem("rat_maid_plush", new PlushBlock(AbstractBlock.Settings.copy(Blocks.GRAY_WOOL).nonOpaque()));
    Block FOLLY_PLUSH = registrar.createWithItem("folly_plush", new PlushBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).nonOpaque()));
    Block MAUVE_PLUSH = registrar.createWithItem("mauve_plush", new PlushBlock(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL).nonOpaque()));

    static void initialize() {
        registrar.registerEntries();
    }
}
