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

public class ModifyGroupDialogFragment extends AppCompatDialogFragment {

    private ModifyGroupDialogInterface dialogInterface;
    private int position;
    private DBHelper dbHelper;


    interface ModifyGroupDialogInterface {
        void modifyGroupName(String groupName, int position);
    }

    public ModifyGroupDialogFragment(int position) {
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_group_dialog, null);
        final TextInputLayout textInputLayout =
                (TextInputLayout) view.findViewById(R.id.group_name_edit_text);
        dbHelper = new DBHelper(getContext());

        final String groupName = dbHelper.getAllScenariosName().get(position);
        textInputLayout.getEditText().setText(groupName);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.modify, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newGroupName = textInputLayout.getEditText()
                                .getText().toString();;
                        dialogInterface.modifyGroupName(newGroupName, position);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dialogInterface = (ModifyGroupDialogInterface) context;

    }
}