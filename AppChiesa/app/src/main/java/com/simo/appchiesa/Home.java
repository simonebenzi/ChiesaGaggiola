package com.simo.appchiesa;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.simo.appchiesa.Arduino.ArduinoConnection;
import com.simo.appchiesa.LightCircuits.LightCircuits;
import com.simo.appchiesa.Scenarios.Scenarios;

import java.util.ArrayList;

//import com.example.Settings;
//import com.example.broadcastreceiver.WiFiReceiver;
//import com.example.interfaces.RoomFound;

public class Home extends Activity {

    public static Context context;
    private Button BttspegniTutto;
    private ImageButton iBsetting, iBOpenChiesa, iBRosario, iBMessa, iBSolenni, iBbagno;
    private EditText arduinoIP;
    private LightCircuits lightCircuits;
    private Scenarios scenarios;
    private ArrayList<String> arrayLights;
    private ArrayList<String> arrayScenarios;
    private ArduinoConnection arduino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // iBsetting = findViewById(R.id.iBsetting);
        BttspegniTutto = findViewById(R.id.BspegniTutto);
        iBOpenChiesa = findViewById(R.id.iBChiesa);
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
        /* if (!ArraySettings.get(12).equals(""))
            ipArduino = ArraySettings.get(12); */

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
                if (arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("off")) {
                    iBRosario.setImageBitmap(Scenarios.setImageScenarios("on", iBRosario.getContext()));
                    arrayScenarios.set(1, "on");
                    arduino.scenarioActivation("chiesa");
                    arduino.scenarioActivation("rosario");
                } else if (arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("on")) {
                    iBRosario.setImageBitmap(Scenarios.setImageScenarios("on", iBRosario.getContext()));
                    arrayScenarios.set(1, "on");
                    arduino.scenarioActivation("rosario");
                }else if (arrayScenarios.get(1).equals("on") && arrayScenarios.get(0).equals("on")) {
                    iBRosario.setImageBitmap(Scenarios.setImageScenarios("off", iBRosario.getContext()));
                    arrayScenarios.set(1, "off");
                    arduino.scenarioDeactivation("rosario");
                }else if (arrayScenarios.get(1).equals("on") && arrayScenarios.get(0).equals("off")) {
                    iBRosario.setImageBitmap(Scenarios.setImageScenarios("off", iBRosario.getContext()));
                    arrayScenarios.set(1, "off");
                    arduino.scenarioDeactivation("chiesa");
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
                if (arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("off")) {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("on", iBMessa.getContext()));
                    arrayScenarios.set(2, "on");
                    arduino.scenarioActivation("chiesa");
                    arduino.scenarioActivation("rosario");
                    arduino.scenarioActivation("messa");
                } else if (arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("on")) {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("on", iBMessa.getContext()));
                    arrayScenarios.set(2, "on");
                    arduino.scenarioActivation("rosario");
                    arduino.scenarioActivation("messa");
                }else if (arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("on")) {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("on", iBMessa.getContext()));
                    arrayScenarios.set(2, "on");
                    arduino.scenarioActivation("messa");
                }else if (arrayScenarios.get(2).equals("on") && arrayScenarios.get(1).equals("on")) {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("off", iBMessa.getContext()));
                    arrayScenarios.set(2, "off");
                    arduino.scenarioDeactivation("messa");
                }else if (arrayScenarios.get(2).equals("on") && arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("off")) {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("off", iBMessa.getContext()));
                    arrayScenarios.set(2, "off");
                    arduino.scenarioDeactivation("chiesa");
                    arduino.scenarioDeactivation("rosario");
                    arduino.scenarioDeactivation("messa");
                }else if (arrayScenarios.get(2).equals("on") && arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("on")) {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("off", iBMessa.getContext()));
                    arrayScenarios.set(2, "off");
                    arduino.scenarioDeactivation("rosario");
                    arduino.scenarioDeactivation("messa");
                }

                scenarios.saveScenarios(arrayScenarios, context);
            }
        });

        iBSolenni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayScenarios.get(3).equals("off") && arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("off")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("on", iBSolenni.getContext()));
                    arrayScenarios.set(3, "on");
                    arduino.scenarioActivation("chiesa");
                    arduino.scenarioActivation("rosario");
                    arduino.scenarioActivation("messa");
                    arduino.scenarioActivation("solenni");
                } else if(arrayScenarios.get(3).equals("off") && arrayScenarios.get(2).equals("on")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("on", iBSolenni.getContext()));
                    arrayScenarios.set(3, "on");
                    arduino.scenarioActivation("solenni");
                }else if(arrayScenarios.get(3).equals("off") && arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("on")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("on", iBSolenni.getContext()));
                    arrayScenarios.set(3, "on");
                    arduino.scenarioActivation("messa");
                    arduino.scenarioActivation("solenni");
                }else if(arrayScenarios.get(3).equals("off") && arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("on")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("on", iBSolenni.getContext()));
                    arrayScenarios.set(3, "on");
                    arduino.scenarioActivation("rosario");
                    arduino.scenarioActivation("messa");
                    arduino.scenarioActivation("solenni");
                }else if(arrayScenarios.get(3).equals("on") && arrayScenarios.get(2).equals("on")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("off", iBSolenni.getContext()));
                    arrayScenarios.set(3, "off");
                    arduino.scenarioDeactivation("solenni");
                }else if(arrayScenarios.get(3).equals("on") && arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("on")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("off", iBSolenni.getContext()));
                    arrayScenarios.set(3, "off");
                    arduino.scenarioDeactivation("messa");
                    arduino.scenarioDeactivation("solenni");
                }else if(arrayScenarios.get(3).equals("on") && arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("on")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("off", iBSolenni.getContext()));
                    arrayScenarios.set(3, "off");
                    arduino.scenarioDeactivation("rosario");
                    arduino.scenarioDeactivation("messa");
                    arduino.scenarioDeactivation("solenni");
                }else if(arrayScenarios.get(3).equals("on") && arrayScenarios.get(2).equals("off") && arrayScenarios.get(1).equals("off") && arrayScenarios.get(0).equals("off")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("off", iBSolenni.getContext()));
                    arrayScenarios.set(3, "off");
                    arduino.scenarioDeactivation("chiesa");
                    arduino.scenarioDeactivation("rosario");
                    arduino.scenarioDeactivation("messa");
                    arduino.scenarioDeactivation("solenni");
                }

                scenarios.saveScenarios(arrayScenarios, context);
            }
        });


        BttspegniTutto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayScenarios.get(0).equals("on")) {
                    iBOpenChiesa.setImageBitmap(Scenarios.setImageScenarios("off", iBOpenChiesa.getContext()));
                    arrayScenarios.set(0, "off");
                    arduino.scenarioDeactivation("chiesa");
                }

                if (arrayScenarios.get(1).equals("on")) {
                    iBRosario.setImageBitmap(Scenarios.setImageScenarios("off", iBRosario.getContext()));
                    arrayScenarios.set(1, "off");
                    arduino.scenarioDeactivation("chiesa");
                    arduino.scenarioDeactivation("rosario");
                }

                if (arrayScenarios.get(2).equals("on")) {
                    iBMessa.setImageBitmap(Scenarios.setImageScenarios("off", iBMessa.getContext()));
                    arrayScenarios.set(2, "off");
                    arduino.scenarioDeactivation("chiesa");
                    arduino.scenarioDeactivation("rosario");
                    arduino.scenarioDeactivation("messa");
                }
                if (arrayScenarios.get(3).equals("on")) {
                    iBSolenni.setImageBitmap(Scenarios.setImageScenarios("off", iBSolenni.getContext()));
                    arrayScenarios.set(3, "off");
                    arduino.scenarioDeactivation("chiesa");
                    arduino.scenarioDeactivation("rosario");
                    arduino.scenarioDeactivation("messa");
                    arduino.scenarioDeactivation("solenni");
                }

                scenarios.saveScenarios(arrayScenarios, context);
            }
        });

        /* iBsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Home.this, SettingActivity.class);
                startActivity(i);
            }
        }); */
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

