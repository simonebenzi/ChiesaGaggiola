package com.simo.appchiesa.Arduino;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class ArduinoConnection extends AsyncTask<Void, Void, Void> {
    //declare variable needed
    private String requestReply, ipAddress, portNumber;
    private Context context;
    private String parameter;
    private String parameterValue;
    private String requestCommand;

    // ArduinoConnection constructor
    public ArduinoConnection(Context context, String parameterValue, String parameter, String ipAddress, String portNumber, String requestCommand) {

        this.context = context;
        this.ipAddress = ipAddress;
        this.parameterValue = parameterValue;
        this.parameter = parameter;
        this.portNumber = portNumber;
        this.requestCommand = requestCommand;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        requestReply = sendRequest(parameterValue, parameter, ipAddress, portNumber, requestCommand);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Log.i("Request Reply", requestReply);
    }

    private String sendRequest(String parameterValue, String parameter, String ipAddress, String portNumber, String requestCommand) {
        String response = null;

        try {
            Log.i("ArduinoConnection", "sendRequest");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("PIN", parameterValue);
            jsonObject.put("status", requestCommand);

            Log.i("sendRequest", "preparing...!");


            HttpClient httpClient = new DefaultHttpClient();
            //define URL e.g. http://myIpaddress:myport/?pin=5 (to toggle pin 5)
            //URI url = new URI("http://" + ipAddress + ":" + portNumber + "/?" + parameter + "=" + parameterValue);
            URI url = new URI("http://" + ipAddress + ":" + portNumber);

            Log.i("ArduinoConnection", url.toString());
            //HttpGet getRequest = new HttpGet(); //create an HTTP GET object
            //getRequest.setURI(url); //set URL of the GET request
            HttpPost postRequest = new HttpPost();  //create an HTTP POST object
            postRequest.setURI(url); //set URL of the GET request
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Content-Type", "application/json");
            StringEntity input = new StringEntity(jsonObject.toString());
            postRequest.setEntity(input);
            //HttpResponse httpResponse = httpClient.execute(getRequest); //execute the request
            HttpResponse httpResponse = httpClient.execute(postRequest); //execute the request

            Log.i("sendRequest", "done!");


            //get arduino's reply
            InputStream content = null;
            content = httpResponse.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            response = in.readLine();
            //closing connection
            content.close();
        } catch (ClientProtocolException e) {
            // HTTP error
            response = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // IO error
            response = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // URL syntax error
            response = e.getMessage();
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return arduino's response
        return response;
    }
}
