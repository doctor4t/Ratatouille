//package dev.doctor4t.ratatouille.datagen;
//
//import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
//import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
//import net.minecraft.registry.RegistryWrapper;
//import net.minecraft.registry.tag.BlockTags;
//
//import java.util.concurrent.CompletableFuture;
//
//public class RatatouilleBlockTagGen extends FabricTagProvider.BlockTagProvider {
//
//    public RatatouilleBlockTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
//        super(output, registriesFuture);
//    }
//
//    @Override
//    protected void configure(RegistryWrapper.WrapperLookup arg) {
//        // todo this was changed from shears to hoe due to the removal of the tag, maybe look into later
//        this.getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
//                .add(RatatouilleBlocks.RAT_MAID_PLUSH)
//                .add(RatatouilleBlocks.FOLLY_PLUSH)
//                .add(RatatouilleBlocks.MAUVE_PLUSH);
//    }
//}
