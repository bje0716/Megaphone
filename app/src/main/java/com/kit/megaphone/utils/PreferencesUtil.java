package com.kit.megaphone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtil {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public PreferencesUtil(Context context) {
        this.sp = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sp.edit();
    }

    public PreferencesUtil(Context context, String name) {
        this.sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public void putBoolean(String name, boolean value) {
        editor.putBoolean(name, value).commit();
    }

    public void putString(String name, String value) {
        editor.putString(name, value).commit();
    }

    public boolean getBoolean(String name, boolean value) {
        return sp.getBoolean(name, value);
    }

    public String getString(String name, String value) {
        return sp.getString(name, value);
    }
}
