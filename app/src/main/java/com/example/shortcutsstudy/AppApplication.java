package com.example.shortcutsstudy;

import android.app.Application;
import android.content.Context;

public class AppApplication extends Application {

    public static Context application;
    @Override
    public void onCreate() {
        super.onCreate();

        application=this;
    }
}
