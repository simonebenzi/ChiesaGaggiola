package com.or.appchiesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ModifyLightDialogFragment extends AppCompatDialogFragment {

    private ModifyLightDialogInterface dialogInterface;
    private int position, clickedItem;
    private String section;
    private DBHelper dbHelper;
    private TextInputLayout textInputLayoutName, textInputLayoutOpName, textInputLayoutPsw;
    private TextInputEditText selectSection;
    private String selectedSection;

    interface ModifyLightDialogInterface {
        void modifyLightDetails(String lightName, int position, String oldSection, String newSection, String opName);
    }

    public ModifyLightDialogFragment(int position, String section) {
        this.position = position;
        this.section = section;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        dbHelper = new DBHelper(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_light_dialog, null);
        textInputLayoutName = (TextInputLayout) view.findViewById(R.id.light_name_edit_text);
        textInputLayoutPsw = (TextInputLayout) view.findViewById(R.id.light_psw_ed);
        textInputLayoutOpName = (TextInputLayout) view.findViewById(R.id.op_name_edit_text);
        selectSection = view.findViewById(R.id.select_section);
        selectedSection = section;
        this.clickedItem = -1;

        final String lightName = dbHelper.getAllLightsNameFromSection(section).get(position);
        final String lightOpName = dbHelper.getAllLightsOpNameFromSection(section).get(position);
        textInputLayoutName.getEditText().setText(lightName);
        textInputLayoutOpName.getEditText().setText(lightOpName);
        selectSection.setText(section);

        selectSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder selectBuilder = new AlertDialog.Builder(getActivity());
                setSelectSectionBuilder(selectBuilder, selectSection, selectedSection);
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

    // To maintain AddLightDialogFragment open when inserted wrong password
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
                        String newLightName = textInputLayoutName.getEditText()
                                .getText().toString();
                        String newLightOpName = textInputLayoutOpName.getEditText()
                                .getText().toString();
                        dialogInterface.modifyLightDetails(newLightName, position, section,
                                selectedSection, newLightOpName);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dialogInterface = (ModifyLightDialogInterface) context;

    }

    private void setSelectSectionBuilder(AlertDialog.Builder selectBuilder, TextInputEditText selectLightsTextInput, String sectionName) {
        selectBuilder.setTitle(R.string.select_section);
        selectBuilder.setCancelable(false);

        ArrayList<String> sectionsName = dbHelper.getAllSectionsName();
        String[] sectionsArray = new String[sectionsName.size()];

        if (sectionName.equals(""))
            clickedItem = -1;

        for (int i = 0; i < sectionsName.size(); i++) {
            sectionsArray[i] = sectionsName.get(i);
            if (sectionsName.get(i).equals(sectionName))
                clickedItem = i;
        }

        selectBuilder.setSingleChoiceItems(sectionsArray, clickedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedSection = sectionsArray[which];
            }
        });

        selectBuilder.setPositiveButton("Seleziona", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickedItem = which;
                selectLightsTextInput.setText(selectedSection);
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
                // Remove selection
                selectedSection = "";
                // Clear text view value
                selectLightsTextInput.setText(R.string.group_lights);

            }
        });
        selectBuilder.show();
    }
}