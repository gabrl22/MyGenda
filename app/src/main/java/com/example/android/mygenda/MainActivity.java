package com.example.android.mygenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_TITLE = "com.example.android.mygenda.TITLE";


    ListView mListView;
    ProgressBar mProgressBar;
    protected List<ParseObject> mTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);


        //Setea el toolbar de la app
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        //Consigue el usuario que esta activo en el momento
        ParseUser currentUser = ParseUser.getCurrentUser();

        //Si no hay ningun usuario registrado, iniciara el login activity
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Nueva tarea
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//Cierra la tarea anterior
            startActivity(intent);
        } else {
            Log.i("JA", currentUser.toString());
        }


        //Activa el click listener a cada item de la lista
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject task = mTasks.get(position);
                String title = task.getString(ParseConstants.KEY_TASK_TITLE);
                String description = task.getString(ParseConstants.KEY_TASK_DESCRIPTION);
                int day = task.getInt(ParseConstants.KEY_TASK_DAY);
                String month = task.getString(ParseConstants.KEY_TASK_MONTH);

                Intent intent = new Intent(MainActivity.this, ViewTask.class);
                intent.putExtra("Title", title);
                intent.putExtra("Description", description);
                //intent.putExtra("Day", day);
                //intent.putExtra("Month", month);
                startActivity(intent);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final ParseObject task = mTasks.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete the task?");

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        mProgressBar.setVisibility(View.VISIBLE);
                        task.deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    //Done
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    retrieveTasks();
                                }
                            }
                        });


                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                AlertDialog dialog = builder.create();
                dialog.show();


                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        retrieveTasks();
    }

    private void retrieveTasks() {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_TITLE);
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> tasks, ParseException e) {

                if (e == null) {
                    mTasks = tasks;
                    if (mListView.getAdapter() == null) {
                        //Adapter lo que se quiere de los mensajes a la lista
                        TaskAdapter adapter = new TaskAdapter(MainActivity.this,
                                mTasks);
                        mListView.setAdapter(adapter);
                    } else {
                        //refill the the list
                        ((TaskAdapter) mListView.getAdapter()).refill(mTasks);
                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.logout:
                ParseUser.logOut();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void addTask(View view) {

        Intent intent = new Intent(this, NewTask.class);
        startActivity(intent);
    }
}
