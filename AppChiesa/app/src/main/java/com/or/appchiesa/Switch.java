package com.or.appchiesa;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

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
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Switch {

    private Context context;
    private RequestQueue queue;
    private DBHelper dbHelper;

    public Switch(Context context) {
        this.context = context;
        // creating a new variable for our request queue
        this.queue = Volley.newRequestQueue(context);
        this.dbHelper = new DBHelper(context);
    }

    public void switchLightOn(String ipAddress, String lightName, String lightOpName, ImageView imageView) {
        // URL to make the GET request
        String url = "http://" + ipAddress + "/" + lightOpName + "/on";
        Log.e("URL", url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the request was accepted
                        Log.i("RESPONSE", "200 OK - LIGHT ON");
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_bulb_on);
                        imageView.setImageDrawable(drawable);
                        dbHelper.updateLightState(false, lightName, lightOpName);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the request was denied
                Log.e("ERROR RESPONSE", "Request not working");
                String message = context.getString(R.string.connection_error);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        // Add the request to the RequestQueue
        queue.add(request);
    }

    public void switchLightOn(String ipAddress, String lightName, String lightOpName) {
        // URL to make the GET request
        String url = "http://" + ipAddress + "/" + lightOpName + "/on";
        Log.e("URL", url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the request was accepted
                        Log.i("RESPONSE", "200 OK - LIGHT ON");
                        dbHelper.updateLightState(false, lightName, lightOpName);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the request was denied
                Log.e("ERROR RESPONSE", "Request not working");
                String message = context.getString(R.string.connection_error);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        // Add the request to the RequestQueue
        queue.add(request);
    }

    public void switchLightOff(String ipAddress, String lightName, String lightOpName, ImageView imageView) {
        // URL to make the GET request
        String url = "http://" + ipAddress + "/" + lightOpName + "/off";
        Log.e("URL", url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the request was accepted
                        Log.i("RESPONSE", "200 OK - LIGHT OFF");
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_bulb);
                        imageView.setImageDrawable(drawable);
                        dbHelper.updateLightState(true, lightName, lightOpName);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the request was denied
                Log.e("ERROR RESPONSE", "Request not working");
                String message = context.getString(R.string.connection_error);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        // Add the request to the RequestQueue
        queue.add(request);
    }

    public void switchLightOff(String ipAddress, String lightName, String lightOpName) {
        // URL to make the GET request
        String url = "http://" + ipAddress + "/" + lightOpName + "/off";
        Log.e("URL", url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the request was accepted
                        Log.i("RESPONSE", "200 OK - LIGHT OFF");
                        dbHelper.updateLightState(true, lightName, lightOpName);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the request was denied
                Log.e("ERROR RESPONSE", "Request not working");
                String message = context.getString(R.string.connection_error);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        // Add the request to the RequestQueue
        queue.add(request);
    }

    public void switchScenarioOn(String scenario, ArrayList<String> lights, String ipAddress, ImageView imageView, ChildRecyclerAdapter adapter) {

        // Check Arduino connection
//        ArrayList<StringRequest> checkRequests = new ArrayList<StringRequest>();

        // OFF request
        // URL to make the GET request
        String url = "http://" + ipAddress + "/status";
        Log.e("URL", url);

        StringRequest statusRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the request was accepted
                        Log.i("RESPONSE", "200 OK - CONNECTION OK");
                        // Update scenario
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_bulb_group_on);
                        imageView.setImageDrawable(drawable);
                        dbHelper.updateScenarioState(false, scenario);

                        ArrayList<StringRequest> switchOnRequests = createScenarioRequests(ipAddress,
                                lights, "on");

                        // Get all other scenarios
                        ArrayList<String> scenariosList = dbHelper.getScenariosExceptOne(scenario);

                        ArrayList<String> toSwitchOffLights = new ArrayList<>();
                        // Update only lights that are not in common
                        for (int i = 0; i < scenariosList.size(); i++) {
                            String scenarioName = scenariosList.get(i);
                            dbHelper.updateScenarioState(true, scenarioName);
                            String[] scenarioLightsArray = convertStringToArray(dbHelper.
                                    getAllScenarioLights(scenarioName));
                            ArrayList<String> scenarioLights = new ArrayList<>(Arrays.asList(scenarioLightsArray));
                            for (int j = 0; j < scenarioLights.size(); j++) {
                                if (!(lights.contains(scenarioLights.get(j)))) {
                                    toSwitchOffLights.add(scenarioLights.get(j));
                                }
                            }
                        }
                        // Update ScenariosFragment adapter
                        adapter.updateRecycle("group");

                        ArrayList<StringRequest> switchOffRequests = createScenarioRequests(ipAddress,
                                toSwitchOffLights, "off");

                        // Send all requests
                        for (int i = 0; i < switchOnRequests.size(); i++)
                            // Add the request to the RequestQueue
                            queue.add(switchOnRequests.get(i));
                        for (int i = 0; i < switchOffRequests.size(); i++)
                            // Add the request to the RequestQueue
                            queue.add(switchOffRequests.get(i));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the request was denied
                Log.e("ERROR RESPONSE", "Not connected!");
                String message = context.getString(R.string.connection_error);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
//        checkRequests.add(offRequest);
        queue.add(statusRequest);

//        for (int i = 0; i < checkRequests.size(); i++)
//            // Add the request to the RequestQueue
//            queue.add(checkRequests.get(i));

    }

    public void switchScenarioOff(String scenario, ArrayList<String> lights, String ipAddress, ImageView imageView) {

        // Check Arduino connection
//        ArrayList<StringRequest> checkRequests = new ArrayList<StringRequest>();

        // Check STATUS request
        // URL to make the GET request
        String url = "http://" + ipAddress + "/status";
        Log.e("URL", url);

        StringRequest statusRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the request was accepted
                        Log.i("RESPONSE", "200 OK - CONNECTION OK");
                        // Update scenario
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_bulb_group);
                        imageView.setImageDrawable(drawable);
                        dbHelper.updateScenarioState(true, scenario);
                        ArrayList<StringRequest> requests = createScenarioRequests(ipAddress,
                                lights, "off");

                        // Send all requests
                        for (int i = 0; i < requests.size(); i++)
                            // Add the request to the RequestQueue
                            queue.add(requests.get(i));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the request was denied
                Log.e("ERROR RESPONSE", "Not connected!");
                String message = context.getString(R.string.connection_error);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
//        checkRequests.add(statusRequest);
        queue.add(statusRequest);
//        for (int i = 0; i < checkRequests.size(); i++)
//            // Add the request to the RequestQueue
//            queue.add(checkRequests.get(i));
    }

    public void switchAllLightsOff(String ipAddress, ArrayList<String> lightsName,
                                   ArrayList<String> lightsOpName, ArrayList<String> scenariosName,
                                   ArrayList<Boolean> lightsState, ArrayList<Boolean> scenariosState,
                                   ViewPagerAdapter adapter) {
        // Check STATUS request
        // URL to make the GET request
        String url = "http://" + ipAddress + "/status";
        Log.e("URL", url);

        StringRequest statusRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the request was accepted
                        Log.i("RESPONSE", "200 OK - CONNECTION OK");

                        int arraySize = lightsName.size();

                        for (int i = 0; i < arraySize; i++) {
                            String opName = lightsOpName.get(i);
                            String name = lightsName.get(i);
                            Boolean state = lightsState.get(i);
                            if (state) {
                                switchLightOff(ipAddress, name, opName);
                                dbHelper.updateLightState(state, name, opName);
                            }
                        }

                        // Deactivate all scenarios
                        arraySize = scenariosName.size();

                        for (int i = 0; i < arraySize; i++) {
                            String scenarioName = scenariosName.get(i);
                            Boolean state = scenariosState.get(i);

                            if(state){
                                dbHelper.updateScenarioState(true, scenarioName);
                            }
                        }

                        // Try to notifyDataSetChanged in Lights Fragment main adapter
                        LightsFragment lightsFragment = (LightsFragment) adapter.getFragments().get(1);
                        try {
                            lightsFragment.getMainRecyclerAdapter().notifyDataSetChanged();
                        } catch (NullPointerException exception) {

                        }

                        // Try to notifyDataSetChanged in Scenarios Fragment adapter
                        ScenariosFragment scenariosFragment = (ScenariosFragment) adapter.getFragments().get(0);
                        try {
                            scenariosFragment.getAdapter().updateRecycle("group");
                        } catch (NullPointerException exception) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the request was denied
                Log.e("ERROR RESPONSE", "Not connected!");
                String message = context.getString(R.string.connection_error);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        queue.add(statusRequest);
    }

    public void switchAllLightsOn(String ipAddress, ArrayList<String> lightsName,
                                  ArrayList<String> lightsOpName, ArrayList<String> scenariosName,
                                  ArrayList<Boolean> lightsState, ArrayList<Boolean> scenariosState,
                                  ViewPagerAdapter adapter) {
        // Check STATUS request
        // URL to make the GET request
        String url = "http://" + ipAddress + "/status";
        Log.e("URL", url);

        StringRequest statusRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the request was accepted
                        Log.i("RESPONSE", "200 OK - CONNECTION OK");

                        // Update lights state
                        int arraySize = lightsName.size();

                        for (int i = 0; i < arraySize; i++) {
                            String opName = lightsOpName.get(i);
                            String name = lightsName.get(i);
                            Boolean state = lightsState.get(i);
                            if (!state) {
                                switchLightOn(ipAddress, name, opName);
                                dbHelper.updateLightState(state, name, opName);
                            }
                        }

                        // Deactivate all scenarios
                        arraySize = scenariosName.size();

                        for (int i = 0; i < arraySize; i++) {
                            String scenarioName = scenariosName.get(i);
                            Boolean state = scenariosState.get(i);

                            if(state){
                                dbHelper.updateScenarioState(true, scenarioName);
                            }
                        }


                        // Try to notifyDataSetChanged in Lights Fragment main adapter
                        LightsFragment lightsFragment = (LightsFragment) adapter.getFragments().get(1);
                        try {
                            lightsFragment.getMainRecyclerAdapter().notifyDataSetChanged();
                        } catch (NullPointerException exception) {

                        }

                        // Try to notifyDataSetChanged in Scenarios Fragment adapter
                        ScenariosFragment scenariosFragment = (ScenariosFragment) adapter.getFragments().get(0);
                        try {
                            scenariosFragment.getAdapter().updateRecycle("group");
                        } catch (NullPointerException exception) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the request was denied
                Log.e("ERROR RESPONSE", "Not connected!");
                String message = context.getString(R.string.connection_error);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        queue.add(statusRequest);
    }


    private static String[] convertStringToArray(String str) {
        String strSeparator = "__,__";
        String[] arr = str.split(strSeparator);
        return arr;
    }

    private ArrayList<StringRequest> createScenarioRequests(String ipAddress, ArrayList<String> lights,
                                                            String aSwitch) {
        // List of requests
        ArrayList<StringRequest> requests = new ArrayList<StringRequest>();

        if (aSwitch.equals("off")) {
            for (int i = 0; i < lights.size(); i++) {
                // URL to make the GET request
                String url = "http://" + ipAddress + "/" + lights.get(i) + "/off";
                Log.e("URL", url);
                // Set the single request
                int finalI = i;
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Log the first 500 characters of the response string
                                Log.i("RESPONSE", "200 OK - LIGHT OFF");

                                Boolean lightState = dbHelper.getLightState(lights.get(finalI));
                                if (lightState)
                                    dbHelper.updateLightState(lightState, lights.get(finalI));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the request was denied
                        Log.e("ERROR RESPONSE", "Request not working");
                    }
                });
                requests.add(request);
            }
        } else if (aSwitch.equals("on")) {
            for (int i = 0; i < lights.size(); i++) {
                // URL to make the GET request
                String url = "http://" + ipAddress + "/" + lights.get(i) + "/on";
                Log.e("URL", url);
                // Set the single request
                int finalI = i;
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Log the first 500 characters of the response string
                                Log.i("RESPONSE", "200 OK - LIGHT ON");

                                Boolean lightState = dbHelper.getLightState(lights.get(finalI));
                                if (!lightState)
                                    dbHelper.updateLightState(lightState, lights.get(finalI));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the request was denied
                        Log.e("ERROR RESPONSE", "Request not working");
                    }
                });
                requests.add(request);
            }
        }
        return requests;
    }
}
