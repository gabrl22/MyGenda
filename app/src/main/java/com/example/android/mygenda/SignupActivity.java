package com.example.android.mygenda;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    EditText mEmail;
    EditText mUserName;
    EditText mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        mEmail = (EditText) findViewById(R.id.email);
        mUserName = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);


    }


    public void signUp(View view) {

        String email = mEmail.getText().toString().trim();
        String username = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();


        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please fill all the fileds required");
            builder.setTitle("Opps!");
            builder.setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
           final ParseUser parseUser = new ParseUser();
            parseUser.setEmail(email);
            parseUser.setUsername(username);
            parseUser.setPassword(password);

            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                        builder.setMessage(e.getMessage());
                        builder.setTitle("Error");
                        builder.setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            });
        }
    }
}
