package dev.doctor4t.ratatouille.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SupporterPlushOnHeadData(String plushName) {
    public static final Codec<SupporterPlushOnHeadData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("plushName").forGetter(SupporterPlushOnHeadData::plushName)
    ).apply(instance, SupporterPlushOnHeadData::new));
}