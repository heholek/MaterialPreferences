package com.yarolegovich.lovelyuserinput;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
import com.yarolegovich.mp.io.UserInputModule;

import java.util.HashMap;
import java.util.Map;

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
    private int theme = 0;

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

    @NonNull
    @Override
    public UserInputModule create(@NonNull Context context) {
        return new LovelyInputModule(context, theme)
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

        @NonNull
        public Builder setTopColor(@ColorInt int color, @ColorInt int iconTint) {
            factory.color = color;
            factory.tint = iconTint;
            return this;
        }

        @NonNull
        public Builder setTheme(@StyleRes int theme) {
            factory.theme = theme;
            return this;
        }

        @NonNull
        public Builder setTopColor(@ColorInt int color) {
            return setTopColor(color, blackForeground(color) ? Color.BLACK : Color.WHITE);
        }

        @NonNull
        public Builder addTitle(@NonNull String key, @NonNull CharSequence title) {
            factory.keyTitleMappings.put(key, title);
            return this;
        }

        @NonNull
        public Builder addMessage(@NonNull String key, @NonNull CharSequence message) {
            factory.keyMessageMappings.put(key, message);
            return this;
        }

        @NonNull
        public Builder addIcon(@NonNull String key, @DrawableRes int icon) {
            factory.keyIconMappings.put(key, icon);
            return this;
        }

        @NonNull
        public Builder addTextFilter(@NonNull String key, @StringRes int errorRes, @NonNull LovelyTextInputDialog.TextFilter filter) {
            factory.keyFilterMappings.put(key, filter);
            factory.keyFilterErrorMappings.put(key, errorRes);
            return this;
        }

        @NonNull
        public LovelyInput build() {
            return factory;
        }
    }
}
