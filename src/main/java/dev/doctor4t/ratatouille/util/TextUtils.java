package dev.doctor4t.ratatouille.util;

import net.minecraft.item.Item;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class TextUtils {
    public static String formatValueString(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (ch == '_') {
                ch = ' ';
            }

            if (Character.isWhitespace(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    public static List<Text> getWithLineBreaks(Text text) {
        List<Text> ret = new ArrayList<>();

        String[] strings = text.getString().split("\n");
        for (String string : strings) {
            ret.addAll(Text.literal(string).getWithStyle(text.getStyle()));
        }
        return ret;
    }

    public static String getItemTranslationKey(Item item) {
        return item.getTranslationKey().replace(":", ".");
    }

    public static List<Text> getTooltipForItem(Item item) {
        return getTooltipForItem(item, Style.EMPTY);
    }

    public static List<Text> getTooltipForItem(Item item, Style style) {
        return TextUtils.getWithLineBreaks(Text.translatable(getItemTranslationKey(item) + ".tooltip").setStyle(style));
    }
}
