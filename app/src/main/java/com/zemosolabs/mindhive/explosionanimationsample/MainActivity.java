package com.zemosolabs.mindhive.explosionanimationsample;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{

    private LayerDrawable layerDrawable;
    private ImageView imageView;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.redaction_view);
        layerDrawable = new LayerDrawable(new Drawable[0]);
        int index;
        for(int i=0; i<100; i++) {
            index = layerDrawable.addLayer(new RedactDrawable(this, R.drawable.blobs_01));
            layerDrawable.setLayerGravity(index, Gravity.CENTER);
            index = layerDrawable.addLayer(new RedactDrawable(this, R.drawable.blobs_03));
            layerDrawable.setLayerGravity(index, Gravity.CENTER);
            index = layerDrawable.addLayer(new RedactDrawable(this, R.drawable.blobs_05));
            layerDrawable.setLayerGravity(index, Gravity.CENTER);
        }
        imageView.setImageDrawable(layerDrawable);
        int layerCount = layerDrawable.getNumberOfLayers();
        for(int i=0; i<layerCount; i++){
            RedactDrawable drawable = (RedactDrawable) layerDrawable.getDrawable(i);
            int start = i%2 == 0 ? 0 : 100;
            int end = i%2 == 0 ? 100 : 0;
            ObjectAnimator animator = ObjectAnimator.ofInt(drawable, "Redaction", start, end);
            animator.setDuration(1000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.addUpdateListener(animatorUpdateListener);
            layerDrawable.setLayerGravity(i, Gravity.CENTER);
            animator.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            animation.getCurrentPlayTime();
            imageView.invalidate();
        }
    };
}
