package com.example.shortcutsstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show=findViewById(R.id.tv_show);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1)
        {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

            tv_show.setText("最大支持的快捷方式数："+shortcutManager.getMaxShortcutCountPerActivity());
        }

    }

    public void createCuts(View view) {
        Intent intent=new Intent(this,DynamicActivity.class);
        startActivity(intent);
    }

    public void createDesktopCuts(View view) {
        Intent intent=new Intent(this,DeskTopShortCutsActivity.class);
        startActivity(intent);
    }

    public void createOldDesktopCuts(View view) {
        //  创建快捷方式的intent广播
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 添加快捷名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "旧版本");
        //  快捷图标是允许重复(不一定有效)
        shortcut.putExtra("duplicate", false);
        // 快捷图标
        // 使用资源id方式
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.add);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        // 使用Bitmap对象模式
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);

        Intent actionIntent=new Intent(this,OldActivity.class);
        //指定快捷方式启动的activity
//        ComponentName componentName = new ComponentName(this, OldActivity.class);
        // 添加携带的下次启动要用的Intent信息
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        // 发送广播
        sendBroadcast(shortcut);
    }
}
