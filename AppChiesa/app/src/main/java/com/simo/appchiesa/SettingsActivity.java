package com.simo.appchiesa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.simo.appchiesa.Settings.Settings.saveSettings;

public class SettingsActivity extends Activity {

    private EditText etArduinoIP;
    private Button bttSetArduinoIP;
    private ImageButton ibReturnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = ScenariosActivity.homeContext;
        setContentView(R.layout.settings);

        etArduinoIP = findViewById(R.id.etArduinoIP);
        bttSetArduinoIP = findViewById(R.id.bttSetArduinoIP);
        ibReturnSettings = findViewById(R.id.ibReturnSettings);

        // Se l'ip di arduino era giá stato inseriro lo carico nell'editText
        if(!ScenariosActivity.arraySettings.get(0).equals("")){
            etArduinoIP.setText(ScenariosActivity.arraySettings.get(0));
        }

        bttSetArduinoIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScenariosActivity.arraySettings.set(0, etArduinoIP.getText().toString().trim());
                // Abbassa la tastiera
                etArduinoIP.onEditorAction(EditorInfo.IME_ACTION_DONE);
                saveSettings(ScenariosActivity.arraySettings, context);
                Toast.makeText(context, "Inidizzo ip inserito!", Toast.LENGTH_LONG).show();
            }
        });

        ibReturnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(SettingsActivity.this, ScenariosActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
