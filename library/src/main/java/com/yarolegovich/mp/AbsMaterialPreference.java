package com.yarolegovich.mp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yarolegovich.mp.io.MaterialPreferences;
import com.yarolegovich.mp.io.StorageModule;
import com.yarolegovich.mp.io.UserInputModule;
import com.yarolegovich.mp.util.CompositeClickListener;
import com.yarolegovich.mp.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public abstract class AbsMaterialPreference<T> extends LinearLayout {
    private final List<PreferenceValueChangedListener<T>> preferenceValueChangedListeners = new ArrayList<>();
    protected String defaultValue;
    protected String key;
    protected UserInputModule userInputModule;
    protected StorageModule storageModule;
    private TextView title;
    private TextView summary;
    private ImageView icon;
    private CompositeClickListener compositeClickListener;

    public AbsMaterialPreference(Context context, String defaultValue, String key, UserInputModule userInputModule, StorageModule storageModule) {
        super(context);
        this.defaultValue = defaultValue;
        this.key = key;
        this.userInputModule = userInputModule;
        this.storageModule = storageModule;
    }

    public AbsMaterialPreference(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AbsMaterialPreference(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AbsMaterialPreference(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected void init(@Nullable AttributeSet attrs) {
        userInputModule = MaterialPreferences.getUserInputModule(getContext());
        storageModule = MaterialPreferences.getStorageModule(getContext());

        CompositeClickListener compositeClickListener = new CompositeClickListener();
        setOnClickListener(compositeClickListener);
        this.compositeClickListener = compositeClickListener;

        String titleText = null;
        String summaryText = null;
        Drawable iconDrawable = null;
        int iconTintColor = -1;

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AbsMaterialPreference);
            try {
                key = ta.getString(R.styleable.AbsMaterialPreference_mp_key);
                defaultValue = ta.getString(R.styleable.AbsMaterialPreference_mp_default_value);

                titleText = ta.getString(R.styleable.AbsMaterialPreference_mp_title);
                summaryText = ta.getString(R.styleable.AbsMaterialPreference_mp_summary);
                iconDrawable = ta.getDrawable(R.styleable.AbsMaterialPreference_mp_icon);

                iconTintColor = ta.getColor(R.styleable.AbsMaterialPreference_mp_icon_tint, -1);
            } finally {
                ta.recycle();
            }
            onCollectAttributes(attrs);
        }

        onConfigureSelf();

        inflate(getContext(), getLayout(), this);

        title = findViewById(R.id.mp_title);
        summary = findViewById(R.id.mp_summary);
        icon = findViewById(R.id.mp_icon);

        setTitle(titleText);
        setSummary(summaryText);
        setIcon(iconDrawable);

        if (iconTintColor != -1)
            setIconColor(iconTintColor);

        onViewCreated();
    }

    public abstract T getValue();

    @CallSuper
    public void setValue(T value) {
        for (PreferenceValueChangedListener<T> listener : preferenceValueChangedListeners)
            listener.onValueChanged(value);
    }

    public void setTitle(@StringRes int textRes) {
        setTitle(string(textRes));
    }

    public void setSummary(@StringRes int textRes) {
        setSummary(string(textRes));
    }

    public void setIcon(@DrawableRes int drawableRes) {
        setIcon(drawable(drawableRes));
    }

    public void setIcon(Drawable iconDrawable) {
        icon.setVisibility(iconDrawable != null ? VISIBLE : GONE);
        icon.setImageDrawable(iconDrawable);
    }

    public void setIconColorRes(@ColorRes int colorRes) {
        icon.setColorFilter(color(colorRes));
    }

    public void setIconColor(@ColorInt int color) {
        icon.setColorFilter(color);
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public void setTitle(CharSequence text) {
        title.setVisibility(visibility(text));
        title.setText(text);
    }

    public String getSummary() {
        return summary.getText().toString();
    }

    public void setSummary(CharSequence text) {
        summary.setVisibility(visibility(text));
        summary.setText(text);
    }

    /*
     * Returns index of listener. Index may change, so better do not rely heavily on it.
     * It is not a key.
     */
    public int addPreferenceClickListener(View.OnClickListener listener) {
        return compositeClickListener.addListener(listener);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (compositeClickListener == null) {
            super.setOnClickListener(l);
        } else {
            compositeClickListener.addListener(l);
        }
    }

    public void removePreferenceClickListener(View.OnClickListener listener) {
        compositeClickListener.removeListener(listener);
    }

    public void removePreferenceClickListener(int index) {
        compositeClickListener.removeListener(index);
    }

    public void setUserInputModule(UserInputModule userInputModule) {
        this.userInputModule = userInputModule;
    }

    public void setStorageModule(StorageModule storageModule) {
        this.storageModule = storageModule;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    @LayoutRes
    protected abstract int getLayout();

    /*
     * Template methods
     */
    protected void onCollectAttributes(@NonNull AttributeSet attrs) {

    }

    protected void onConfigureSelf() {
        setBackgroundResource(Utils.clickableBackground(getContext()));
        int padding = Utils.dpToPixels(getContext(), 16);
        setPadding(padding, padding, padding, padding);
        setGravity(Gravity.CENTER_VERTICAL);
        setClickable(true);
    }

    protected void onViewCreated() {

    }

    protected String string(@StringRes int resId) {
        return getContext().getString(resId);
    }

    protected Drawable drawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    protected int color(@ColorRes int colorRes) {
        return ContextCompat.getColor(getContext(), colorRes);
    }

    protected int visibility(CharSequence text) {
        return !TextUtils.isEmpty(text) ? VISIBLE : GONE;
    }

    public void addPreferenceValueListener(PreferenceValueChangedListener<T> listener) {
        preferenceValueChangedListeners.add(listener);
    }

    public interface PreferenceValueChangedListener<T> {
        void onValueChanged(T value);
    }

    public static abstract class Builder<V, T> {
        protected final Context context;
        protected StorageModule storageModule;
        protected T defaultValue;
        protected String key;
        protected UserInputModule userInputModule;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder<V, T> defaultValue(T defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder<V, T> key(String key) {
            this.key = key;
            return this;
        }

        public Builder<V, T> storageModule(StorageModule storageModule) {
            this.storageModule = storageModule;
            return this;
        }

        public Builder<V, T> userInputModule(UserInputModule userInputModule) {
            this.userInputModule = userInputModule;
            return this;
        }

        @NonNull
        public abstract V build();
    }
}
