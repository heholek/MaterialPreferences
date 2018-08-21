package com.yarolegovich.mp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.yarolegovich.mp.io.StorageModule;
import com.yarolegovich.mp.io.UserInputModule;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public class MaterialEditTextPreference extends AbsMaterialTextValuePreference<String> {

    public MaterialEditTextPreference(Context context, String defaultValue, String key, UserInputModule userInputModule, StorageModule storageModule, @ShowValueMode int showValueMode) {
        super(context, defaultValue, key, userInputModule, storageModule, showValueMode);
        init(null);
    }

    public MaterialEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public String getValue() {
        return storageModule.getString(key, defaultValue);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        storageModule.saveString(key, value);
        showNewValueIfNeeded(value);
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        showNewValueIfNeeded(toRepresentation(getValue()));
    }

    @Override
    public void onClick(View v) {
        userInputModule.showEditTextInput(key, getTitle(), getValue(), this);
    }

    @Override
    protected CharSequence toRepresentation(String value) {
        return value;
    }

    public static class Builder extends AbsMaterialTextValuePreference.Builder<MaterialEditTextPreference, String> {

        public Builder(Context context) {
            super(context);
        }

        @NonNull
        @Override
        public MaterialEditTextPreference build() {
            return new MaterialEditTextPreference(context, defaultValue, key, userInputModule, storageModule, showValueMode);
        }
    }
}
