package com.example.shortcutsstudy;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ShortcutHelper {

    public void putKey(String id_dynamic_1, ShortResource shortResource) {

        idMap.put(id_dynamic_1,shortResource);

        SharedPreferenceUtils.saveObj(mContext,"IdMap",idMap);
    }

    public void removeKey(String id_dynamic_1)
    {
        idMap.remove(id_dynamic_1);
        SharedPreferenceUtils.saveObj(mContext,"IdMap",idMap);
    }

    private static class SingleTonHolder
    {
        private static ShortcutHelper INSTANCE=new ShortcutHelper();
    }

    private ShortcutHelper()
    {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1)
        {
            mContext=AppApplication.application;
            mShortcutManager=mContext.getSystemService(ShortcutManager.class);
        }

    }
    public static ShortcutHelper getInstance()
    {
        return SingleTonHolder.INSTANCE;
    }

    //保存shortcut 的id和 资源文件对应关系
    public Map<String,ShortResource> idMap;


    private  ShortcutManager mShortcutManager;

    private  Context mContext;

    public void refreshShortcuts()
    {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final List<ShortcutInfo> updateList = new ArrayList<>();
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1)
                {
                    for(ShortcutInfo shortcutInfo:getShortcuts())
                    {
                        if(shortcutInfo.isImmutable())
                        {
                            continue;
                        }

                        //重新构建builder
                        final ShortcutInfo.Builder builder = new ShortcutInfo.Builder(mContext, shortcutInfo.getId());
                        builder.setIntent(shortcutInfo.getIntent());
                        builder.setRank(shortcutInfo.getRank());
                        //
                        ShortResource shortResource=idMap.get(shortcutInfo.getId());
                        if(shortcutInfo!=null)
                        {
                            builder.setLongLabel(mContext.getResources().getString(shortResource.getLongLabel()));
                            builder.setShortLabel(mContext.getResources().getString(shortResource.getShortLabel()));
                        }
                        updateList.add(builder.build());

                    }
                    if(updateList.size()>0)
                    {
                       boolean isSuccess= mShortcutManager.updateShortcuts(updateList);
                       if(!isSuccess)
                       {
                           Toast.makeText(AppApplication.application,"rate-limited",Toast.LENGTH_SHORT).show();
                       }

                    }

                }

            }
        });

    }


    public List<ShortcutInfo> getShortcuts()
    {
        final List<ShortcutInfo> shortCuts = new ArrayList<>();
        final HashSet<String> shortCutKeys = new HashSet<>();

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1)
        {
            if(mShortcutManager!=null)
            {
                //检查dynamic shortcuts
                for (ShortcutInfo shortcutInfo:mShortcutManager.getDynamicShortcuts())
                {
                    if(!shortcutInfo.isImmutable())
                    {
                        shortCuts.add(shortcutInfo);
                        shortCutKeys.add(shortcutInfo.getId());
                    }

                }

                //检查pinned shortcuts
                for (ShortcutInfo shortcutInfo : mShortcutManager.getPinnedShortcuts()) {
                    if (!shortcutInfo.isImmutable() && !shortCutKeys.contains(shortcutInfo.getId())) {
                        shortCuts.add(shortcutInfo);
                        shortCutKeys.add(shortcutInfo.getId());
                    }
                }

            }
        }
        return shortCuts;

    }

    public ShortcutInfo getDynamicShortcutsById(String id)
    {
        ShortcutInfo shortCuts = null;

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1)
        {
            if(mShortcutManager!=null)
            {
                //检查dynamic shortcuts
                for (ShortcutInfo shortcutInfo:mShortcutManager.getDynamicShortcuts())
                {
                    if(id.equals(shortcutInfo.getId()))
                    {
                        shortCuts=shortcutInfo;
                    }

                }
            }
        }
        return shortCuts;
    }





}
