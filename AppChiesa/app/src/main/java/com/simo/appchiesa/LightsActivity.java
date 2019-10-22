package com.simo.appchiesa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.simo.appchiesa.Arduino.ArduinoConnection;
import com.simo.appchiesa.LightCircuits.LightCircuits;


public class LightsActivity extends Activity {

    private ImageButton ibReturn, ibCH4, ibCH5, ibCH6a, ibCH6b, ibCH7, ibCH8, ibCH9, ibCH10, ibCH11, ibCH12a, ibCH12b, ibCH13, ibCH14, ibCH15;
    private LightCircuits lightCircuits;
    private ArduinoConnection arduino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = ScenariosActivity.homeContext;
        setContentView(R.layout.lights);

        ibReturn = findViewById(R.id.ibReturn);
        ibCH4 = findViewById(R.id.ibCH4);
        ibCH5 = findViewById(R.id.ibCH5);
        ibCH6a = findViewById(R.id.ibCH6a);
        ibCH6b = findViewById(R.id.ibCH6b);
        ibCH7 = findViewById(R.id.ibCH7);
        ibCH8 = findViewById(R.id.ibCH8);
        ibCH9 = findViewById(R.id.ibCH9);
        ibCH10 = findViewById(R.id.ibCH10);
        ibCH11 = findViewById(R.id.ibCH11);
        ibCH12a = findViewById(R.id.ibCH12a);
        ibCH12b = findViewById(R.id.ibCH12b);
        ibCH13 = findViewById(R.id.ibCH13);
        ibCH14 = findViewById(R.id.ibCH14);
        ibCH15 = findViewById(R.id.ibCH15);

        lightCircuits = new LightCircuits();
        arduino = new ArduinoConnection(context, ScenariosActivity.arrayLights);

