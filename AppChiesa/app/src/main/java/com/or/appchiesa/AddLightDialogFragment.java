package com.or.appchiesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddLightDialogFragment extends AppCompatDialogFragment {

    private AddLightDialogInterface dialogInterface;
    private DBHelper dbHelper;
    private String selectedSection;
    private TextInputLayout textInputLayoutName, textInputLayoutOpName, textInputLayoutPsw;
    private TextInputEditText selectSection;
    private int clickedItem;

    interface AddLightDialogInterface {
        void getLightInfos(String lightName, String opName, String section);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_light_dialog, null);
        textInputLayoutName = view.findViewById(R.id.light_name_edit_text);
        textInputLayoutOpName = view.findViewById(R.id.op_name_edit_text);
        textInputLayoutPsw = view.findViewById(R.id.light_psw_ed);
        selectSection = view.findViewById(R.id.select_section);
        dbHelper = new DBHelper(getContext());
        selectedSection = "";

        selectSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder selectBuilder = new AlertDialog.Builder(getActivity());
                setSelectSectionBuilder(selectBuilder, selectSection);
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

                    if(insertedPsw.equals(storedPsw)){
                        dialogInterface.getLightInfos(
                                textInputLayoutName.getEditText().getText().toString(),
                                textInputLayoutOpName.getEditText().getText().toString(),
                                selectedSection);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dialogInterface = (AddLightDialogInterface) context;

    }

    private void setSelectSectionBuilder(AlertDialog.Builder selectBuilder, TextInputEditText selectLightsTextInput) {
        selectBuilder.setTitle(R.string.select_section);
        selectBuilder.setCancelable(false);
        ArrayList<String> sectionsName = dbHelper.getAllSectionsName();
        String[] sectionsArray = new String[sectionsName.size()];

        if (selectedSection.equals(""))
            clickedItem = -1;

        for (int i = 0; i < sectionsName.size(); i++) {
            sectionsArray[i] = sectionsName.get(i);
            if (sectionsName.get(i).equals(selectedSection))
                clickedItem = i;
        }

        selectBuilder.setSingleChoiceItems(sectionsArray, clickedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedSection = sectionsArray[which];
            }
        });

        selectBuilder.setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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