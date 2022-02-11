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

public class ModifyScenarioDialogFragment extends AppCompatDialogFragment {

    private ModifyGroupDialogInterface dialogInterface;
    private int position;
    private DBHelper dbHelper;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPsw;


    interface ModifyGroupDialogInterface {
        void modifyGroupName(String groupName, int position);
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
        dbHelper = new DBHelper(getContext());

        final String groupName = dbHelper.getAllScenariosName().get(position);
        textInputLayoutName.getEditText().setText(groupName);

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

                    String psw = textInputLayoutPsw.getEditText().getText().toString();
                    if(psw.equals(MainActivity.PASSWORD)){
                        String newGroupName = textInputLayoutName.getEditText()
                                .getText().toString();;
                        dialogInterface.modifyGroupName(newGroupName, position);
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

        dialogInterface = (ModifyGroupDialogInterface) context;

    }
}