        // Carico le immagini degli ImageButtons
        ibCH4.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(0), ibCH4.getContext()));
        ibCH5.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(1), ibCH5.getContext()));
        ibCH6a.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(2), ibCH6a.getContext()));
        ibCH6b.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(3), ibCH6b.getContext()));
        ibCH7.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(4), ibCH7.getContext()));
        ibCH8.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(5), ibCH8.getContext()));
        ibCH9.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(6), ibCH9.getContext()));
        ibCH10.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(7), ibCH10.getContext()));
        ibCH11.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(8), ibCH11.getContext()));
        ibCH12a.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(9), ibCH12a.getContext()));
        ibCH12b.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(10), ibCH12b.getContext()));
        ibCH13.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(11), ibCH13.getContext()));
        ibCH14.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(12), ibCH14.getContext()));
        ibCH15.setImageBitmap(LightCircuits.setImageLights(ScenariosActivity.arrayLights.get(13), ibCH15.getContext()));

        // Gestione pressione dei bottoni
        ibCH4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(0).equals("off")) {
                    ibCH4.setImageBitmap(LightCircuits.setImageLights("on", ibCH4.getContext()));
                    ScenariosActivity.arrayLights.set(0, "on");
                    if(ScenariosActivity.arrayScenarios.get(0).equals("off")){
                        arduino.lightActivation("ch4");
                    }
                } else {
                    ibCH4.setImageBitmap(LightCircuits.setImageLights("off", ibCH4.getContext()));
                    ScenariosActivity.arrayLights.set(0, "off");
                    if(ScenariosActivity.arrayScenarios.get(0).equals("off")){
                        arduino.lightDeactivation("ch4");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(1).equals("off")) {
                    ibCH5.setImageBitmap(LightCircuits.setImageLights("on", ibCH5.getContext()));
                    ScenariosActivity.arrayLights.set(1, "on");
                    if(ScenariosActivity.arrayScenarios.get(0).equals("off")){
                        arduino.lightActivation("ch5");
                    }
                } else {
                    ibCH5.setImageBitmap(LightCircuits.setImageLights("off", ibCH5.getContext()));
                    ScenariosActivity.arrayLights.set(1, "off");
                    if(ScenariosActivity.arrayScenarios.get(0).equals("off")){
                        arduino.lightDeactivation("ch5");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH6a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(2).equals("off")) {
                    ibCH6a.setImageBitmap(LightCircuits.setImageLights("on", ibCH6a.getContext()));
                    ScenariosActivity.arrayLights.set(2, "on");
                    if(ScenariosActivity.arrayScenarios.get(2).equals("off")){
                        arduino.lightActivation("ch6a");
                    }
                } else {
                    ibCH6a.setImageBitmap(LightCircuits.setImageLights("off", ibCH6a.getContext()));
                    ScenariosActivity.arrayLights.set(2, "off");
                    if(ScenariosActivity.arrayScenarios.get(2).equals("off")){
                        arduino.lightDeactivation("ch6a");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH6b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(3).equals("off")) {
                    ibCH6b.setImageBitmap(LightCircuits.setImageLights("on", ibCH6b.getContext()));
                    ScenariosActivity.arrayLights.set(3, "on");
                    if(ScenariosActivity.arrayScenarios.get(1).equals("off")){
                        arduino.lightActivation("ch6b");
                    }
                } else {
                    ibCH6b.setImageBitmap(LightCircuits.setImageLights("off", ibCH6b.getContext()));
                    ScenariosActivity.arrayLights.set(3, "off");
                    if(ScenariosActivity.arrayScenarios.get(1).equals("off")){
                        arduino.lightDeactivation("ch6b");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(4).equals("off")) {
                    ibCH7.setImageBitmap(LightCircuits.setImageLights("on", ibCH7.getContext()));
                    ScenariosActivity.arrayLights.set(4, "on");
                    arduino.lightActivation("ch7");
                } else {
                    ibCH7.setImageBitmap(LightCircuits.setImageLights("off", ibCH7.getContext()));
                    ScenariosActivity.arrayLights.set(4, "off");
                    arduino.lightDeactivation("ch7");
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(5).equals("off")) {
                    ibCH8.setImageBitmap(LightCircuits.setImageLights("on", ibCH8.getContext()));
                    ScenariosActivity.arrayLights.set(5, "on");
                    arduino.lightActivation("ch8");
                } else {
                    ibCH8.setImageBitmap(LightCircuits.setImageLights("off", ibCH8.getContext()));
                    ScenariosActivity.arrayLights.set(5, "off");
                    arduino.lightDeactivation("ch8");
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(6).equals("off")) {
                    ibCH9.setImageBitmap(LightCircuits.setImageLights("on", ibCH9.getContext()));
                    ScenariosActivity.arrayLights.set(6, "on");
                    if(ScenariosActivity.arrayScenarios.get(0).equals("off")){
                        arduino.lightActivation("ch9");
                    }
                } else {
                    ibCH9.setImageBitmap(LightCircuits.setImageLights("off", ibCH9.getContext()));
                    ScenariosActivity.arrayLights.set(6, "off");
                    if(ScenariosActivity.arrayScenarios.get(0).equals("off")){
                        arduino.lightDeactivation("ch9");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(7).equals("off")) {
                    ibCH10.setImageBitmap(LightCircuits.setImageLights("on", ibCH10.getContext()));
                    ScenariosActivity.arrayLights.set(7, "on");
                    if(ScenariosActivity.arrayScenarios.get(1).equals("off")){
                        arduino.lightActivation("ch10");
                    }
                } else {
                    ibCH10.setImageBitmap(LightCircuits.setImageLights("off", ibCH10.getContext()));
                    ScenariosActivity.arrayLights.set(7, "off");
                    if(ScenariosActivity.arrayScenarios.get(1).equals("off")){
                        arduino.lightDeactivation("ch10");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(8).equals("off")) {
                    ibCH11.setImageBitmap(LightCircuits.setImageLights("on", ibCH11.getContext()));
                    ScenariosActivity.arrayLights.set(8, "on");
                    if(ScenariosActivity.arrayScenarios.get(2).equals("off")){
                        arduino.lightActivation("ch11");
                    }
                } else {
                    ibCH11.setImageBitmap(LightCircuits.setImageLights("off", ibCH11.getContext()));
                    ScenariosActivity.arrayLights.set(8, "off");
                    if(ScenariosActivity.arrayScenarios.get(2).equals("off")){
                        arduino.lightDeactivation("ch11");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH12a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(9).equals("off")) {
                    ibCH12a.setImageBitmap(LightCircuits.setImageLights("on", ibCH12a.getContext()));
                    ScenariosActivity.arrayLights.set(9, "on");
                    if(ScenariosActivity.arrayScenarios.get(0).equals("off")){
                        arduino.lightActivation("ch12a");
                    }
                } else {
                    ibCH12a.setImageBitmap(LightCircuits.setImageLights("off", ibCH12a.getContext()));
                    ScenariosActivity.arrayLights.set(9, "off");
                    if(ScenariosActivity.arrayScenarios.get(0).equals("off")){
                        arduino.lightDeactivation("ch12a");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH12b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(10).equals("off")) {
                    ibCH12b.setImageBitmap(LightCircuits.setImageLights("on", ibCH12b.getContext()));
                    ScenariosActivity.arrayLights.set(10, "on");
                    if(ScenariosActivity.arrayScenarios.get(3).equals("off")){
                        arduino.lightActivation("ch12b");
                    }
                } else {
                    ibCH12b.setImageBitmap(LightCircuits.setImageLights("off", ibCH12b.getContext()));
                    ScenariosActivity.arrayLights.set(10, "off");
                    if(ScenariosActivity.arrayScenarios.get(3).equals("off")){
                        arduino.lightDeactivation("ch12b");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(11).equals("off")) {
                    ibCH13.setImageBitmap(LightCircuits.setImageLights("on", ibCH13.getContext()));
                    ScenariosActivity.arrayLights.set(11, "on");
                    arduino.lightActivation("ch13");
                } else {
                    ibCH13.setImageBitmap(LightCircuits.setImageLights("off", ibCH13.getContext()));
                    ScenariosActivity.arrayLights.set(11, "off");
                    arduino.lightDeactivation("ch13");
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(12).equals("off")) {
                    ibCH14.setImageBitmap(LightCircuits.setImageLights("on", ibCH14.getContext()));
                    ScenariosActivity.arrayLights.set(12, "on");
                    arduino.lightActivation("ch14");
                } else {
                    ibCH14.setImageBitmap(LightCircuits.setImageLights("off", ibCH14.getContext()));
                    ScenariosActivity.arrayLights.set(12, "off");
                    arduino.lightDeactivation("ch14");
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibCH15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScenariosActivity.arrayLights.get(13).equals("off")) {
                    ibCH15.setImageBitmap(LightCircuits.setImageLights("on", ibCH15.getContext()));
                    ScenariosActivity.arrayLights.set(13, "on");
                    if(ScenariosActivity.arrayScenarios.get(3).equals("off")){
                        arduino.lightActivation("ch15");
                    }
                } else {
                    ibCH15.setImageBitmap(LightCircuits.setImageLights("off", ibCH15.getContext()));
                    ScenariosActivity.arrayLights.set(13, "off");
                    if(ScenariosActivity.arrayScenarios.get(3).equals("off")){
                        arduino.lightDeactivation("ch15");
                    }
                }

                lightCircuits.saveLights(ScenariosActivity.arrayLights, context);
            }
        });

        ibReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(LightsActivity.this, ScenariosActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}