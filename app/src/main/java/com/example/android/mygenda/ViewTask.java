package com.example.android.mygenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ViewTask extends AppCompatActivity {

    TextView mTitle;
    TextView mDescripton;
    ProgressBar mProgressBar;

    String intentTitle;
    List<ParseObject> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        //Setea el toolbar de la app
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTitle = (TextView)findViewById(R.id.title_task);
        mDescripton = (TextView)findViewById(R.id.description_task);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.INVISIBLE);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String intentTitle = (String)b.get("Title");
        String description = (String)b.get("Description");
        mTitle.setText(intentTitle);
        mDescripton.setText(description);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_TITLE);
        query.whereEqualTo(ParseConstants.KEY_TASK_TITLE, intentTitle);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> task, ParseException e) {
                if (e == null) {

                    mTask = task;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewTask.this);
            builder.setMessage("Delete the task?");
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    ParseObject task = mTask.get(0);
                    task.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {
                                toMainActivity();
                            }
                            else{
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(ViewTask.this);
                                builder.setMessage(e.getMessage());
                                AlertDialog dialog = builder.create();
                                dialog.show();*/
                                toMainActivity();
                            }
                        }
                    });


                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            AlertDialog dialog = builder.create();
            dialog.show();

            ParseObject task = mTask.get(0);
            task.deleteInBackground();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void toMainActivity(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
