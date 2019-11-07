package com.simo.appchiesa.Arduino;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.simo.appchiesa.ScenariosActivity;

import java.util.ArrayList;

public class ArduinoConnection {
    //declare variable needed
    private String ipAddress;
    private Context context;
    private ArrayList<String> arrayLights;

    // ArduinoConnection constructor
    public ArduinoConnection(Context context, ArrayList<String> arrayLights) {

        this.context = context;
        this.arrayLights = arrayLights;
    }

    public void scenarioActivation(String scenario) {
        if(!ScenariosActivity.arraySettings.get(0).equals("")){
            if(scenario.equals("chiesa")){
                if(ScenariosActivity.arrayLights.get(0).equals("off")){
                    this.lightActivation("ch4");
                }
                if(ScenariosActivity.arrayLights.get(1).equals("off")){
                    this.lightActivation("ch5");
                }
                if(ScenariosActivity.arrayLights.get(6).equals("off")){
                    this.lightActivation("ch9");
                }
                if(ScenariosActivity.arrayLights.get(9).equals("off"))
                    this.lightActivation("ch12a");
            }
            else if(scenario.equals("rosario")){
                if(ScenariosActivity.arrayLights.get(3).equals("off")){
                    this.lightActivation("ch6b");
                }
                if(ScenariosActivity.arrayLights.get(7).equals("off")){
                    this.lightActivation("ch10");
                }
            }
            else if(scenario.equals("messa")){
                if(ScenariosActivity.arrayLights.get(2).equals("off")){
                    this.lightActivation("ch6a");
                }
                if(ScenariosActivity.arrayLights.get(8).equals("off")){
                    this.lightActivation("ch11");
                }
            }
            else if(scenario.equals("solenni")){
                if(ScenariosActivity.arrayLights.get(14).equals("off")){
                    this.lightActivation("ch3");
                }
                if(ScenariosActivity.arrayLights.get(10).equals("off")){
                    this.lightActivation("ch12b");
                }
                if(ScenariosActivity.arrayLights.get(13).equals("off")){
                    this.lightActivation("ch15");
                }
            }
        }
        else {
            Toast.makeText(context, "Inserire Indirizzo IP Arduino!", Toast.LENGTH_LONG).show();
        }
    }

    public void scenarioDeactivation(String scenario) {
        if(!ScenariosActivity.arraySettings.get(0).equals("")){
            if(scenario.equals("chiesa")){
                if(ScenariosActivity.arrayLights.get(0).equals("off")){
                    this.lightDeactivation("ch4");
                }
                if(ScenariosActivity.arrayLights.get(1).equals("off")){
                    this.lightDeactivation("ch5");
                }
                if(ScenariosActivity.arrayLights.get(6).equals("off")){
                    this.lightDeactivation("ch9");
                }
                if(ScenariosActivity.arrayLights.get(9).equals("off")){
                    this.lightDeactivation("ch12a");
                }
            }
            else if(scenario.equals("rosario")){
                if(ScenariosActivity.arrayLights.get(3).equals("off")){
                    this.lightDeactivation("ch6b");
                }
                if(ScenariosActivity.arrayLights.get(7).equals("off"))
                    this.lightDeactivation("ch10");
            }
            else if(scenario.equals("messa")){
                if(ScenariosActivity.arrayLights.get(2).equals("off")){
                    this.lightDeactivation("ch6a");
                }
                if(ScenariosActivity.arrayLights.get(8).equals("off")){
                    this.lightDeactivation("ch11");
                }
            }
            else if(scenario.equals("solenni")){
                if(ScenariosActivity.arrayLights.get(14).equals("off")){
                    this.lightDeactivation("ch3");
                }
                if(ScenariosActivity.arrayLights.get(10).equals("off")){
                    this.lightDeactivation("ch12b");
                }
                if(ScenariosActivity.arrayLights.get(13).equals("off")){
                    this.lightDeactivation("ch15");
                }
            }
        }
        else {
            Toast.makeText(context, "Inserire Indirizzo IP Arduino!", Toast.LENGTH_LONG).show();
        }
    }

    public void lightActivation(String lightCircuit){
        if(!ScenariosActivity.arraySettings.get(0).equals("")){
            ipAddress = ScenariosActivity.arraySettings.get(0);
            // Instantiate the RequestQueue
            RequestQueue queue = Volley.newRequestQueue(this.context);
            String url = "http://" + ipAddress + "/" + lightCircuit + "/on:80";

            // Request a string response from the provided URL
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("onResponse", response.substring(0, 50));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "That didn't work!");
                }
            });
            // Add the request to the RequestQueue.
            queue.add(request);
        }
        else {
            Toast.makeText(context, "Inserire Indirizzo IP Arduino!", Toast.LENGTH_LONG).show();
        }
    }

    public void lightDeactivation(String lightCircuit){
        if(!ScenariosActivity.arraySettings.get(0).equals("")){
            ipAddress = ScenariosActivity.arraySettings.get(0);
            // Instantiate the RequestQueue
            RequestQueue queue = Volley.newRequestQueue(this.context);
            String url = "http://" + ipAddress + "/" + lightCircuit + "/off:80";

            // Request a string response from the provided URL
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("onResponse", response.substring(0, 50));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "That didn't work!");
                }
            });
            // Add the request to the RequestQueue.
            queue.add(request);
        }
        else {
            Toast.makeText(context, "Inserire Indirizzo IP Arduino!", Toast.LENGTH_LONG).show();
        }
    }
}
