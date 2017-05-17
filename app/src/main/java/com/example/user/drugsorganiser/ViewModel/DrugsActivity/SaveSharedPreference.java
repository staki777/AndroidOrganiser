package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by DV7 on 2017-05-17.
 */

public class SaveSharedPreference
{
    static final String PREF_USER_ID= "userid";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserID(Context ctx, int userID)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USER_ID, userID);
        editor.commit();
    }

    public static int getUserID(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_USER_ID, -1);
    }

    public static void clearUserID(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
