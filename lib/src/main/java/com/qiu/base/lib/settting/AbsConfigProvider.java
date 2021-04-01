package com.qiu.base.lib.settting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.utils.App;
import com.qiu.base.lib.utils.KeyStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbsConfigProvider {

    private static final String TAG = "config_helper";

    public interface KeyConfig<T> {

        boolean isInit();

        void reset();

        @Nullable
        T getValue();

        void setValue(@Nullable T value);
    }

    public static abstract class AbsConfig<T> implements KeyConfig<T> {

        private static final String TAG = "abs_config";

        @NonNull
        private final String mKey;
        @Nullable
        private final T mDefValue;
        @Nullable
        private T mValue = null;
        private boolean mIsInit;

        public AbsConfig(@NonNull String key, @Nullable T defValue) {
            mKey = key;
            mDefValue = defValue;
        }

        @NonNull
        String getKey() {
            return mKey;
        }

        @SuppressWarnings("unchecked")
        void parserFromJson(@NonNull JSONObject json) {
            try {
                setValue((T) json.get(getKey()));
            } catch (JSONException e) {
                Logger.e(TAG, e.toString());
            }
        }

        void appendToJson(@NonNull JSONObject json) {
            try {
                json.put(mKey, mIsInit ? mValue : mDefValue);
            } catch (JSONException e) {
                Logger.e(TAG, e.toString());
            }
        }

        @Override
        public boolean isInit() {
            return mIsInit;
        }

        @Override
        public void reset() {
            mValue = null;
            mIsInit = false;
        }

        @Nullable
        @Override
        public T getValue() {
            return mIsInit ? mValue : mDefValue;
        }

        @Nullable
        public T getDefValue() {
            return mDefValue;
        }

        @Override
        public void setValue(@Nullable T value) {
            mValue = value;
            mIsInit = true;
        }
    }

    @NonNull
    private final Set<AbsConfig<?>> sSavableConfigSet = new HashSet<>();

    @NonNull
    protected abstract String getStorageKey();

    protected void addSavableConfig(@NonNull AbsConfig<?> config) {
        sSavableConfigSet.add(config);
    }

    protected void addSavableConfigs(@NonNull AbsConfig<?>... configs) {
        sSavableConfigSet.addAll(Arrays.asList(configs));
    }

    public void saveConfigSet() {
        final JSONObject jsonObject = new JSONObject();
        for (AbsConfig<?> config : sSavableConfigSet) {
            config.appendToJson(jsonObject);
        }
        KeyStorage.saveString(getStorageKey(), jsonObject.toString());
    }

    public void loadConfigSet() {
        final String storageJson = KeyStorage.loadString(getStorageKey(), null);
        if (storageJson == null) {
            return;
        }
        try {
            final JSONObject jsonObject = new JSONObject(storageJson);

            for (AbsConfig<?> config : sSavableConfigSet) {
                config.parserFromJson(jsonObject);
            }
        } catch (JSONException e) {
            Logger.e(TAG, e.toString());
        }
    }

    @NonNull
    public List<AbsConfig<?>> getConfigList() {
        return new ArrayList<>(sSavableConfigSet);
    }
}
