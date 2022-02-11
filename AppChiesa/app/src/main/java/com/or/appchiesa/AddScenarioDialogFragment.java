package com.or.appchiesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;

public class AddScenarioDialogFragment extends AppCompatDialogFragment {

    private AddScenarioDialogInterface dialogInterface;
    private DBHelper dbHelper;
    private boolean[] selectedLights;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPsw;
    private TextInputEditText selectLights;


    interface AddScenarioDialogInterface {
        void getGroupDetails(String group, boolean[] selectedLights);
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
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing here because we override this button later to change the close behaviour.
                        //However, we still need this because on older versions of Android unless we
                        //pass a handler the button doesn't get instantiated
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

                    String psw = textInputLayoutPsw.getEditText().getText().toString();
                    if(psw.equals(MainActivity.PASSWORD)){
                        dialogInterface.getGroupDetails(textInputLayoutName
                                .getEditText().getText().toString(), selectedLights);
                        wantToCloseDialog = true;
                    }
                    else{
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
        selectedLights = new boolean[lightsName.size()];
        for(int i = 0; i < selectedLights.length; i++){
            selectedLights[i] = false;
        }
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
                    // Concat array value
                    stringBuilder.append(lightsArray[lightsList.get(j)]);
                    // Set true in selected lights
                    selectedLights[lightsList.get(j)] = true;
                    // Check condition
                    if (j != lightsList.size() - 1) {
                        // When j value not equal to lights list size - 1 add comma
                        stringBuilder.append(", ");
                    }

                }
                // set text on textView
                selectLightsTextInput.setHint(stringBuilder.toString());
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

        dialogInterface = (AddScenarioDialogInterface) context;

    }
}
