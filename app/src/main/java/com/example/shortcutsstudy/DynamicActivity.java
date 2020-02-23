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
                builder.setIntent(shortcutInfo.getIntent());
                builder.setRank(shortcutInfo.getRank());
                //
                ShortResource shortResource= ShortcutHelper.getInstance().idMap.get(shortcutInfo.getId());
                if(shortcutInfo!=null)
                {
                    shortResource.setLongLabel(R.string.new_dynamic_shortcut_long_label1);
                    builder.setLongLabel(getString(shortResource.getLongLabel()));
                    builder.setShortLabel(getString(shortResource.getShortLabel()));
                }

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

                ShortcutHelper.getInstance().removeKey(ID_DYNAMIC_1);

            }
        }
        else
        {
            Toast.makeText(this,"版本太低",Toast.LENGTH_SHORT).show();
        }

    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private ShortcutInfo createShortcutInfo1() {
        ShortResource shortResource=new ShortResource();
        shortResource.setShortLabel(R.string.dynamic_shortcut_short_label1);
        shortResource.setLongLabel(R.string.dynamic_shortcut_long_label1);
        ShortcutHelper.getInstance().putKey(ID_DYNAMIC_1,shortResource);

        return new ShortcutInfo.Builder(this, ID_DYNAMIC_1)
                .setShortLabel(getString(shortResource.getShortLabel()))
                .setLongLabel(getString(shortResource.getLongLabel()))
                .setIcon(Icon.createWithResource(this, R.drawable.link))
                //设置等级，在列表中做显示时做排序用的
                .setRank(1)
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com")))
                .build();
    }

}
