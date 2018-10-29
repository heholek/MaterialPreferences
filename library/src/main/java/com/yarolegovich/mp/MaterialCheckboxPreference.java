package com.yarolegovich.mp;

import android.content.Context;
import android.util.AttributeSet;

import com.yarolegovich.mp.io.StorageModule;
import com.yarolegovich.mp.io.UserInputModule;

import androidx.annotation.NonNull;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public class MaterialCheckboxPreference extends AbsMaterialCheckablePreference {

    private MaterialCheckboxPreference(Context context, boolean defaultValue, String key, UserInputModule userInputModule, StorageModule storageModule) {
        super(context, defaultValue, key, userInputModule, storageModule);
        init(null);
    }

    public MaterialCheckboxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialCheckboxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialCheckboxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_checkbox_preference;
    }

    public static class Builder extends AbsMaterialPreference.Builder<MaterialCheckboxPreference, Boolean> {
        public Builder(Context context) {
            super(context);
        }

        @Override
        @NonNull
        public MaterialCheckboxPreference build() {
            return new MaterialCheckboxPreference(context, defaultValue, key, userInputModule, storageModule);
        }
    }
}
