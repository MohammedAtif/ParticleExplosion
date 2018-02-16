package com.zemosolabs.mindhive.explosionanimationsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zemosolabs.mindhive.explosionanimationsample.redaction_graphics.RedactionDrawable;

public class MainActivity extends AppCompatActivity{

    private RedactionDrawable layerDrawable;
    private ImageView imageView;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.redaction_view);
        layerDrawable = new RedactionDrawable(this);
        imageView.setImageDrawable(layerDrawable);
        layerDrawable.generateDefaultLayers();
        layerDrawable.addAnimationTarget(imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        layerDrawable.startAnimation();
    }

    @Override
    protected void onPause() {
        layerDrawable.endAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        layerDrawable.removeAnimationTarget(imageView);
        super.onDestroy();
    }
}
