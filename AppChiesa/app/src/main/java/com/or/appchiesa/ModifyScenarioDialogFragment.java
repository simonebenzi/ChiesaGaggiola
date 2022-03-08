package com.or.appchiesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ModifyScenarioDialogFragment extends AppCompatDialogFragment {

    private ModifyGroupDialogInterface dialogInterface;
    private int position;
    private DBHelper dbHelper;
    private boolean[] selectedLights;
    private boolean resetSelection;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPsw;
    private TextInputEditText selectLights;


    interface ModifyGroupDialogInterface {
        void modifyGroupName(String groupName, int position, boolean[] selectedLights);
    }

    public ModifyScenarioDialogFragment(int position) {
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_group_dialog, null);
        textInputLayoutName = (TextInputLayout) view.findViewById(R.id.group_name_edit_text);
        textInputLayoutPsw = (TextInputLayout) view.findViewById(R.id.group_psw_ed);
        selectLights = view.findViewById(R.id.select_lights);
        dbHelper = new DBHelper(getContext());

        // Set pre-selected lights
        ArrayList<String> scenariosName = dbHelper.getAllScenariosName();
        ArrayList<String> lightsOpName = dbHelper.getAllLightsOpNameFromSection();
        ArrayList<String> lightsName = dbHelper.getAllLightsNameFromSection();
        String scenarioLightsStr = dbHelper.getAllScenarioLights(scenariosName.get(position));
        String[] scenarioLightsArray = convertStringToArray(scenarioLightsStr);
        ArrayList<String> scenarioLights = new ArrayList<>(Arrays.asList(scenarioLightsArray));
        selectedLights = new boolean[lightsOpName.size()];
        Arrays.fill(selectedLights, false);
        for (int j = 0; j < lightsOpName.size(); j++) {
            if (scenarioLights.contains(lightsOpName.get(j)))
                // Set true in selected lights
                selectedLights[j] = true;
        }
        // Set selected lights in edit text
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> lightsMemory = new ArrayList<>();
        // Concat array value
        for (int i = 0; i < selectedLights.length; i++) {
            if (selectedLights[i]) {
                lightsMemory.add(lightsName.get(i));
            }
        }

        for (int i = 0; i < lightsMemory.size(); i++) {
            stringBuilder.append(lightsMemory.get(i));
            // Check condition
            if (i != lightsMemory.size() - 1) {
                // When j value not equal to lights list size - 1 add comma
                stringBuilder.append(", ");
            }
        }

        // set text on textView
        selectLights.setText(stringBuilder.toString());

        final String groupName = dbHelper.getAllScenariosName().get(position);
        textInputLayoutName.getEditText().setText(groupName);

        resetSelection = true;

        selectLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder selectBuilder = new AlertDialog.Builder(getActivity());
                setSelectLightsBuilder(selectBuilder, selectLights);
            }
        });

        resetSelection = true;

        selectLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder selectBuilder = new AlertDialog.Builder(getActivity());
                setSelectLightsBuilder(selectBuilder, selectLights);
            }
        });

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.modify, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    // To maintain AddScenarioDialogFragment open when inserted wrong password
    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog = false;

                    String insertedPsw = textInputLayoutPsw.getEditText().getText().toString();
                    String storedPsw = dbHelper.getPassword();

                    if (insertedPsw.equals(storedPsw)) {
                        String newGroupName = textInputLayoutName.getEditText()
                                .getText().toString();
                        ;
                        dialogInterface.modifyGroupName(newGroupName, position, selectedLights);
                        wantToCloseDialog = true;
                    } else {
                        String message = "Password errata! Riprovare!";
                        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    if (wantToCloseDialog)
                        dismiss();
                }
            });
        }
    }

    private void setSelectLightsBuilder(AlertDialog.Builder selectBuilder, TextInputEditText selectLightsTextInput) {
        // Set title and dialog non cancelable
        selectBuilder.setTitle(R.string.group_lights);
        selectBuilder.setCancelable(false);
        ArrayList<Integer> lightsList = new ArrayList<>();
        ArrayList<String> lightsName = dbHelper.getAllLightsNameFromSection();
        String[] lightsArray = new String[lightsName.size()];

        for (int i = 0; i < lightsName.size(); i++) {
            lightsArray[i] = lightsName.get(i);
        }

        // Set multiple choice items
        selectBuilder.setMultiChoiceItems(lightsArray, selectedLights, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    // When checkbox selected add position in lightsList
                    lightsList.add(which);
                    // Sort array list
                    Collections.sort(lightsList);
                } else {
                    // When checkbox unselected remove position from lightsList
                    lightsList.remove(Integer.valueOf(which));
                }
            }
        });

        selectBuilder.setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();

                for (int j = 0; j < lightsList.size(); j++) {
                    // Set true in selected lights
                    selectedLights[lightsList.get(j)] = true;
                }
                ArrayList<String> lightsMemory = new ArrayList<>();
                // Concat array value
                for (int i = 0; i < selectedLights.length; i++) {
                    if (selectedLights[i]) {
                        lightsMemory.add(lightsArray[i]);
                    }
                }

                for (int i = 0; i < lightsMemory.size(); i++) {
                    stringBuilder.append(lightsMemory.get(i));
                    // Check condition
                    if (i != lightsMemory.size() - 1) {
                        // When j value not equal to lights list size - 1 add comma
                        stringBuilder.append(", ");
                    }
                }

                // set text on textView
                selectLightsTextInput.setText(stringBuilder.toString());
            }
        });

        selectBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss dialog
                dialogInterface.dismiss();
            }
        });

        selectBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int j = 0; j < selectedLights.length; j++) {
                    // Remove all selections
                    selectedLights[j] = false;
                    // Clear lights list
                    lightsList.clear();
                    // Clear text view value
                    selectLightsTextInput.setText(R.string.group_lights);
                }
            }
        });
        selectBuilder.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dialogInterface = (ModifyGroupDialogInterface) context;

    }

    private static String[] convertStringToArray(String str) {
        String strSeparator = "__,__";
        String[] arr = str.split(strSeparator);
        return arr;
    }
}