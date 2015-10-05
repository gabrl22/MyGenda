package com.example.android.mygenda;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Calendar;

public class NewTask extends AppCompatActivity {

    EditText mTitleEditText;
    EditText mDescriptionEditText;
    ProgressBar mProgressBar;
    String mTaskMonth;
    int mDayTask;

    Calendar mCalendar;
    static int mDay, mMonth, mYear;

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mDay = day;
            mMonth = month;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);


        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitleEditText = (EditText) findViewById(R.id.title_task);
        mDescriptionEditText = (EditText) findViewById(R.id.description_task);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);

        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

    }


    public void saveTask() {

        mProgressBar.setVisibility(View.VISIBLE);

        String taskTitle = mTitleEditText.getText().toString();
        String taskDescription = mDescriptionEditText.getText().toString();
        mDayTask = mDay;
        switch (mMonth){
            case 0:
                mTaskMonth = "JAN";
                break;
            case 1:
                mTaskMonth = "FEB";
                break;
            case 2:
                mTaskMonth = "MAR";
                break;
            case 3:
                mTaskMonth = "APR";
                break;
            case 4:
                mTaskMonth = "MAY";
                break;
            case 5:
                mTaskMonth = "JUN";
                break;
            case 6:
                mTaskMonth = "JUL";
                break;
            case 7:
                mTaskMonth = "AUG";
                break;
            case 8:
                mTaskMonth = "SEP";
                break;
            case 9:
                mTaskMonth = "OCT";
                break;
            case 10:
                mTaskMonth = "NOV";
                break;
            case 11:
                mTaskMonth = "DEC";
                break;
        }

        ParseObject task = new ParseObject(ParseConstants.CLASS_TITLE);
        task.put(ParseConstants.KEY_TASK_TITLE, taskTitle);
        task.put(ParseConstants.KEY_TASK_DESCRIPTION, taskDescription);
        task.put(ParseConstants.KEY_TASK_DAY, mDayTask);
        task.put(ParseConstants.KEY_TASK_MONTH, mTaskMonth);
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

                } else {
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

    public void selectDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_save) {
            //Save the task
            saveTask();
        }
        return super.onOptionsItemSelected(item);
    }


}
