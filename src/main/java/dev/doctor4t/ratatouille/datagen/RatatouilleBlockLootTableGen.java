package dev.doctor4t.ratatouille.datagen;

import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

import java.util.function.Consumer;
import java.util.function.Function;

public class RatatouilleBlockLootTableGen extends FabricBlockLootTableProvider {

    public RatatouilleBlockLootTableGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        this.addSelfDrop(RatatouilleBlocks.RAT_MAID_PLUSH);
        this.addSelfDrop(RatatouilleBlocks.FOLLY_PLUSH);
        this.addSelfDrop(RatatouilleBlocks.MAUVE_PLUSH);
    }

    protected void addFamily(BlockFamily family) {
        this.addFamily(family, this::addSelfDrop);
    }

    protected void addFamily(BlockFamily family, Consumer<Block> consumer) {
        family.getVariants().values().forEach(consumer);
        consumer.accept(family.getBaseBlock());
    }

    protected void addSelfDrop(Block block) {
        this.addSelfDrop(block, this::drops);
    }

    protected void addSelfDrop(Block block, Function<Block, LootTable.Builder> function) {
        if (block.getHardness() == -1.0f) {
            // Register drops as nothing if block is unbreakable
            this.addDrop(block, dropsNothing());
        } else {
            this.addDrop(block, function);
        }
    }

    protected void addNothingDrop(Block block) {
        this.addDrop(block, dropsNothing());
    }

    protected ConstantLootNumberProvider count(float value) {
        return ConstantLootNumberProvider.create(value);
    }
}
