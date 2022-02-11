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

import com.google.android.material.textfield.TextInputLayout;

public class ModifyLightDialogFragment extends AppCompatDialogFragment {

    private ModifyLightDialogInterface dialogInterface;
    private int position;
    private String section;
    private DBHelper dbHelper;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPsw;

    interface ModifyLightDialogInterface {
        void modifyLightDetails(String lightName, int position, String section);
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

        final String lightName = dbHelper.getAllLightsNameFromSection(section).get(position);
        //final String lightName = Light.lights.get(this.position).getName();
        textInputLayoutName.getEditText().setText(lightName);
        final String lightIpAddress = dbHelper.getIpAddress();
        //final String lightIpAddress = Light.lights.get(this.position).getIpAddress();

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
                    String storedPsw = textInputLayoutPsw.getEditText().getText().toString();
                    if(insertedPsw.equals(storedPsw)){
                        String newLightName = textInputLayoutName.getEditText()
                                .getText().toString();
                        dialogInterface.modifyLightDetails(newLightName, position, section);
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

        dialogInterface = (ModifyLightDialogInterface) context;

    }
}