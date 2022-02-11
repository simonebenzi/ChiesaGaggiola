package com.or.appchiesa;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Switch {

    private Context context;
    private RequestQueue queue;

    public Switch(Context context) {
        this.context = context;
        // creating a new variable for our request queue
        this.queue = Volley.newRequestQueue(context);
    }

    public void switchLightOn(String ipAddress, String light) {
        // URL to make the GET request
        String url = "http://" + ipAddress + "/" + light + "/on";
        Log.e("URL", url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the first 500 characters of the response string
                        Log.i("RESPONSE", response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the first 500 characters of the response string
                Log.e("ERROR RESPONSE", "Request not working");
            }
        });
        // Add the request to the RequestQueue
        queue.add(request);
    }

    public void switchLightOff(String ipAddress, String light) {
        // URL to make the GET request
        String url = "http://" + ipAddress + "/" + light + "/off";
        Log.e("URL", url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the first 500 characters of the response string
                        Log.i("RESPONSE", response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the first 500 characters of the response string
                Log.e("ERROR RESPONSE", "Request not working");
            }
        });
        // Add the request to the RequestQueue
        queue.add(request);
    }

    public void switchScenarioOn(ArrayList<String> lights, String ipAddress) {
        // List of requests
        ArrayList<StringRequest> requests = new ArrayList<StringRequest>();

        for(int i = 0; i < lights.size(); i++){
            // URL to make the GET request
            String url = "http://" + ipAddress + "/" + lights.get(i) + "/on";
            Log.e("URL", url);
            // Set the single request
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Log the first 500 characters of the response string
                            Log.i("RESPONSE", response.substring(0, 500));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log the first 500 characters of the response string
                    Log.e("ERROR RESPONSE", "Request not working");
                }
            });
            requests.add(request);
        }

        // Send all requests
        for(int i = 0; i < requests.size(); i++)
        // Add the request to the RequestQueue
        queue.add(requests.get(i));
    }

    public void switchScenarioOff(ArrayList<String> lights, String ipAddress) {
        // List of requests
        ArrayList<StringRequest> requests = new ArrayList<StringRequest>();

        for(int i = 0; i < lights.size(); i++){
            // URL to make the GET request
            String url = "http://" + ipAddress + "/" + lights.get(i) + "/off";
            Log.e("URL", url);
            // Set the single request
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Log the first 500 characters of the response string
                            Log.i("RESPONSE", response.substring(0, 500));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log the first 500 characters of the response string
                    Log.e("ERROR RESPONSE", "Request not working");
                }
            });
            requests.add(request);
        }

        // Send all requests
        for(int i = 0; i < requests.size(); i++)
            // Add the request to the RequestQueue
            queue.add(requests.get(i));
    }
}
