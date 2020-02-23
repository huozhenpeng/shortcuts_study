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
        ShortcutHelper.getInstance().idMap= (Map<String, ShortResource>) SharedPreferenceUtils.getObj(application,"IdMap");
        if(ShortcutHelper.getInstance().idMap==null)
        {
            ShortcutHelper.getInstance().idMap=new HashMap<>();
        }
        Log.e(TAG,""+ShortcutHelper.getInstance().idMap.keySet().size());
    }
}
