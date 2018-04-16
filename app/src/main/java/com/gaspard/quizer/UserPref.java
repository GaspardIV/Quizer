package com.gaspard.quizer;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPref {
    private static String LOAD_IMG_TAG = "LOAD_IMAGE";

    public static Boolean getLoadImagePref(Context act) {
        SharedPreferences sharedPref = act.getSharedPreferences("A", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(LOAD_IMG_TAG, true);
    }

    public static void setLoadImagePref(Context act, Boolean load) {
        SharedPreferences sharedPref = act.getSharedPreferences("A", Context.MODE_PRIVATE);
        sharedPref.edit().putBoolean(LOAD_IMG_TAG, load).apply();
    }

}