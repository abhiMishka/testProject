package com.abhishek.testproject;

import android.app.Application;

/**
 * Created by abhishekkumar on 17/11/16.
 */

public class Global extends Application {

    static Global instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static Global getInstance() {
        return instance;
    }


}
