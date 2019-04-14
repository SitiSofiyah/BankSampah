package com.example.intel.fireapp.Account.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.intel.fireapp.Account.Utils.PreferencesUtility.LOGGED_IN_ANGGOTA;
import static com.example.intel.fireapp.Account.Utils.PreferencesUtility.LOGGED_IN_PK;
import static com.example.intel.fireapp.Account.Utils.PreferencesUtility.LOGGED_IN_TR;
import static com.example.intel.fireapp.Account.Utils.PreferencesUtility.id;

public class SaveSharedPreference {
    static String nama;
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setLoggedInPK(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PK, loggedIn);
        editor.apply();
    }

    public static void setLoggedInTR(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_TR, loggedIn);
        editor.apply();
    }

    public static void setLoggedInAnggota(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_ANGGOTA, loggedIn);
        editor.apply();
    }


    public static boolean getLoggedStatusPK(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PK, false);
    }

    public static boolean getLoggedStatusTR(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_TR, false);
    }

    public static boolean getLoggedStatusAnggota(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_ANGGOTA, false);
    }

    public static void setId(Context context, String name) {
        nama = name;
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(id, name);
        editor.apply();
    }


    public static String getId(Context context) {
        return getPreferences(context).getString(id, nama);
    }
}
