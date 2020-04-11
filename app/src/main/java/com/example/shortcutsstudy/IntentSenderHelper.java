package com.example.shortcutsstudy;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class IntentSenderHelper {

    private static final String TAG = "IntentSenderHelper";

    public static IntentSender getDefaultIntentSender(@NonNull Context context, @NonNull String action) {
        return PendingIntent.getBroadcast(context, 0, new Intent(action),
                PendingIntent.FLAG_ONE_SHOT).getIntentSender();
    }


    public static IntentSender getDefaultIntentSender(@NonNull Context context, @NonNull String action, Class<?> clz, @Nullable Bundle bundle) {
        Intent intent = new Intent(action);
        intent.setComponent(new ComponentName(context, clz));
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT).getIntentSender();
    }
}
