package com.example.android.mygenda;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    TextView mSignUp;
    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        mUsername = (EditText)findViewById(R.id.username);
        mPassword = (EditText)findViewById(R.id.password);

        mSignUp =(TextView)findViewById(R.id.signup_textview);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logIn(View view){

        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please fill all the fileds required");
            builder.setTitle("Opps!");
            builder.setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{

            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if(e == null){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
