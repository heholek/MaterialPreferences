package com.yarolegovich.mp.io;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Created by yarolegovich on 05.05.2016.
 */
public class MaterialPreferences {
    private static final MaterialPreferences instance = new MaterialPreferences();
    private UserInputModule.Factory userInputModuleFactory;
    private StorageModule.Factory storageModuleFactory;

    private MaterialPreferences() {
        userInputModuleFactory = new StandardUserInputFactory();
        storageModuleFactory = new SharedPrefsStorageFactory(null);
    }

    @NonNull
    public static UserInputModule getUserInputModule(@NonNull Context context) {
        return instance.userInputModuleFactory.create(context);
    }

    @NonNull
    public static StorageModule getStorageModule(@NonNull Context context) {
        return instance.storageModuleFactory.create(context);
    }

    public static void setUserInputModule(@NonNull UserInputModule.Factory factory) {
        instance.userInputModuleFactory = factory;
    }

    public static void setStorageModule(@NonNull StorageModule.Factory factory) {
        instance.storageModuleFactory = factory;
    }

    public static void setDefault() {
        instance.userInputModuleFactory = new StandardUserInputFactory();
        instance.storageModuleFactory = new SharedPrefsStorageFactory(null);
    }

    private static class StandardUserInputFactory implements UserInputModule.Factory {
        @NonNull
        @Override
        public UserInputModule create(@NonNull Context context) {
            return new StandardUserInputModule(context);
        }
    }
}
