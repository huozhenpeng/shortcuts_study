package com.example.shortcutsstudy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DynamicCutsOpenActivity extends AppCompatActivity {

    private TextView tv_message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamiccusopen);
        tv_message=findViewById(R.id.tv_message);
        Intent data=getIntent();
        if(data!=null)
        {
            String message=data.getStringExtra("key");
            if(!TextUtils.isEmpty(message))
            {
                tv_message.setText(message);
            }
        }

    }
}
