package dev.doctor4t.ratatouille.supporter;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.upcraft.datasync.api.util.Entitlements;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.UUID;

public class SupporterUtils {
    public static Text getSupporterStylisedName(UUID playerUuid, Text text) {
        // get the data for the player
        if (isSupporter(playerUuid)) {
            return text.copy().append(Text.literal("\uE780").styled(style -> style.withColor(0xFF005A)));
        }

        return text;
    }

    public static boolean isSupporter(UUID uuid) {
        Optional<Entitlements> entitlements = Entitlements.token().get(uuid);
        return entitlements.map(value -> value.keys().stream().anyMatch(identifier -> identifier.equals(Ratatouille.PLUSH_ON_HEAD_DATA_ID))).orElse(false);
    }
}
