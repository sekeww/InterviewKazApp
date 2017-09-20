package com.shapp.www.interviewkazapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class SignUpActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText iinEditText;
    private Button signUpButton;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        iinEditText = (EditText) findViewById(R.id.iinEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClick();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpBtnClick();
            }
        });
    }

    private void onSignUpBtnClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String iin = iinEditText.getText().toString();

        BackendlessUser user = new BackendlessUser();
        user.setProperty("name", username);
        user.setProperty("iin", iin);
        user.setPassword(password);

        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(SignUpActivity.this, "Registered successfully. Please, login.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("SignUp", "failed to register" + fault.getMessage());
                Snackbar snackbar = Snackbar
                        .make(mLinearLayout, fault.getMessage() + "!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });

// Changing message text color
                snackbar.setActionTextColor(Color.RED);

// Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        });
    }

    private void onLoginClick() {
        finish();
    }
}
