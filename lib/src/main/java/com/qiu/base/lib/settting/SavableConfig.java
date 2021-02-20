package com.qiu.base.lib.settting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.utils.KeyStorage;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class SavableConfig {
//    @NonNull
//    private static final String TAG = SavableConfig.class.getSimpleName();
//    @NonNull
//    private static final String SP_WEB_CONFIG = "sp_web_config";
//
//    public interface ConfigBase<T> {
//
//        @NonNull
//        public String getKey();
//
//        public void parserFromJson(@NonNull JSONObject json);
//
//        public boolean isInit();
//
//        public void reset();
//
//        public T getValue();
//
//        public void setValue(T value);
//    }
//
//    private static class SaveBuilder {
//
//        @Nullable
//        private JSONObject mJSONObject;
//
//        public SaveBuilder() {
//        }
//
//        @NonNull
//        public SaveBuilder append(@NonNull ConfigBase<?> config) throws JSONException {
//            if (mJSONObject == null) {
//                mJSONObject = new JSONObject();
//            }
//            mJSONObject.put(config.getKey(), config.getValue());
//            return this;
//        }
//
//        @Nullable
//        public String buildJSON() {
//            if (mJSONObject != null) {
//                final String json = mJSONObject.toString();
//                mJSONObject = null;
//                return json;
//            }
//            return null;
//        }
//    }
//
//    public enum BooleanConfig implements ConfigBase<Boolean> {
//        ;
//        @NonNull
//        private final String mKey;
//        private final boolean mDefValue;
//        private boolean mIsInit;
//        private boolean mValue;
//
//        BooleanConfig(@NonNull String key, boolean defValue) {
//            mKey = key;
//            mDefValue = defValue;
//        }
//
//        @NonNull
//        @Override
//        public String getKey() {
//            return mKey;
//        }
//
//        @Override
//        public Boolean getValue() {
//            return mIsInit ? mValue : mDefValue;
//        }
//
//        @Override
//        public void setValue(Boolean value) {
//            mValue = value;
//            mIsInit = true;
//        }
//
//        @Override
//        public boolean isInit() {
//            return mIsInit;
//        }
//
//        @Override
//        public void reset() {
//            mValue = mDefValue;
//            mIsInit = false;
//        }
//
//        @Override
//        public void parserFromJson(@NonNull JSONObject json) {
//            try {
//                if (json.has(mKey)) {
//                    mValue = json.getBoolean(mKey);
//                    mIsInit = true;
//                }
//            } catch (JSONException e) {
//                Logger.e(TAG, e.toString());
//            }
//        }
//    }
//
//    public enum IntConfig implements ConfigBase<Integer> {
//        ;
//
//        @NonNull
//        private final String mKey;
//        private final int mDefValue;
//        private boolean mIsInit;
//        private int mValue;
//
//        IntConfig(@NonNull String key, int defValue) {
//            mKey = key;
//            mDefValue = defValue;
//        }
//
//        @NonNull
//        @Override
//        public String getKey() {
//            return mKey;
//        }
//
//        @NonNull
//        @Override
//        public Integer getValue() {
//            return mIsInit ? mValue : mDefValue;
//        }
//
//        @Override
//        public void setValue(@NonNull Integer value) {
//            mValue = value;
//            mIsInit = true;
//        }
//
//        @Override
//        public boolean isInit() {
//            return mIsInit;
//        }
//
//        @Override
//        public void reset() {
//            mValue = mDefValue;
//            mIsInit = false;
//        }
//
//        @Override
//        public void parserFromJson(@NonNull JSONObject json) {
//            try {
//                if (json.has(mKey)) {
//                    mValue = json.getInt(mKey);
//                    mIsInit = true;
//                }
//            } catch (JSONException e) {
//                Logger.e(TAG, e.toString());
//            }
//        }
//    }
//
//    public enum StringConfig implements ConfigBase<String> {
//        ;
//
//        @NonNull
//        private final String mKey;
//        private final String mDefValue;
//        private boolean mIsInit;
//        private String mValue;
//
//        StringConfig(@NonNull String key, String defValue) {
//            mKey = key;
//            mDefValue = defValue;
//        }
//
//        @NonNull
//        @Override
//        public String getKey() {
//            return mKey;
//        }
//
//        @Override
//        public void parserFromJson(@NonNull JSONObject json) {
//            try {
//                if (json.has(mKey)) {
//                    mValue = json.getString(mKey);
//                    mIsInit = true;
//                }
//            } catch (JSONException e) {
//                Logger.e(TAG, e.toString());
//            }
//        }
//
//        @Override
//        public boolean isInit() {
//            return false;
//        }
//
//        @Override
//        public void reset() {
//            mValue = mDefValue;
//            mIsInit = false;
//        }
//
//        @Override
//        public void setValue(@NonNull String value) {
//            mValue = value;
//            mIsInit = true;
//        }
//
//        @NonNull
//        @Override
//        public String getValue() {
//            return mIsInit ? mValue : mDefValue;
//        }
//    }
//
//    protected SavableConfig() {
//    }
//
//    public void updateConfig(@NonNull String json) {
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(json);
//        } catch (JSONException e) {
//            Logger.e(TAG, e.toString());
//        }
//        if (jsonObject == null) {
//            return;
//        }
//        for (BooleanConfig config : BooleanConfig.values()) {
//            config.parserFromJson(jsonObject);
//        }
//        for (IntConfig config : IntConfig.values()) {
//            config.parserFromJson(jsonObject);
//        }
//        for (StringConfig config : StringConfig.values()) {
//            config.parserFromJson(jsonObject);
//        }
//    }
//
//    public void loadLocalConfig() {
//        final String json = KeyStorage.loadString(SP_WEB_CONFIG, "");
//        updateConfig(json);
//    }
//
//    public void saveConfig() {
//        final String json = toJson();
//        KeyStorage.saveString(SP_WEB_CONFIG, json);
//    }
//
//    @NonNull
//    private String toJson() {
//        JSONObject jsonObject = new JSONObject();
//        for (BooleanConfig config : BooleanConfig.values()) {
//            if (config.isInit()) {
//                try {
//                    jsonObject.put(config.getKey(), config.getValue());
//                } catch (JSONException e) {
//                    Logger.e(TAG, e.toString());
//                }
//            }
//        }
//        for (IntConfig config : IntConfig.values()) {
//            if (config.isInit()) {
//                try {
//                    jsonObject.put(config.getKey(), config.getValue());
//                } catch (JSONException e) {
//                    Logger.e(TAG, e.toString());
//                }
//            }
//        }
//        for (StringConfig config : StringConfig.values()) {
//            if (config.isInit()) {
//                try {
//                    jsonObject.put(config.getKey(), config.getValue());
//                } catch (JSONException e) {
//                    Logger.e(TAG, e.toString());
//                }
//            }
//        }
//        Logger.d(jsonObject.toString());
//        return jsonObject.toString();
//    }
//
//    @NonNull
//    protected abstract String getConfigSpKey();
}

