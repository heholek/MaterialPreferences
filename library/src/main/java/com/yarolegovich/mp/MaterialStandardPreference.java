package com.yarolegovich.mp;

import android.content.Context;
import android.util.AttributeSet;

import com.yarolegovich.mp.io.StorageModule;
import com.yarolegovich.mp.io.UserInputModule;

import androidx.annotation.NonNull;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public class MaterialStandardPreference extends AbsMaterialPreference<Void> {

    public MaterialStandardPreference(Context context) {
        super(context, null);
    }

    public MaterialStandardPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialStandardPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialStandardPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MaterialStandardPreference(Context context, String defaultValue, String key, UserInputModule userInputModule, StorageModule storageModule) {
        super(context, defaultValue, key, userInputModule, storageModule);
        init(null);
    }

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public void setValue(Void value) {
        super.setValue(value);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_standard_preference;
    }

    public static class Builder extends AbsMaterialPreference.Builder<MaterialStandardPreference, String> {

        public Builder(Context context) {
            super(context);
        }

        @NonNull
        @Override
        public MaterialStandardPreference build() {
            return new MaterialStandardPreference(context, defaultValue, key, userInputModule, storageModule);
        }
    }
}
