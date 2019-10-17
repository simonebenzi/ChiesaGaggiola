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
    public ArduinoConnection(Context context, String parameterValue, String ipAddress, String requestCommand) {

        this.context = context;
        this.ipAddress = ipAddress;
        this.parameterValue = parameterValue;
        this.requestCommand = requestCommand;
    }

    public void scenarioActivation(String scenario) {


    }

    public void scenarioADeactivation(String scenario) {


    }

    public void lightActivation(String lightCircuit){
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "http://192.168.1.124/" + lightCircuit + "/on:80";

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
        String url = "http://192.168.1.124/" + lightCircuit + "/off:80";

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
