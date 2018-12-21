package com.surya432.kamusque;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    SharedPreferences prefs;
    Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setContext(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }
    public void setFirst(Boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.setItFristRun);
        editor.putBoolean(key, input);
        editor.apply();
    }
    public Boolean getFirst(){
        String Key = context.getResources().getString(R.string.setItFristRun);
        return prefs.getBoolean(Key, true);
    }
}
