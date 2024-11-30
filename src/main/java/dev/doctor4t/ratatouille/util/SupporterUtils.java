package dev.doctor4t.ratatouille.util;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.upcraft.datasync.api.util.Entitlements;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
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
