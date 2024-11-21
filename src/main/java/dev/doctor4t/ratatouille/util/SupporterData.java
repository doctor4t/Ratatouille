package dev.doctor4t.ratatouille.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SupporterData(String message, int color) {

    public static final Codec<SupporterData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("message").forGetter(SupporterData::message),
            Codec.INT.fieldOf("color").forGetter(SupporterData::color)
    ).apply(instance, SupporterData::new));
}