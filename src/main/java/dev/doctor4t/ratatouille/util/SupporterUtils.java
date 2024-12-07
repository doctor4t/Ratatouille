package dev.doctor4t.ratatouille.util;

import dev.doctor4t.ratatouille.Ratatouille;
import net.minecraft.text.Text;

import java.util.UUID;

public class SupporterUtils {
    public static Text getSupporterStylisedName(UUID playerUuid, Text text) {
        // get the data for the player
        if (Ratatouille.isSupporter(playerUuid)) {
            return text.copy().append(Text.literal("\uE780").styled(style -> style.withColor(0xFF005A)));
        }

        return text;
    }
}
