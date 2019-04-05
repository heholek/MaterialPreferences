package com.yarolegovich.mp.io;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.colormode.ColorMode;
import com.yarolegovich.mp.R;

import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by yarolegovich on 06.05.2016.
 */
public class StandardUserInputModule implements UserInputModule {
    protected Context context;

    public StandardUserInputModule(Context context) {
        this.context = context;
    }

    @Override
    @SuppressLint("InflateParams")
    public void showEditTextInput(String key, CharSequence title, CharSequence defaultValue, final Listener<String> listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
        final EditText inputField = view.findViewById(R.id.mp_text_input);

        if (defaultValue != null) {
            inputField.setText(defaultValue);
            inputField.setSelection(defaultValue.length());
        }

        final Dialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .show();
        view.findViewById(R.id.mp_btn_confirm).setOnClickListener(v -> {
            listener.onInput(inputField.getText().toString());
            dialog.dismiss();
        });
    }

    @Override
    public void showSingleChoiceInput(String key, CharSequence title, CharSequence[] displayItems, final CharSequence[] values, int selected, final Listener<String> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(displayItems, selected, (dialog, which) -> {
                    String selected1 = values[which].toString();
                    listener.onInput(selected1);
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void showMultiChoiceInput(String key, CharSequence title, CharSequence[] displayItems, final CharSequence[] values, final boolean[] itemStates, final Listener<Set<String>> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMultiChoiceItems(displayItems, itemStates, (dialog, which, isChecked) -> itemStates[which] = isChecked)
                .setOnDismissListener(dialog -> {
                    Set<String> result = new HashSet<>();
                    for (int i = 0; i < values.length; i++) {
                        if (itemStates[i]) {
                            result.add(values[i].toString());
                        }
                    }
                    listener.onInput(result);
                })
                .show();
    }

    @Override
    public void showColorSelectionInput(String key, CharSequence title, int defaultColor, final Listener<Integer> colorListener) {
        FragmentActivity activity;
        try {
            activity = (FragmentActivity) context;
        } catch (ClassCastException exc) {
            throw new AssertionError(context.getString(R.string.exc_not_frag_activity_subclass));
        }
        String tag = colorListener.getClass().getSimpleName();
        new ChromaDialog.Builder()
                .initialColor(defaultColor)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .onColorSelected(colorListener::onInput)
                .create()
                .show(activity.getSupportFragmentManager(), tag);
    }
}
