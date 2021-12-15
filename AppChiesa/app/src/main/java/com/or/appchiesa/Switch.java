package com.or.appchiesa;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Switch {

    private Context context;
    private RequestQueue queue;

    public Switch(Context context) {
        this.context = context;
        // creating a new variable for our request queue
        this.queue = Volley.newRequestQueue(context);
    }

    public void switchOn(String ipAddress, int port) {
        // url to post our data
        String portStr = Integer.toString(port);
        String url = "http://" + ipAddress + ":" + portStr + "/zeroconf/switch";
        Log.e("URL", url);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String name = respObj.getString("name");
                    String job = respObj.getString("job");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String bodyStr = "{\n" +
                        "\"deviceid\": \"\",\n" +
                        "\"data\": {\n" +
                        "\"switch\": \"on\"\n" +
                        "}\n" +
                        "}";
                byte[] body = new byte[0];
                try {
                    body = bodyStr.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("BODYERR", "Unable to gets bytes from JSON", e.fillInStackTrace());
                }
                return body;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void switchOff(String ipAddress, int port) {
        // url to post our data
        String portStr = Integer.toString(port);
        String url = "http://" + ipAddress + ":" + portStr + "/zeroconf/switch";

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String name = respObj.getString("name");
                    String job = respObj.getString("job");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.e("ERROR", "Fail to get response = " + error.toString());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String bodyStr = "{\n" +
                        "\"deviceid\": \"\",\n" +
                        "\"data\": {\n" +
                        "\"switch\": \"off\"\n" +
                        "}\n" +
                        "}";
                byte[] body = new byte[0];
                try {
                    body = bodyStr.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("BODYERR", "Unable to gets bytes from JSON", e.fillInStackTrace());
                }
                return body;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}
