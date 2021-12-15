package com.or.appchiesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

public class AddLightDialogFragment extends AppCompatDialogFragment {

    private AddLightDialogInterface dialogInterface;

    interface AddLightDialogInterface {
        void getLightInfos(String lightName, String ipAddress);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_light_dialog, null);
        final TextInputLayout textInputLayoutName = (TextInputLayout) view.findViewById(R.id.light_name_edit_text);
        final TextInputLayout textInputLayoutIpAddress = (TextInputLayout) view.findViewById(R.id.ip_address_edit_text);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogInterface.getLightInfos(
                                textInputLayoutName.getEditText().getText().toString(),
                                textInputLayoutIpAddress.getEditText().getText().toString());
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dialogInterface = (AddLightDialogInterface) context;

    }
}