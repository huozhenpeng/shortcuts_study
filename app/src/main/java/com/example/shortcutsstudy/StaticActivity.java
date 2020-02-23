package com.example.shortcutsstudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class StaticActivity extends AppCompatActivity {

    private TextView tv_second;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        tv_second=findViewById(R.id.tv_second);
        if (getIntent().getData() != null && TextUtils.equals(getIntent().getAction(), Intent.ACTION_VIEW)) {
            Uri uri = getIntent().getData();
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments != null && pathSegments.size() > 0) {
                tv_second.setText(pathSegments.get(0));
            }
        }
    }
}
