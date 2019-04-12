package com.example.intel.fireapp.Account;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.intel.fireapp.Account.PreferencesUtility.LOGGED_IN_PREF;

public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, String loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static String getLoggedStatus(Context context) {
        return getPreferences(context).getString(LOGGED_IN_PREF,null);
    }
}
