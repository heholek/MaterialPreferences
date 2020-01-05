package com.yarolegovich.mp.io;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Set;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public interface StorageModule {

    void saveBoolean(@NonNull String key, boolean value);

    void saveString(@NonNull String key, String value);

    void saveInt(@NonNull String key, int value);

    void saveStringSet(@NonNull String key, Set<String> value);

    boolean getBoolean(@NonNull String key, boolean defaultVal);

    String getString(@NonNull String key, String defaultVal);

    int getInt(@NonNull String key, int defaultVal);

    Set<String> getStringSet(@NonNull String key, Set<String> defaultVal);

    interface Factory {
        @NonNull
        StorageModule create(@NonNull Context context);
    }
}
