package com.example.shortcutsstudy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DynamicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            shortcutManager = getSystemService(ShortcutManager.class);
        }
    }

    private ShortcutManager shortcutManager;
    private String ID_DYNAMIC_1="ID_DYNAMIC_1";
    public void createShortCuts(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            List<ShortcutInfo> shortcutInfo = new ArrayList<>();
            shortcutInfo.add(createShortcutInfo1());
            if (shortcutManager != null) {
                shortcutManager.setDynamicShortcuts(shortcutInfo);
            }
        }
        else
        {
            Toast.makeText(this,"版本太低",Toast.LENGTH_SHORT).show();
        }
    }


    public void updateShortCuts(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutInfo shortcutInfo=ShortcutHelper.getInstance().getDynamicShortcutsById(ID_DYNAMIC_1);
            List<ShortcutInfo> updates=new ArrayList<>();
            if(shortcutInfo!=null)
            {
                //重新构建builder
                final ShortcutInfo.Builder builder = new ShortcutInfo.Builder(this, shortcutInfo.getId());
                Intent intent=shortcutInfo.getIntent();
                if(intent!=null)
                {
                    intent.putExtra("longLabel",R.string.new_dynamic_shortcut_long_label1);
                    intent.putExtra("shortLabel",R.string.new_dynamic_shortcut_short_label1);

                    builder.setIntent(intent);

                    builder.setLongLabel(getString(R.string.new_dynamic_shortcut_long_label1));
                    builder.setShortLabel(getString(R.string.new_dynamic_shortcut_short_label1));
                }

                builder.setRank(shortcutInfo.getRank());


                updates.add(builder.build());
            }
            if (shortcutManager != null) {
                shortcutManager.updateShortcuts(updates);
            }
        }
        else
        {
            Toast.makeText(this,"版本太低",Toast.LENGTH_SHORT).show();
        }

    }

    public void delShortCuts(View view) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            if (shortcutManager != null) {

                List<String> delList=new ArrayList<>();
                delList.add(ID_DYNAMIC_1);

                shortcutManager.removeDynamicShortcuts(delList);


            }
        }
        else
        {
            Toast.makeText(this,"版本太低",Toast.LENGTH_SHORT).show();
        }

    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private ShortcutInfo createShortcutInfo1() {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
        intent.putExtra("longLabel",R.string.dynamic_shortcut_long_label1);
        intent.putExtra("shortLabel",R.string.dynamic_shortcut_short_label1);

        return new ShortcutInfo.Builder(this, ID_DYNAMIC_1)
                .setShortLabel(getString(R.string.dynamic_shortcut_short_label1))
                .setLongLabel(getString(R.string.dynamic_shortcut_long_label1))
                .setIcon(Icon.createWithResource(this, R.drawable.link))
                //设置等级，在列表中做显示时做排序用的
                .setRank(1)
                .setIntent(intent)
                .build();
    }

}
