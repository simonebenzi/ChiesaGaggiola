package com.simo.appchiesa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.simo.appchiesa.Arduino.ArduinoConnection;
import com.simo.appchiesa.LightCircuits.LightCircuits;
import com.simo.appchiesa.Scenarios.Scenarios;

//import com.example.Settings;
//import com.example.broadcastreceiver.WiFiReceiver;
//import com.example.interfaces.RoomFound;

import java.util.ArrayList;

import static com.example.Settings.LoadSettings;
import static com.example.Settings.LoadSunrise;
import static com.example.Settings.LoadSunset;


public class Home extends Activity {

    public static WifiManager wifiManager = null;
    private static LocationManager locationManager = null;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static Context context;
    private Button BttspegniTutto;
    private ImageButton iBsetting, iBOpenChiesa, iBRosario, iBMessa, iBSolenni, iBbagno;
    private EditText arduinoIP;
    private final String pinSalotto = "5";
    private final String pinIngresso = "6";
    private String roomPrev = "";
    private LightCircuits lightCircuits;
    private Scenarios scenarios;
    private ArrayList<String> arrayLights;
    private ArrayList<String> arrayScenarios;
    private String ipArduino = null;
    private ArduinoConnection arduino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        iBsetting = findViewById(R.id.iBsetting);
        BttspegniTutto = findViewById(R.id.BspegniTutto);
        iBOpenChiesa = findViewById(R.id.iBOpenChiesa);
        iBRosario = findViewById(R.id.iBRosario);
        iBMessa = findViewById(R.id.iBMessa);
        iBSolenni = findViewById(R.id.iBSolenni);

        context = getApplicationContext();

        lightCircuits = new LightCircuits();
        scenarios = new Scenarios();
        arduino = new ArduinoConnection(context);

        // Carico gli stati delle luci
        arrayLights = lightCircuits.loadLights(context);
        arrayScenarios = scenarios.loadScenarios(context);
        // Carico le immagini degli imageButtons e nel caso uno sia on accendo led arduino
        /*if (ArrayLights.get(0).equals("on")) {

            new CountDownTimer(1, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    new ArduinoConnection(context, pinIngresso, "pin", ipArduino, "80").execute();
                }
            }.start();*/
        iBOpenChiesa.setImageBitmap(Scenarios.setImageScenarios(arrayScenarios.get(0), iBOpenChiesa.getContext()));
        //}
        /*if (ArrayLights.get(1).equals("on")) {

            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    new ArduinoConnection(context, pinSalotto, "pin", ipArduino, "80").execute();
                }
            }.start();*/
        iBRosario.setImageBitmap(Scenarios.setImageScenarios(arrayScenarios.get(1), iBRosario.getContext()));
        //}

        iBMessa.setImageBitmap(Scenarios.setImageScenarios(arrayScenarios.get(2), iBMessa.getContext()));
        iBSolenni.setImageBitmap(Scenarios.setImageScenarios(arrayScenarios.get(3), iBSolenni.getContext()));

        // Carica le impostazioni
//        ArrayList<String> ArraySettings = settings.LoadSettings(context);

        // Controlla se l'ip di arduino é impostato e in caso affermativo lo inserisce
        if (!ArraySettings.get(12).equals(""))
            ipArduino = ArraySettings.get(12);

        iBOpenChiesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayScenarios.get(0).equals("off")) {
                    iBOpenChiesa.setImageBitmap(Scenarios.setImageScenarios("on", iBOpenChiesa.getContext()));
                    arrayScenarios.set(0, "on");
                    arduino.scenarioActivation("chiesa");
                } else {
                    iBOpenChiesa.setImageBitmap(Scenarios.setImageScenarios("off", iBOpenChiesa.getContext()));
                    arrayScenarios.set(0, "off");
                    arduino.scenarioDeactivation("chiesa");
                }

                scenarios.saveScenarios(arrayScenarios, context);
                /*new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {*/
                //new ArduinoConnection(context, pinIngresso, "pin", ipArduino, "80").execute();
                    /*}
                }.start();*/
            }
        });

        iBRosario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayScenarios.get(1).equals("off")) {
                    iBRosario.setImageBitmap(Scenarios.setImageScenarios("on", iBRosario.getContext()));
                    arrayScenarios.set(1, "on");
                    arduino.scenarioActivation("rosario");
                } else {
                    iBRosario.setImageBitmap(Scenarios.setImageScenarios("off", iBRosario.getContext()));
                    arrayScenarios.set(1, "off");
                    arduino.scenarioDeactivation("rosario");
                }

                scenarios.saveScenarios(arrayScenarios, context);
                /*new CountDownTimer(1, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {*/
                //new ArduinoConnection(context, pinSalotto, "pin", ipArduino, "80").execute();
                    /*}
                }.start();*/
            }
        });

        iBMessa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayScenarios.get(2).equals("off")) {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("on", iBMessa.getContext()));
                    arrayScenarios.set(2, "on");
                    arduino.scenarioActivation("messa");
                } else {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("off", iBMessa.getContext()));
                    arrayScenarios.set(2, "off");
                    arduino.scenarioDeactivation("messa");
                }

                scenarios.saveScenarios(arrayScenarios, context);
            }
        });

        iBSolenni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayScenarios.get(3).equals("off")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("on", iBSolenni.getContext()));
                    arrayScenarios.set(3, "on");
                    arduino.scenarioActivation("solenni");
                } else {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("off", iBSolenni.getContext()));
                    arrayScenarios.set(3, "off");
                    arduino.scenarioDeactivation("solenni");
                }

                scenarios.saveScenarios(arrayScenarios, context);
            }
        });


        BttspegniTutto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ArrayLights.get(0).equals("on")) {
                    iBOpenChiesa.setImageBitmap(Lights.SetImageLights("off", iBOpenChiesa.getContext()));
                    ArrayLights.set(0, "off");

                    /*new CountDownTimer(1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {*/
                    new ArduinoConnection(context, pinIngresso, "pin", ipArduino, "80", "OFF").execute();
                        /*}
                    }.start();*/
                }

                if (ArrayLights.get(1).equals("on")) {
                    iBRosario.setImageBitmap(Lights.SetImageLights("off", iBRosario.getContext()));
                    ArrayLights.set(1, "off");
                    /*new CountDownTimer(5000, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {*/
                    new ArduinoConnection(context, pinSalotto, "pin", ipArduino, "80", "OFF").execute();
                        /*}
                    }.start();*/
                }
                if (ArrayLights.get(2).equals("on")) {
                    iBMessa.setImageBitmap(Lights.SetImageLights("off", iBMessa.getContext()));
                    ArrayLights.set(2, "off");
                }
                if (ArrayLights.get(3).equals("on")) {
                    iBSolenni.setImageBitmap(Lights.SetImageLights("off", iBSolenni.getContext()));
                    ArrayLights.set(3, "off");
                }
                if (ArrayLights.get(4).equals("on")) {
                    iBbagno.setImageBitmap(Lights.SetImageLights("off", iBbagno.getContext()));
                    ArrayLights.set(4, "off");
                }
                lights.SaveLights(ArrayLights, context);
            }
        });

        iBsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Home.this, SettingActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

