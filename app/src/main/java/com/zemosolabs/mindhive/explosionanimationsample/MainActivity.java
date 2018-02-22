package com.zemosolabs.mindhive.explosionanimationsample;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zemosolabs.mindhive.explosionanimationsample.redaction_graphics.RedactionDrawable;

public class MainActivity extends AppCompatActivity{

//    private RedactionDrawable layerDrawable;
    private AnimationDrawable animationDrawable;
    private ImageView imageView;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.redaction_view);
//        layerDrawable = new RedactionDrawable(this);
//        imageView.setImageDrawable(layerDrawable);
//        layerDrawable.generateDefaultLayers();
//        layerDrawable.addAnimationTarget(imageView);
        animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_01), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_02), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_03), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_04), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_05), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_06), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_06), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_06), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.blobs_06), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.explosion_01), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.explosion_03), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.explosion_04), 100);
        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.explosion_08), 100);
        imageView.setImageDrawable(animationDrawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
//        layerDrawable.startAnimation();
    }

    @Override
    protected void onPause() {
        animationDrawable.stop();
//        layerDrawable.endAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
//        layerDrawable.removeAnimationTarget(imageView);
        super.onDestroy();
    }
}
