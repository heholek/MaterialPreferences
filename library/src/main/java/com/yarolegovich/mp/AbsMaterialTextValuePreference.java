package com.yarolegovich.mp;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.yarolegovich.mp.io.StorageModule;
import com.yarolegovich.mp.io.UserInputModule;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.yarolegovich.mp.R.styleable.AbsMaterialTextValuePreference;
import static com.yarolegovich.mp.R.styleable.AbsMaterialTextValuePreference_mp_show_value;

/**
 * Created by yarolegovich on 05.05.2016.
 */
@SuppressWarnings("ResourceType")
public abstract class AbsMaterialTextValuePreference<T> extends AbsMaterialPreference<T> implements UserInputModule.Listener<T>, android.view.View.OnClickListener {
    public static final int NOT_SHOW_VALUE = 0;
    public static final int SHOW_ON_RIGHT = 1;
    public static final int SHOW_ON_BOTTOM = 2;
    private TextView rightValue;
    private int showValueMode;

    public AbsMaterialTextValuePreference(Context context, String defaultValue, String key, UserInputModule userInputModule, StorageModule storageModule, @ShowValueMode int showValueMode) {
        super(context, defaultValue, key, userInputModule, storageModule);
        this.showValueMode = showValueMode;
    }

    public AbsMaterialTextValuePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsMaterialTextValuePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbsMaterialTextValuePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onCollectAttributes(@NonNull AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, AbsMaterialTextValuePreference);
        try {
            showValueMode = ta.getInt(AbsMaterialTextValuePreference_mp_show_value, NOT_SHOW_VALUE);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onViewCreated() {
        rightValue = findViewById(R.id.mp_right_value);
        showNewValueIfNeeded(toRepresentation(getValue()));
        addPreferenceClickListener(this);
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        showNewValueIfNeeded(toRepresentation(getValue()));
    }

    @Override
    public void onInput(@NonNull T value) {
        setValue(value);
    }

    protected abstract CharSequence toRepresentation(T value);

    protected void setRightValue(CharSequence value) {
        rightValue.setVisibility(visibility(value));
        rightValue.setText(value);
    }

    protected void showNewValueIfNeeded(CharSequence value) {
        switch (showValueMode) {
            case SHOW_ON_RIGHT:
                setRightValue(value);
                break;
            case SHOW_ON_BOTTOM:
                setSummary(value);
                break;
        }
    }

    protected boolean hasSummary() {
        return showValueMode != SHOW_ON_BOTTOM && !TextUtils.isEmpty(getSummary());
    }

    @Override
    protected int getLayout() {
        return R.layout.view_text_input_preference;
    }

    @IntDef({NOT_SHOW_VALUE, SHOW_ON_RIGHT, SHOW_ON_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowValueMode {
    }

    public abstract static class Builder<V extends AbsMaterialTextValuePreference<T>, T> extends AbsMaterialPreference.Builder<V, T> {
        protected int showValueMode;

        public Builder(Context context) {
            super(context);
        }

        public Builder<V, T> showValueMode(@ShowValueMode int showValueMode) {
            this.showValueMode = showValueMode;
            return this;
        }
    }
}
