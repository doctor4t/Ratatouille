package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.block.PlushBlockEntity;
import dev.doctor4t.ratatouille.util.registrar.BlockEntityTypeRegistrar;
import net.minecraft.block.entity.BlockEntityType;

public interface RatatouilleBlockEntities {
    BlockEntityTypeRegistrar registrar = new BlockEntityTypeRegistrar(Ratatouille.MOD_ID);

    BlockEntityType<PlushBlockEntity> PLUSH = registrar.create("plush", PlushBlockEntity::new, RatatouilleBlocks.RAT_MAID_PLUSH, RatatouilleBlocks.FOLLY_PLUSH, RatatouilleBlocks.MAUVE_PLUSH);

    static void initialize() {
        registrar.registerEntries();
    }
}
