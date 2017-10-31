package com.ultradevs.ultrakernel.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by ahmedhady on 30/10/17.
 */

public class prefs {
    public static int getInt(String name, int defaults, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(name, defaults);
    }

    public static void putInt(String name, int value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(name, value).apply();
    }

    public static long getLong(String name, long defaults, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(name, defaults);
    }

    public static void putLong(String name, long value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(name, value).apply();
    }

    public static boolean getBoolean(String name, boolean defaults, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(name, defaults);
    }

    public static void putBoolean(String name, boolean value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(name, value).apply();
    }

    public static String getString(String name, String defaults, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(name, defaults);
    }

    public static void putString(String name, String value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(name, value).apply();
    }
    public static void remove(String name, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(name).apply();
    }
}
