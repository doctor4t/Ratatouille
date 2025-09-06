package dev.doctor4t.ratatouille.supporter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PlushOnHeadSupporterData(String plush) {
    public static final Codec<PlushOnHeadSupporterData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("plush").forGetter(PlushOnHeadSupporterData::plush)
    ).apply(instance, PlushOnHeadSupporterData::new));
}