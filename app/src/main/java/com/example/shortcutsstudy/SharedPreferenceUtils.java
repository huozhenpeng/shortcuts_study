package com.example.shortcutsstudy;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SharedPreferenceUtils {

    public static void saveObj(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences("config", 0);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.close();
            String objBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            sp.edit().putString(key, objBase64).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getObj(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", 0);
        String objBase64 = sp.getString(key, null);
        if (TextUtils.isEmpty(objBase64)) {
            return null;
        }
        byte[] base64Bytes = Base64.decode(objBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);

        ObjectInputStream ois;
        Object obj = null;
        try {
            ois = new ObjectInputStream(bais);
            obj = (Object) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
