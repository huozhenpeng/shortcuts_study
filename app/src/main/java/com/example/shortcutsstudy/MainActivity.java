package com.example.shortcutsstudy;

import androidx.appcompat.app.AppCompatActivity;

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

//            tv_show.setText("最大支持的快捷方式数："+shortcutManager.getMaxShortcutCountPerActivity());
            tv_show.setText("最大支持的快捷方式数："+getString(R.string.dynamic_shortcut_long_label1));
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
}
