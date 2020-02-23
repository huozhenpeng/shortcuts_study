package com.example.shortcutsstudy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ShortcutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent!=null)
        {
            if (Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {

                    ShortcutHelper.getInstance().refreshShortcuts();
                }
            }

        }
    }
}
