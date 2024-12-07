package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.block.PlushBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface RatatouilleBlockEntities {

    Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

    static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> blockEntityType) {
        BLOCK_ENTITY_TYPES.put(blockEntityType, Ratatouille.id(name));
        return blockEntityType;
    }

    BlockEntityType<PlushBlockEntity> PLUSH = create("plush", FabricBlockEntityTypeBuilder
            .create(PlushBlockEntity::new)
            .addBlocks(RatatouilleBlocks.RAT_MAID_PLUSH, RatatouilleBlocks.FOLLY_PLUSH, RatatouilleBlocks.MAUVE_PLUSH)
            .build());

    static void initialize() {
        BLOCK_ENTITY_TYPES.forEach((blockEntityType, id) -> Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType));
    }


}
