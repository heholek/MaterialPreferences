package com.yarolegovich.mp.io;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by yarolegovich on 16.05.2016.
 */
public class SharedPrefsStorageFactory implements StorageModule.Factory {
    private final String preferencesName;

    public SharedPrefsStorageFactory(@Nullable String preferencesName) {
        this.preferencesName = preferencesName;
    }

    @NonNull
    @Override
    public StorageModule create(@NonNull Context context) {
        return new SharedPreferenceStorageModule(preferencesName != null ?
                context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
                : PreferenceManager.getDefaultSharedPreferences(context));
    }
}
