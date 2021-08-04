package com.example.fixawy.Share.Session;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class SharedPreferencesConfig implements SharedPreferences {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesConfig(Context context) {
        this.context = context;
        sharedPreferences = (SharedPreferences) context.getSharedPreferences("status", Context.MODE_PRIVATE);
    }

    // Shared Preference Owner Login

    public void writeUserLoginStatus(boolean status) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean("key1", status);
        editor.commit();
    }

    public boolean readUserLoginStatus() {
        boolean status = false;
        status = sharedPreferences.getBoolean("key1", false);
        return status;
    }

    // Shared Preference Worker Login
    public boolean readWorkerLoginStatus() {
        boolean status = false;
        status = sharedPreferences.getBoolean("key2", false);
        return status;
    }

    public void writeWorkerLoginStatus(boolean status) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean("key2", status);
        editor.commit();
    }

    // Shared Preference ShopOwner Login
    public boolean readShopOwnerLoginStatus() {
        boolean status = false;
        status = sharedPreferences.getBoolean("key3", false);
        return status;
    }

    public void writeShopOwnerLoginStatus(boolean status) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean("key3", status);
        editor.commit();
    }
    @Override
    public Map<String, ?> getAll() {
        return null;
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return null;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return null;
    }

    @Override
    public int getInt(String key, int defValue) {
        return 0;
    }

    @Override
    public long getLong(String key, long defValue) {
        return 0;
    }

    @Override
    public float getFloat(String key, float defValue) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return false;
    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    public Editor edit() {
        return null;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }
}
