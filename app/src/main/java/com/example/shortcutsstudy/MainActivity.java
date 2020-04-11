package com.example.shortcutsstudy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.graphics.drawable.IconCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private TextView tv_show;
    private  RuntimeSettingPage runtimeSettingPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show=findViewById(R.id.tv_show);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int check = ShortcutPermission.check(this);
        String state = "未知";
        switch (check) {
            case ShortcutPermission.PERMISSION_DENIED:
                state = "已禁止";
                break;
            case ShortcutPermission.PERMISSION_GRANTED:
                state = "已同意";
                break;
            case ShortcutPermission.PERMISSION_ASK:
                state = "询问";
                break;
            case ShortcutPermission.PERMISSION_UNKNOWN:
                state = "未知";
                break;
        }
        tv_show.setText(state);
    }

    public void openPermissionSettingPage(View view) {
        if(runtimeSettingPage==null) {
            runtimeSettingPage=new RuntimeSettingPage(MainActivity.this);
        }
        runtimeSettingPage.start();
    }

    public void createShortCut(View view) {
        int check = ShortcutPermission.check(MainActivity.this);
        if (check == ShortcutPermission.PERMISSION_GRANTED || check == ShortcutPermission.PERMISSION_UNKNOWN) {
            Intent intentForShortcut = new Intent(MainActivity.this, ShortCutOpenActivity.class);
            String shortCutName="小说";
            intentForShortcut.putExtra("name", shortCutName);
            intentForShortcut.putExtra("id", shortCutName.hashCode());
            intentForShortcut.putExtra("isShortcut", true);
            intentForShortcut.setAction(Intent.ACTION_VIEW);
            final ShortcutInfoCompat shortcutInfoCompat=new ShortcutInfoCompat.Builder(MainActivity.this,shortCutName)
                    .setShortLabel(shortCutName)
                    .setIcon(IconCompat.createWithResource(this,R.drawable.add))
                    .setIntent(intentForShortcut)
                    .build();

            ShortcutHelper.isShortcutExit(this, shortcutInfoCompat.getId(), shortcutInfoCompat.getShortLabel(), new ShortcutHelper.ShortcutExistCallback() {
                @Override
                public void shortcutNotExist() {
                    boolean pinShortcut = createPinShortcut(shortcutInfoCompat, MainActivity.this);
                    if (pinShortcut) {
                        Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void shortcutExist() {
//                    boolean updatePinShortcut = updatePinShortcut(shortcutInfoCompat, MainActivity.this);
                    Toast.makeText(MainActivity.this,"已存在",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void shortcutExistWithHW() {
//                    boolean updatePinShortcut = updatePinShortcut(shortcutInfoCompat, MainActivity.this);
                    Toast.makeText(MainActivity.this,"已存在",Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            ShortcutPermissionTipDialog shortcutPermissionTipDialog = new ShortcutPermissionTipDialog();
            shortcutPermissionTipDialog.show(getSupportFragmentManager(), "shortcut");
            shortcutPermissionTipDialog.setTitle("快捷方式未开启");
            shortcutPermissionTipDialog.setTvContentTip("检测到权限未开启，请前往系统设置，为此应用打开\"创建桌面快捷方式\"的权限。");
            shortcutPermissionTipDialog.setOnConfirmClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(runtimeSettingPage==null) {
                        runtimeSettingPage=new RuntimeSettingPage(MainActivity.this);
                    }
                    runtimeSettingPage.start();
                }
            });
        }
    }

    private boolean createPinShortcut(@NonNull ShortcutInfoCompat shortcutInfoCompat, @NonNull Context context) {
        Bundle bundle = new Bundle();
        bundle.putString("id", shortcutInfoCompat.getId());
        bundle.putCharSequence("label", shortcutInfoCompat.getShortLabel());
        IntentSender intentSender = IntentSenderHelper.getDefaultIntentSender(context, NormalCreateBroadcastReceiver.ACTION, NormalCreateBroadcastReceiver.class, bundle);
        return ShortcutHelper.requestPinShortcut(context, shortcutInfoCompat, intentSender);
    }

    private boolean updatePinShortcut(@NonNull ShortcutInfoCompat shortcutInfoCompat, @NonNull Context context) {
        return ShortcutHelper.updatePinShortcut(context, shortcutInfoCompat);
    }


}
