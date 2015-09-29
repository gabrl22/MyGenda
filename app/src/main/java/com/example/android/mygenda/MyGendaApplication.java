package com.example.android.mygenda;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Gabriel on 09/28/15.
 */
public class MyGendaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "fwlDtJoA9toCnuSu9yHjke37cEygIy5An1FGkgJd", "uHootNyM3fWrAG4C7Q1hwiK05WT0wwlFXsnDcTo3");


        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

    }



}
