package com.example.android.scheduler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.android.scheduler.R;
import com.example.android.scheduler.dialogs.AuthDialogFragment;
import com.example.android.scheduler.global.Global;
import com.google.firebase.auth.FirebaseAuth;

// TODO: 17.07.2019 FirebaseAuth.AuthStateListener???
public class AuthActivity extends AppCompatActivity {

    public static final String LOG_TAG = AuthActivity.class.getSimpleName();

    private FirebaseAuth auth;

    private EditText email;
    private EditText password;
    private Button sign_in;
    private Button sign_up;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
        progressBar = findViewById(R.id.progress_bar);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(LOG_TAG, "auth on activity result called");
        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCodes.SIGN_OUT) {
                Global.user = null;
                auth.signOut();
                Log.i(LOG_TAG, "signed out");
            }
        }
    }

    public void signIn(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                this,
                task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Global.user = auth.getCurrentUser();
                        if (Global.user != null)
                            startActivityForResult(
                                    new Intent(
                                            this,
                                            MainActivity.class
                                    ),
                                    RequestCodes.SIGN_OUT
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
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                this,
                task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Global.user = auth.getCurrentUser();
                        if (Global.user != null) {
                            AuthDialogFragment dialog = new AuthDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(
                                    "msg",
                                    "Successful account creation. You will sign in as " + email
                            );
                            dialog.setArguments(bundle);
                            dialog.setListener(
                                    (dialog1, id) ->
                                            startActivityForResult(
                                                    new Intent(
                                                            this,
                                                            MainActivity.class
                                                    ),
                                                    RequestCodes.SIGN_OUT
                                            )
                            );
                            dialog.show(getSupportFragmentManager(), "");
                        } else
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