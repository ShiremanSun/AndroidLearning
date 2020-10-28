package com.sunny.student.safesp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * created by sunshuo
 * on 2020/6/2
 */
class SecuritySharedPreference implements SharedPreferences {

    private final SharedPreferences mSharedPreferences;

    //清空存储的内容
    public static void clear(Context context, String name) {
        SecuritySharedPreference preferences = new SecuritySharedPreference(context, name, Context.MODE_PRIVATE);
        SecuritySharedPreference.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * constructor
     * @param context should be ApplicationContext not activity
     * @param name file name
     * @param mode context mode
     */
    public SecuritySharedPreference(Context context, String name, int mode){
        if (TextUtils.isEmpty(name)){
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            mSharedPreferences =  context.getSharedPreferences(name, mode);
        }
    }


    /**
     * 加密
     * @return cipherText base64
     */
    private String encryptPreference(String plainText){
        return EncryptUtil.getInstance().encrypt(plainText);
    }

    /**
     * decrypt function
     * @return plainText
     */
    private String decryptPreference(String cipherText){
        return EncryptUtil.getInstance().decrypt(cipherText);
    }


    @Override
    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        //先对key加密，再对value解密
        final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
        return encryptValue == null ? defValue : decryptPreference(encryptValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        final Set<String> encryptSet = mSharedPreferences.getStringSet(encryptPreference(key), null);
        if (encryptSet == null){
            return defValues;
        }
        final Set<String> decryptSet = new HashSet<>();
        for (String encryptValue : encryptSet){
            decryptSet.add(decryptPreference(encryptValue));
        }
        return decryptSet;
    }

    @Override
    public int getInt(String key, int defValue) {
        final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
        if (encryptValue == null) {
            return defValue;
        }
        try {
            return Integer.parseInt(decryptPreference(encryptValue));
        } catch (Exception e) {
            return defValue;
        }
    }

    @Override
    public long getLong(String key, long defValue) {
        final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
        if (encryptValue == null) {
            return defValue;
        }
        try {
            return Long.parseLong(decryptPreference(encryptValue));
        } catch (Exception e) {
            return defValue;
        }

    }

    @Override
    public float getFloat(String key, float defValue) {
        final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
        if (encryptValue == null) {
            return defValue;
        }
        try {
            return Float.parseFloat(decryptPreference(encryptValue));
        } catch (Exception e) {
            return defValue;
        }

    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
        if (encryptValue == null) {
            return defValue;
        }
        try {
            return Boolean.parseBoolean(decryptPreference(encryptValue));
        } catch (Exception e) {
            return defValue;
        }
    }

    @Override
    public boolean contains(String key) {
        return mSharedPreferences.contains(encryptPreference(key));
    }

    @Override
    public Editor edit() {
        return new SecurityEditor();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
    public final class SecurityEditor implements Editor {
        private Editor mEditor;
        /**
         * constructor
         */
        @SuppressLint("CommitPrefEdits")
        private SecurityEditor(){
            mEditor = mSharedPreferences.edit();
        }
        @Override
        public Editor putString(String key, @Nullable String value) {
            mEditor.putString(encryptPreference(key), encryptPreference(value));
            return this;
        }

        @Override
        public Editor putStringSet(String key, @Nullable Set<String> values) {
            final Set<String> encryptSet = new HashSet<>();
            if (values != null) {
                for (String value : values){
                    encryptSet.add(encryptPreference(value));
                }
            }
            mEditor.putStringSet(encryptPreference(key), encryptSet);
            return this;
        }

        @Override
        public Editor putInt(String key, int value) {
            mEditor.putString(encryptPreference(key), encryptPreference(Integer.toString(value)));
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            mEditor.putString(encryptPreference(key), encryptPreference(Long.toString(value)));
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            mEditor.putString(encryptPreference(key), encryptPreference(Float.toString(value)));
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            mEditor.putString(encryptPreference(key), encryptPreference(Boolean.toString(value)));
            return this;
        }

        @Override
        public Editor remove(String key) {
            mEditor.remove(encryptPreference(key));
            return this;
        }

        @Override
        public Editor clear() {
            mEditor.clear();
            return this;
        }

        @Override
        public boolean commit() {
            return mEditor.commit();
        }

        @Override
        public void apply() {
            mEditor.apply();
        }
    }
}
