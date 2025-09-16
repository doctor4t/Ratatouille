package dev.doctor4t.ratatouille.client.util;

import net.minecraft.client.option.SimpleOption;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

public class OptionLocker {
    public static final HashMap<String, Optional<?>> LOCKED_OPTIONS = new HashMap<>();

    public static final HashMap<SimpleOption<?>, String> OPTIONS_KEYS = new HashMap<>();

    public static <T> void override(@NotNull String key, @NotNull T value) {
        LOCKED_OPTIONS.put(key, Optional.of(value));
    }

    public static <T> void overrideOption(@NotNull String option, @NotNull T value) {
        override("options." + option, value);
    }

    public static <T> void overrideSoundCategoryVolume(@NotNull String option, float value) {
        override("soundCategory." + option, value);
    }

    public static Optional<?> getOverriddenValueOf(String key) {
        if (LOCKED_OPTIONS.containsKey(key)) {
            return LOCKED_OPTIONS.get(key);
        } else {
            return Optional.empty();
        }
    }

    public static boolean isOptionLocked(SimpleOption<?> option) {
        return LOCKED_OPTIONS.containsKey(OPTIONS_KEYS.get(option));
    }
}
