package com.zemosolabs.mindhive.explosionanimationsample;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author atif
 * Created on 15/02/18.
 */

public class RedactionView extends Animation {

    private int angle = 30;
    private float scale = 0.1f;

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        transformation.setTransformationType(Transformation.TYPE_BOTH);
        transformation.setAlpha(0.5f);
        Matrix matrix = transformation.getMatrix();
        matrix.postRotate(angle, 200, 200);
        matrix.postScale(scale, scale);
        scale += 0.01;
        angle += 10;
        super.applyTransformation(interpolatedTime, transformation);
    }
}
