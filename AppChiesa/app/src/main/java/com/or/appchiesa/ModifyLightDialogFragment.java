package com.or.appchiesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.textfield.TextInputLayout;

public class ModifyLightDialogFragment extends AppCompatDialogFragment {

    private ModifyLightDialogInterface dialogInterface;
    private int position;
    private String section;
    private DBHelper dbHelper;

    interface ModifyLightDialogInterface {
        void modifyLightDetails(String lightName, String ipAddress, int position, String section);
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
        final TextInputLayout textInputLayoutName =
                (TextInputLayout) view.findViewById(R.id.light_name_edit_text);
        final TextInputLayout textInputLayoutIpAddress =
                (TextInputLayout) view.findViewById(R.id.ip_address_edit_text);

        final String lightName = dbHelper.getAllLightsNameFromSection(section).get(position);
        //final String lightName = Light.lights.get(this.position).getName();
        textInputLayoutName.getEditText().setText(lightName);
        final String lightIpAddress = dbHelper.getAllLightsIpAddressFromSection(section).get(position);
        //final String lightIpAddress = Light.lights.get(this.position).getIpAddress();
        textInputLayoutIpAddress.getEditText().setText(lightIpAddress);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.modify, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newLightName = textInputLayoutName.getEditText()
                                .getText().toString();
                        String newIpAddress = textInputLayoutIpAddress.getEditText()
                                .getText().toString();
                        dialogInterface.modifyLightDetails(newLightName, newIpAddress, position, section);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dialogInterface = (ModifyLightDialogInterface) context;

    }
}