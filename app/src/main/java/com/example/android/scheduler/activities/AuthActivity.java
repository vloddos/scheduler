package com.example.android.scheduler.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.scheduler.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

// TODO: 17.07.2019 FirebaseAuth.AuthStateListener???
public class AuthActivity extends AppCompatActivity {

    public static final String LOG_TAG = AuthActivity.class.getSimpleName();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private String token;

    private EditText email;
    private EditText password;
    private Button sign_in;
    private Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = auth.getCurrentUser();
//        updateUI(currentUser);
    }

    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Required");
            valid = false;
        } else
            email.setError(null);

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Required");
            valid = false;
        } else
            password.setError(null);

        return valid;
    }

    public static class AuthDialogFragment extends DialogFragment {

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

    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                this,
                task -> {
                    if (task.isSuccessful()) {
                        user = auth.getCurrentUser();
                        if (user != null)
                            user.getIdToken(true).addOnCompleteListener(
                                    this,
                                    task1 -> {
                                        GetTokenResult getTokenResult = task1.getResult();
                                        if (getTokenResult != null) {
                                            token = getTokenResult.getToken();
                                            if (token != null)
                                                startActivity(
                                                        new Intent(
                                                                this,
                                                                MainActivity.class
                                                        )
                                                );
                                            else
                                                Log.e(LOG_TAG, "token is null");
                                        } else
                                            Log.e(LOG_TAG, "get token result is null");
                                    }
                            );
                        else
                            Log.e(LOG_TAG, "current user is null");
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            AuthDialogFragment dialog = new AuthDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("msg", exception.getMessage());
                            dialog.setArguments(bundle);
                            dialog.show(getSupportFragmentManager(), "");
                        } else
                            Log.e(LOG_TAG, "unknown signing in exception");
                    }
                }
        );
    }

    public void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                this,
                task -> {
                    if (task.isSuccessful()) {
                        user = auth.getCurrentUser();
                        if (user != null)
                            user.getIdToken(true).addOnCompleteListener(
                                    this,
                                    task1 -> {
                                        GetTokenResult getTokenResult = task1.getResult();
                                        if (getTokenResult != null) {
                                            token = getTokenResult.getToken();
                                            if (token != null) {
                                                AuthDialogFragment dialog = new AuthDialogFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putString(
                                                        "msg",
                                                        "Successful account creation. You will sign in as " + email
                                                );
                                                dialog.setArguments(bundle);
                                                dialog.setListener(
                                                        (dialog1, id) ->
                                                                startActivity(
                                                                        new Intent(
                                                                                this,
                                                                                MainActivity.class
                                                                        )
                                                                )
                                                );
                                                dialog.show(getSupportFragmentManager(), "");
                                            } else
                                                Log.e(LOG_TAG, "token is null");
                                        } else
                                            Log.e(LOG_TAG, "get token result is null");
                                    }
                            );
                        else
                            Log.e(LOG_TAG, "current user is null");
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            AuthDialogFragment dialog = new AuthDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("msg", exception.getMessage());
                            dialog.setArguments(bundle);
                            dialog.show(getSupportFragmentManager(), "");
                        } else
                            Log.e(LOG_TAG, "unknown signing up exception");
                    }
                }
        );
    }

    public void onClick(View view) {
        if (!validateForm())
            return;

        String
                email = this.email.getText().toString(),
                password = this.password.getText().toString();

        if (view.getId() == sign_in.getId())
            signIn(email, password);
        else
            signUp(email, password);
    }
}