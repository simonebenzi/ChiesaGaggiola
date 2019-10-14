package com.simo.appchiesa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simo.appchiesa.Arduino.ArduinoConnection;

public class MainActivity extends AppCompatActivity {

    EditText etUrl = null;
    ArduinoConnection arduino = null;
    Button bttRequest = null;
    Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        bttRequest = findViewById(R.id.bttRequest);
        etUrl = findViewById(R.id.etURL);

        bttRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String insertedIpAddress = etUrl.getText().toString().trim();
                arduino = new ArduinoConnection(context, "5", insertedIpAddress, "ON");
                arduino.sendRequest();
            }
        });
    }
}
