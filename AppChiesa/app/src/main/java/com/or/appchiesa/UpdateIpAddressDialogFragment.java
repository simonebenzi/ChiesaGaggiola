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

public class UpdateIpAddressDialogFragment extends AppCompatDialogFragment {

    private DBHelper dbHelper;
    private TextInputLayout textInputLayoutIpAddress;
    private TextInputLayout textInputLayoutPsw;
    private String oldIpAddress;

    public UpdateIpAddressDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        dbHelper = new DBHelper(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_ip_dialog, null);
        textInputLayoutIpAddress = (TextInputLayout) view.findViewById(R.id.ip_address_edit_text);
        textInputLayoutPsw = (TextInputLayout) view.findViewById(R.id.light_psw_ed);

        oldIpAddress = dbHelper.getIpAddress();
        textInputLayoutIpAddress.getEditText().setText(oldIpAddress);

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
                    if(insertedPsw.equals(storedPsw)){
                        String newIpAddress = textInputLayoutIpAddress.getEditText()
                                .getText().toString();
                        dbHelper.updateIpAddress(newIpAddress, oldIpAddress);
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
    }
}