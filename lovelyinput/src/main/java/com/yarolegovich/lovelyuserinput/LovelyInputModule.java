package com.yarolegovich.lovelyuserinput;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.yarolegovich.lovelydialog.AbsLovelyDialog;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
import com.yarolegovich.mp.io.StandardUserInputModule;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yarolegovich on 16.05.2016.
 */
public class LovelyInputModule extends StandardUserInputModule {
    private Map<String, Integer> keyIconMappings;
    private Map<String, CharSequence> keyTitleMapping;
    private Map<String, CharSequence> keyMessageMapping;
    private Map<String, LovelyTextInputDialog.TextFilter> keyFilterMappings;
    private Map<String, Integer> keyFilterErrorMappings;
    private int topColor;
    private int tintColor;

    public LovelyInputModule(@NonNull Context context) {
        super(context);
    }

    public LovelyInputModule(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    @Override
    public void showEditTextInput(@NonNull String key, @NonNull CharSequence title, @Nullable CharSequence defaultValue, @NonNull Listener<String> listener) {
        AbsLovelyDialog dialog = standardInit(new LovelyTextInputDialog(context)
                .setInitialInput(defaultValue != null ? defaultValue.toString() : null)
                .setConfirmButton(android.R.string.ok, listener::onInput), key, title);

        if (dialog != null) dialog.show();
        else super.showEditTextInput(key, title, defaultValue, listener);
    }

    @Override
    public void showSingleChoiceInput(@NonNull String key, @NonNull CharSequence title, @NonNull CharSequence[] displayItems, @NonNull CharSequence[] values, int selected, @NonNull Listener<String> listener) {
        AbsLovelyDialog dialog = standardInit(new LovelyChoiceDialog(context)
                .setItems(displayItems, (position, item) -> listener.onInput(values[position].toString())), key, title);

        if (dialog != null) dialog.show();
        else super.showSingleChoiceInput(key, title, displayItems, values, selected, listener);
    }

    @Override
    public void showMultiChoiceInput(@NonNull String key, @NonNull CharSequence title, @NonNull CharSequence[] displayItems, @NonNull CharSequence[] values, @NonNull boolean[] itemStates, @NonNull Listener<Set<String>> listener) {
        AbsLovelyDialog dialog = standardInit(new LovelyChoiceDialog(context)
                .setItemsMultiChoice(displayItems, itemStates, (positions, items) -> {
                    Set<String> selected = new HashSet<>();
                    for (int position : positions)
                        selected.add(values[position].toString());
                    listener.onInput(selected);
                }), key, title);

        if (dialog != null) dialog.show();
        else super.showMultiChoiceInput(key, title, displayItems, values, itemStates, listener);
    }

    @Nullable
    private AbsLovelyDialog standardInit(@NonNull AbsLovelyDialog dialog, @NonNull String key, @NonNull CharSequence prefTitle) {
        CharSequence title = getTitle(key, prefTitle);
        CharSequence message;
        Integer iconRes;
        if ((message = keyMessageMapping.get(key)) == null || (iconRes = keyIconMappings.get(key)) == null)
            return null;

        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        if (!TextUtils.isEmpty(message)) dialog.setMessage(message);

        if (dialog instanceof LovelyTextInputDialog) {
            if (!keyFilterMappings.containsKey(key) || !keyFilterErrorMappings.containsKey(key))
                return null;

            LovelyTextInputDialog.TextFilter filter = keyFilterMappings.get(key);
            Integer errorRes = keyFilterErrorMappings.get(key);

            if (filter != null && errorRes != null && errorRes != 0)
                ((LovelyTextInputDialog) dialog).setInputFilter(errorRes, filter);
        }

        return dialog.setTopColor(topColor)
                .setIconTintColor(tintColor)
                .setIcon(iconRes);
    }

    @NonNull
    private CharSequence getTitle(@NonNull String key, @NonNull CharSequence prefTitle) {
        CharSequence title;
        return (title = keyTitleMapping.get(key)) == null ? prefTitle : title;
    }

    @NonNull
    public LovelyInputModule setKeyIconMappings(Map<String, Integer> mappings) {
        this.keyIconMappings = mappings;
        return this;
    }

    @NonNull
    public LovelyInputModule setKeyTitleMapping(Map<String, CharSequence> mappings) {
        this.keyTitleMapping = mappings;
        return this;
    }

    @NonNull
    public LovelyInputModule setKeyMessageMapping(Map<String, CharSequence> mappings) {
        this.keyMessageMapping = mappings;
        return this;
    }

    @NonNull
    public LovelyInputModule setKeyFilterMappings(Map<String, LovelyTextInputDialog.TextFilter> mappings) {
        this.keyFilterMappings = mappings;
        return this;
    }

    @NonNull
    public LovelyInputModule setKeyFilterErrorMappings(Map<String, Integer> mappings) {
        this.keyFilterErrorMappings = mappings;
        return this;
    }

    @NonNull
    public LovelyInputModule setTopColor(int topColor) {
        this.topColor = topColor;
        return this;
    }

    @NonNull
    public LovelyInputModule setTintColor(int tintColor) {
        this.tintColor = tintColor;
        return this;
    }
}
