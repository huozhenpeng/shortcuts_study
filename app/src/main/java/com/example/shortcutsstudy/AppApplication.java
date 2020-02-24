package com.example.shortcutsstudy;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class AppApplication extends Application {

    public static Context application;
    private String TAG="AppApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        application=this;
    }
}
