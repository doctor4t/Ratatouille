package dev.doctor4t.ratatouille;

import dev.doctor4t.ratatouille.index.*;
import dev.doctor4t.ratatouille.util.SupporterData;
import dev.upcraft.datasync.api.DataSyncAPI;
import dev.upcraft.datasync.api.SyncToken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Ratatouille implements ModInitializer {
    public static final String MOD_ID = "ratatouille";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final SyncToken<SupporterData> SUPPORTER_DATA_SYNC_TOKEN = DataSyncAPI.register(SupporterData.class, Ratatouille.id("test_data"), SupporterData.CODEC);

    public static Identifier id(String string) {
        return new Identifier(MOD_ID, string);
    }

    @Override
    public void onInitialize() {
        RatatouilleBlocks.initialize();
        RatatouilleBlockEntities.initialize();
        RatatouilleItems.initialize();
        RatatouilleSounds.initialize();
        RatatouilleEntities.initialize();

        UseItemCallback.EVENT.register((player, world, hand) -> {
            var stack = player.getStackInHand(hand);

            // when right clicking with a Netherite Axe, show a message to the player
            if (stack.isOf(Items.NETHERITE_AXE)) {
                if (!world.isClient()) {
                    // get the data for the player
                    Optional<SupporterData> optional = player.datasync$get(SUPPORTER_DATA_SYNC_TOKEN);

                    optional.ifPresentOrElse(data -> {
                        var messageText = Text.literal(data.message()).styled(s -> s.withColor(data.color()));
                        player.sendMessage(Text.literal("Your message is: ").append(messageText));
                    }, () -> player.sendMessage(Text.literal("You do not have any data stored!")));
                }
                return TypedActionResult.success(stack, world.isClient());
            }

            return TypedActionResult.pass(stack);
        });
    }
}