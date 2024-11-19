package dev.doctor4t.ratatouille.util;

import dev.doctor4t.ratatouille.entity.PlayerHeadEntity;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;

public class PlayerClientHead {
    public static boolean isOwned(PlayerHeadEntity head) {
        if (MinecraftClient.getInstance().player != null) {
            if (head.getPlayerUuid().isPresent() && head.getPlayerUuid().get() == MinecraftClient.getInstance().player.getUuid()) {
                return true;
            }
            return Objects.equals(head.getPlayerName(), MinecraftClient.getInstance().player.getEntityName());
        }
        return false;
    }
}