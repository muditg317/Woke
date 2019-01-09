package com.pineapple.woke.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.pineapple.woke.R;

public class DialogFragment_Notif extends DialogFragment {

    public DialogFragment_Notif() {
        // Empty constructor required for DialogFragment
    }

    public static DialogFragment_Notif newInstance(String title) {
        DialogFragment_Notif frag = new DialogFragment_Notif();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Testing dialog");

        // Add the buttons
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DialogFragment_Notif.this.getDialog().dismiss();
            }
        });
        return alertDialogBuilder.create();
    }


}
