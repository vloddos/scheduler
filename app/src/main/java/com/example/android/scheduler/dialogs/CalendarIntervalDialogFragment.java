package com.example.android.scheduler.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CalendarIntervalDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return
                new AlertDialog.Builder(getActivity())
                        .setMessage("Invalid calendar interval\nstart datetime>end datetime")
                        .setNeutralButton("ok", (dialog, id) -> {
                        })
                        .create();
    }
}
