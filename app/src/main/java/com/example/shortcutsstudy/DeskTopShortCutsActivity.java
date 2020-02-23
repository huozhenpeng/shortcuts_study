package com.example.shortcutsstudy;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DeskTopShortCutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);
    }

    private String id="PINNED_ID";
    public void createShortCuts(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
                Intent intent = new Intent(this, DynamicCutsOpenActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra("key", "fromPinnedShortcut");

                ShortResource shortResource=new ShortResource();
                shortResource.setShortLabel(R.string.pinned_shortcut_short_label2);
                shortResource.setLongLabel(R.string.pinned_shortcut_long_label2);
                ShortcutHelper.getInstance().putKey(id,shortResource);

                ShortcutInfo pinShortcutInfo = new ShortcutInfo.Builder(this, id)
                        .setShortLabel(getString(shortResource.getShortLabel()))
                        .setLongLabel(getString(shortResource.getLongLabel()))
                        .setIcon(Icon.createWithResource(this, R.drawable.add))
                        .setIntent(intent)
                        .build();

                Intent pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo);
                PendingIntent successCallback = PendingIntent.getBroadcast(this, 0,
                        pinnedShortcutCallbackIntent, 0);
                //这个方式API 26才加进去的
                //https://developer.android.google.cn/reference/android/content/pm/ShortcutManager?hl=zh-cn#requestPinShortcut(android.content.pm.ShortcutInfo,%20android.content.IntentSender)
                boolean b = shortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.getIntentSender());
                Toast.makeText(this,"set pinned shortcuts " + (b ? "success" : "failed") + "!",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this,"版本太低",Toast.LENGTH_SHORT).show();
        }
    }
}
