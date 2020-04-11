package com.example.shortcutsstudy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NormalCreateBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "NormalCreateBroadcastRe";

    public static final String ACTION = "com.shortcut.core.normal_create";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION.equals(action)) {
            String id = intent.getStringExtra("id");
            String label = intent.getStringExtra("label");
        }
    }
}
