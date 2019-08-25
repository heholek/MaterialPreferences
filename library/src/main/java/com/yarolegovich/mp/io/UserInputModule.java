package com.yarolegovich.mp.io;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Set;

/**
 * Created by yarolegovich on 05.05.2016.
 */
public interface UserInputModule {

    void showEditTextInput(@NonNull String key, @NonNull CharSequence title, @Nullable CharSequence defaultValue, @NonNull Listener<String> listener);

    void showSingleChoiceInput(@NonNull String key, @NonNull CharSequence title, @NonNull CharSequence[] displayItems, @NonNull CharSequence[] values, int selected, @NonNull Listener<String> listener);

    void showMultiChoiceInput(@NonNull String key, @NonNull CharSequence title, @NonNull CharSequence[] displayItems, @NonNull CharSequence[] values, @NonNull boolean[] defaultSelection, @NonNull Listener<Set<String>> listener);

    interface Factory {
        @NonNull
        UserInputModule create(@NonNull Context context);
    }

    interface Listener<T> {
        void onInput(@NonNull T value);
    }
}
