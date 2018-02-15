package com.zemosolabs.mindhive.explosionanimationsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.support.annotation.NonNull;

/**
 * @author atif
 * Created on 15/02/18.
 */

public class RedactDrawable extends DrawableWrapper implements Redact{

    private int currentAngle;
    private int currentAlpha;
    private float currentScale;
    private Matrix redactMatrix = new Matrix();

    public RedactDrawable(Context context, int resId) {
        super(context.getResources().getDrawable(resId, context.getTheme()));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Drawable d = getDrawable();
        final Rect bounds = d.getBounds();

        final int width = bounds.right - bounds.left;
        final int height = bounds.bottom - bounds.top;
        final float px = width * 0.5f;
        final float py = height * 0.5f;

        float pivotX = px + bounds.left;
        float pivotY = py + bounds.top;

        final int saveCount = canvas.save();
        redactMatrix.setRotate(currentAngle, pivotX, pivotY);
        redactMatrix.postScale(currentScale, currentScale);
        redactMatrix.postTranslate(canvas.getWidth()/2 - pivotX*currentScale, canvas.getHeight()/2 - pivotY*currentScale);
        canvas.setMatrix(redactMatrix);
        setAlpha(currentAlpha);
        d.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setRedaction(int redactFactor) {
        float factor = redactFactor/100f;
        this.currentAngle = Math.round(factor*360);
        this.currentAlpha = Math.round(factor*255);
        this.currentScale = factor*5;
        invalidateSelf();
    }
}
