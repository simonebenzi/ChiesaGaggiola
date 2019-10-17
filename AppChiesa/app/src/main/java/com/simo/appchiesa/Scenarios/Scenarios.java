package com.simo.appchiesa.Scenarios;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Scenarios {

    public ArrayList<String> ArrayLights;  // 0(Apertura Chiesa) 1(Funz Rosario) 2(Funz Messa) 3(Funz Solenne)
    public Scenarios(){
        this.ArrayLights = new ArrayList<String>();
    }

    // salva gli stati degli scenari
    public static void saveScenarios(ArrayList<String> arrayScenarios, Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Scenarios", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayScenarios);
        editor.putString("Scenarios", json);
        editor.commit();
    }

    //prende gli stati salvati degli scenari
    public static ArrayList<String> loadScenarios(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Scenarios", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Scenarios", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> arrayScenariosLoaded = gson.fromJson(json, type);
        if (arrayScenariosLoaded == null) {     //se é la prima volta che si avvia l'app vengono creati gli impostazioni
            arrayScenariosLoaded = new ArrayList<String>();
            arrayScenariosLoaded.add(0, "off");
            arrayScenariosLoaded.add(1, "off");
            arrayScenariosLoaded.add(2, "off");
            arrayScenariosLoaded.add(3, "off");

            return arrayScenariosLoaded;
        } else
            return arrayScenariosLoaded;
    }

    public static Bitmap setImageScenarios (String string, Context context)  {

        int intImg;
        Bitmap newImage;

        if(string.contains(" ")){
            string = string.replace(" ","_");
        }
        else
            string = string;


        intImg = context.getResources().getIdentifier("scenario_" +
                string.toLowerCase(), "mipmap", context.getPackageName()); //salva l'id della bitmap corretta

        newImage = BitmapFactory.decodeResource(context.getResources(), intImg);

        return newImage;
    }
}
