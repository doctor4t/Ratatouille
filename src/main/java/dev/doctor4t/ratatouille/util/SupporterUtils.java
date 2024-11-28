package dev.doctor4t.ratatouille.util;

import dev.upcraft.datasync.api.util.Entitlements;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SupporterUtils {
    public static Text getSupporterStylisedName(UUID playerUuid, Text text) {
        // get the data for the player
        Optional<Entitlements> entitlements = Entitlements.token().get(playerUuid);

        if (entitlements.isPresent()) {

            List<Identifier> keys = entitlements.get().keys();

            String prefix = "";
            int color = 0xFFFFFF;
            for (Identifier key : keys) {
                if (key.toString().equals("ratatouille:ratty")) {
                    prefix = "[\uD83D\uDC00] ";
                    color = 0xff005a;
                    break;
                } else if (key.toString().equals("ratatouille:moderator")) {
                    prefix = "[Moderator] ";
                    color = 0x00ff5a;
                    break;
                } else if (key.toString().equals("ratatouille:community")) {
                    prefix = "[Community] ";
                    color = 0xa600cf;
                    break;
                } else if (key.toString().equals("ratatouille:big_rat")) {
                    prefix = "[Big Rat] ";
                    color = 0xffc300;
                    break;
                } else if (key.toString().equals("ratatouille:rat")) {
                    prefix = "[Rat] ";
                    color = 0xff5d5c;
                    break;
                }
            }
            prefix = "\uE780";

            int finalColor = color;
            return text.copy().append(prefix).styled(s -> s.withColor(finalColor));
        }

        return text;
    }

}
