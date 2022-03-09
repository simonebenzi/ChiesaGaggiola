package com.or.appchiesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class PasswordDialogFragment extends AppCompatDialogFragment {

    private PasswordDialogInterface dialogInterface;
    private DBHelper dbHelper;
    private TextInputLayout textInputLayoutPsw;

    private String type;
    private String item;
    private SQLiteDatabase db;

    interface PasswordDialogInterface {
        void updateLightRecycler();
        void updateScenarioRecycler();
    }

    public PasswordDialogFragment(String item, String type, SQLiteDatabase db) {
        this.item = item;
        this.db = db;
        this.type = type;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        dbHelper = new DBHelper(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_psw_dialog, null);
        textInputLayoutPsw = (TextInputLayout) view.findViewById(R.id.psw_edit_text);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    // To maintain PasswordDialogFragment open when inserted wrong password
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
                        if (type.equals("light")){
                            dbHelper.deleteLight(db, item);
                            dialogInterface.updateLightRecycler();
                        }
                        else if(type.equals("scenario")){
                            dbHelper.deleteScenario(db, item);
                            dialogInterface.updateScenarioRecycler();
                        }
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

        dialogInterface = (PasswordDialogInterface) context;

    }
}