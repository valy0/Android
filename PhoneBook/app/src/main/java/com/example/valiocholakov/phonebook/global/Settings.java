package com.example.valiocholakov.phonebook.global;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by valiocholakov on 10/19/16.
 */

public class Settings {
    private Context mContext;
    private static Settings settings;

    public static Settings getInstance(Context context) {
        if (settings == null) {
            settings = new Settings(context);
        }

        return settings;
    }

    public Settings(Context context) {
        this.mContext = context;
    }

    boolean addCounties() {
        SharedPreferences preferences = getSharedPreferences();
        boolean addCounties = !preferences.getBoolean(Constants.ADDED_COUNTRIES , false);

        if (addCounties) {
            preferences.edit().putBoolean(Constants.ADDED_COUNTRIES, true);
        }

        return addCounties ;
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences("com.example.valiocholakov.phonebook", Context.MODE_PRIVATE);
    }
}
