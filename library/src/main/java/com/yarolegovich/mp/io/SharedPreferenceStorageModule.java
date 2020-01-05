package com.yarolegovich.mp.io;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Set;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public class SharedPreferenceStorageModule implements StorageModule {
    private SharedPreferences prefs;

    public SharedPreferenceStorageModule(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void saveBoolean(@NonNull String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    @Override
    public void saveString(@NonNull String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    @Override
    public void saveInt(@NonNull String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    @Override
    public void saveStringSet(@NonNull String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultVal) {
        return prefs.getBoolean(key, defaultVal);
    }

    @Override
    public String getString(@NonNull String key, String defaultVal) {
        return prefs.getString(key, defaultVal);
    }

    @Override
    public int getInt(@NonNull String key, int defaultVal) {
        return prefs.getInt(key, defaultVal);
    }

    @Override
    public Set<String> getStringSet(@NonNull String key, Set<String> defaultVal) {
        return prefs.getStringSet(key, defaultVal);
    }
}
