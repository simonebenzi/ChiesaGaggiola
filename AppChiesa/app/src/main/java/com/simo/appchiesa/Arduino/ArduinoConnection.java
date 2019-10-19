package com.simo.appchiesa.Arduino;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ArduinoConnection {
    //declare variable needed
    private String ipAddress;
    private Context context;
    private String parameterValue;
    private String requestCommand;

    // ArduinoConnection constructor
    public ArduinoConnection(Context context) {

        this.context = context;
    }

    public void scenarioActivation(String scenario) {
        if(scenario.equals("chiesa")){
            this.lightActivation("ch4");
            this.lightActivation("ch5");
            this.lightActivation("ch9");
            this.lightActivation("ch12a");
        }
        else if(scenario.equals("rosario")){
            this.lightActivation("ch6b");
            this.lightActivation("ch10");
        }
        else if(scenario.equals("messa")){
            this.lightActivation("ch6a");
            this.lightActivation("ch11");
        }
        else if(scenario.equals("solenni")){
            this.lightActivation("ch3");
            this.lightActivation("ch12b");
            this.lightActivation("ch15");
        }
    }

    public void scenarioDeactivation(String scenario) {
        if(scenario.equals("chiesa")){
            this.lightDeactivation("ch4");
            this.lightDeactivation("ch5");
            this.lightDeactivation("ch9");
            this.lightDeactivation("ch12a");
        }
        else if(scenario.equals("rosario")){
            this.lightDeactivation("ch6b");
            this.lightDeactivation("ch10");
        }
        else if(scenario.equals("messa")){
            this.lightDeactivation("ch6a");
            this.lightDeactivation("ch11");
        }
        else if(scenario.equals("solenni")){
            this.lightDeactivation("ch3");
            this.lightDeactivation("ch12b");
            this.lightDeactivation("ch15");
        }

    }

    public void lightActivation(String lightCircuit){
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "http://192.168.1.142/" + lightCircuit + "/on:80";

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

    public void lightDeactivation(String lightCircuit){
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "http://192.168.1.142/" + lightCircuit + "/off:80";

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
}
