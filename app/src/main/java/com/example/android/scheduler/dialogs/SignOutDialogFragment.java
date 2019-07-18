package com.example.android.scheduler.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SignOutDialogFragment extends DialogFragment {

    private DialogInterface.OnClickListener positiveListener = (dialog, id) -> {
    };

    public void setPositiveListener(DialogInterface.OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return
                new AlertDialog.Builder(getActivity())
                        .setMessage("You will sign out. Are you sure?")
                        .setPositiveButton(
                                "yes",
                                positiveListener
                        )
                        .setNegativeButton(
                                "no",
                                (dialog, id) -> {
                                }
                        )
                        .create();
    }
}
