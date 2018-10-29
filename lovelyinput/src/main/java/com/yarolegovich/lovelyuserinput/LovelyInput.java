package com.yarolegovich.lovelyuserinput;

import android.content.Context;
import android.graphics.Color;

import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
import com.yarolegovich.mp.io.UserInputModule;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * Created by yarolegovich on 16.05.2016.
 */
public class LovelyInput implements UserInputModule.Factory {
    private final Map<String, Integer> keyIconMappings;
    private final Map<String, CharSequence> keyTitleMappings;
    private final Map<String, CharSequence> keyMessageMappings;
    private final Map<String, LovelyTextInputDialog.TextFilter> keyFilterMappings;
    private final Map<String, Integer> keyFilterErrorMappings;
    private int color;
    private int tint;

    private LovelyInput() {
        keyIconMappings = new HashMap<>();
        keyTitleMappings = new HashMap<>();
        keyMessageMappings = new HashMap<>();
        keyFilterMappings = new HashMap<>();
        keyFilterErrorMappings = new HashMap<>();
    }

    private static boolean blackForeground(@ColorInt int color) {
        return (Color.red(color) * 0.299 + Color.green(color) * 0.587 + Color.blue(color) * 0.114) > 186;
    }

    @Override
    public UserInputModule create(Context context) {
        return new LovelyInputModule(context)
                .setKeyMessageMapping(keyMessageMappings)
                .setKeyTitleMapping(keyTitleMappings)
                .setKeyIconMappings(keyIconMappings)
                .setKeyFilterMappings(keyFilterMappings)
                .setKeyFilterErrorMappings(keyFilterErrorMappings)
                .setTintColor(tint)
                .setTopColor(color);
    }

    public static class Builder {
        private final LovelyInput factory;

        public Builder() {
            factory = new LovelyInput();
        }

        public Builder setTopColor(@ColorInt int color, @ColorInt int iconTint) {
            factory.color = color;
            factory.tint = iconTint;
            return this;
        }

        public Builder setTopColor(@ColorInt int color) {
            return setTopColor(color, blackForeground(color) ? Color.BLACK : Color.WHITE);
        }

        public Builder addTitle(String key, CharSequence title) {
            factory.keyTitleMappings.put(key, title);
            return this;
        }

        public Builder addMessage(String key, CharSequence message) {
            factory.keyMessageMappings.put(key, message);
            return this;
        }

        public Builder addIcon(String key, @DrawableRes int icon) {
            factory.keyIconMappings.put(key, icon);
            return this;
        }

        public Builder addTextFilter(String key, @StringRes int errorRes, LovelyTextInputDialog.TextFilter filter) {
            factory.keyFilterMappings.put(key, filter);
            factory.keyFilterErrorMappings.put(key, errorRes);
            return this;
        }

        public LovelyInput build() {
            return factory;
        }

    }
}
