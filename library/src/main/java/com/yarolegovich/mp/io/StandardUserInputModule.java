package com.yarolegovich.mp.io;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.yarolegovich.mp.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yarolegovich on 06.05.2016.
 */
public class StandardUserInputModule implements UserInputModule {
    protected final Context context;
    private final int theme;

    public StandardUserInputModule(@NonNull Context context) {
        this.context = context;
        this.theme = 0;
    }

    public StandardUserInputModule(@NonNull Context context, @StyleRes int theme) {
        this.context = context;
        this.theme = theme;
    }

    private MaterialAlertDialogBuilder buildDialog() {
        if (theme == 0) return new MaterialAlertDialogBuilder(context);
        else return new MaterialAlertDialogBuilder(context, theme);
    }

    @Override
    @SuppressLint("InflateParams")
    public void showEditTextInput(@NonNull String key, @NonNull CharSequence title, @Nullable CharSequence defaultValue, @NonNull final Listener<String> listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
        EditText inputField = view.findViewById(R.id.mp_text_input);

        if (defaultValue != null && !TextUtils.isEmpty(defaultValue)) {
            inputField.setText(defaultValue);
            inputField.setSelection(defaultValue.length());
        }

        Dialog dialog = buildDialog()
                .setTitle(title)
                .setView(view)
                .show();

        view.findViewById(R.id.mp_btn_confirm).setOnClickListener(v -> {
            listener.onInput(inputField.getText().toString());
            dialog.dismiss();
        });
    }

    @Override
    public void showSingleChoiceInput(@NonNull String key, @NonNull CharSequence title, @NonNull CharSequence[] displayItems, @NonNull final CharSequence[] values, int selected, @NonNull final Listener<String> listener) {
        buildDialog()
                .setTitle(title)
                .setSingleChoiceItems(displayItems, selected, (dialog, which) -> {
                    String selected1 = values[which].toString();
                    listener.onInput(selected1);
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void showMultiChoiceInput(@NonNull String key, @NonNull CharSequence title, @NonNull CharSequence[] displayItems, @NonNull final CharSequence[] values, @NonNull final boolean[] itemStates, @NonNull final Listener<Set<String>> listener) {
        buildDialog()
                .setTitle(title)
                .setMultiChoiceItems(displayItems, itemStates, (dialog, which, isChecked) -> itemStates[which] = isChecked)
                .setOnDismissListener(dialog -> {
                    Set<String> result = new HashSet<>();
                    for (int i = 0; i < values.length; i++)
                        if (itemStates[i])
                            result.add(values[i].toString());

                    listener.onInput(result);
                })
                .show();
    }
}
