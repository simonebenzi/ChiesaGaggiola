package com.simo.appchiesa.LightCircuits;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class LightCircuits {

    public ArrayList<String> ArrayLights;     // 0(CH.4) 1(CH.5) 2(CH.6a) 3(CH.6b) 4(CH.7) 5(CH.8) 6(CH.9)  7(CH.10) 8(CH.11) 9(CH.12a) 10(CH.12b) 11(CH.13) 12(CH.14) 13(CH.15) 14(CH.3)

    public LightCircuits(){
        this.ArrayLights = new ArrayList<String>();
    }

    // salva gli stati delle luci
    public static void saveLights(ArrayList<String> arrayLights, Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Lights", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayLights);
        editor.putString("Lights", json);
        editor.commit();

    }

    //prende gli stati salvati delle luci
    public static ArrayList<String> loadLights(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Lights", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Lights", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> arrayLightsLoaded = gson.fromJson(json, type);
        if (arrayLightsLoaded == null) {     //se é la prima volta che si avvia l'app vengono creati gli impostazioni
            arrayLightsLoaded = new ArrayList<String>();
            arrayLightsLoaded.add(0, "off");
            arrayLightsLoaded.add(1, "off");
            arrayLightsLoaded.add(2, "off");
            arrayLightsLoaded.add(3, "off");
            arrayLightsLoaded.add(4, "off");
            arrayLightsLoaded.add(5, "off");
            arrayLightsLoaded.add(6, "off");
            arrayLightsLoaded.add(7, "off");
            arrayLightsLoaded.add(8, "off");
            arrayLightsLoaded.add(9, "off");
            arrayLightsLoaded.add(10, "off");
            arrayLightsLoaded.add(11, "off");
            arrayLightsLoaded.add(12, "off");
            arrayLightsLoaded.add(13, "off");
            arrayLightsLoaded.add(14, "off");

            return arrayLightsLoaded;
        } else
            return arrayLightsLoaded;
    }

    public static Bitmap setImageLights (String string, Context context)  {

        int intImg;
        Bitmap newImage;

        if(string.contains(" ")){
            string = string.replace(" ","_");
        }
        else
            string = string;


        intImg = context.getResources().getIdentifier("luce_" +
                string.toLowerCase(), "mipmap", context.getPackageName()); //salva l'id della bitmap corretta

        newImage = BitmapFactory.decodeResource(context.getResources(), intImg);

        return newImage;
    }
}
