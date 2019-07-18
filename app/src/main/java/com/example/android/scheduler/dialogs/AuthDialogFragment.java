package com.example.android.scheduler.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class AuthDialogFragment extends DialogFragment {

    private String msg;
    private DialogInterface.OnClickListener listener = (dialog, id) -> {
    };

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null)
            msg = args.getString("msg");
    }

    public void setListener(@NonNull DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return
                new AlertDialog.Builder(getActivity())
                        .setMessage(msg)
                        .setNeutralButton(
                                "ok",
                                listener
                        )
                        .create();
    }
}
