package com.simo.appchiesa.Settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Settings {

    public ArrayList<String> arraySettings;

    public Settings(){
        this.arraySettings = new ArrayList<String>();
    }

    // salva le impostazioni
    public static void saveSettings (ArrayList<String> arraySettings, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraySettings);
        editor.putString("Settings", json);
        editor.commit();
    }

    //prende le impostazioni salvate
    public static ArrayList<String> loadSettings(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Settings", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> arraySettingsLoaded = gson.fromJson(json, type);
        if (arraySettingsLoaded == null) {     //se é la prima volta che si avvia l'app vengono create le impostazioni di default
            arraySettingsLoaded = new ArrayList<String>();
            arraySettingsLoaded.add(0, "");
            return arraySettingsLoaded;
        } else
            return arraySettingsLoaded;
    }
}
