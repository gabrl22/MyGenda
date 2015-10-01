package com.example.android.mygenda;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class NewTask extends AppCompatActivity {

    EditText mTitleEditText;
    EditText mDescriptionEditText;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mTitleEditText = (EditText)findViewById(R.id.title_task);
        mDescriptionEditText = (EditText)findViewById(R.id.description_task);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

    }

    public void saveTask(View view){

        mProgressBar.setVisibility(View.VISIBLE);
        String taskTitle = mTitleEditText.getText().toString();
        String taskDescription = mDescriptionEditText.getText().toString();

        ParseObject task = new ParseObject(ParseConstants.CLASS_TITLE);
        task.put(ParseConstants.KEY_TASK_TITLE, taskTitle);
        task.put(ParseConstants.KEY_TASK_DESCRIPTION, taskDescription);
        task.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //Done
                    mProgressBar.setVisibility(View.INVISIBLE);

                    Intent intent = new Intent(NewTask.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewTask.this);
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
