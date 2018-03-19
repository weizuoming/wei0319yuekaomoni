package com.example.hello.weizuoming20180319.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.hello.weizuoming20180319.R;
import com.example.hello.weizuoming20180319.view.custom.GramophoneView;

public class BannerActivity extends AppCompatActivity {
    private GramophoneView gramophoneView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        gramophoneView = (GramophoneView) findViewById(R.id.gramophone);
    }
    public void pauseOrstart(View view) {
        gramophoneView.pauseOrstart();
    }
}
