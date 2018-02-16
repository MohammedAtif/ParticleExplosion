/*
 * Copyright 2018 Mohammed Atif
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.zemosolabs.mindhive.explosionanimationsample.redaction_graphics;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * @author atif
 * Created on 16/02/18.
 */

public class ExplosionDrawable extends DrawableWrapper implements RedactInterface {

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;
    private ObjectAnimator mExplosionAnimator;
    private final int mMaxAnimationTime;

    private final Matrix mRedactMatrix = new Matrix();
    private int mCurrentAngle;
    private float mCurrentScale;
    private int mCurrentAlpha;

    //region Constructors

    /**
     * Creates a new wrapper around the specified drawable.
     * @param context Context of the Activity or app
     * @param resId resource Id of the drawable file to be loaded
     * @param animatorUpdateListener Listener to listen for animation changes
     */
    public ExplosionDrawable(@NonNull Context context, @DrawableRes int resId, @NonNull ValueAnimator.AnimatorUpdateListener animatorUpdateListener, int maxAnimationTime) {
        this(context.getResources().getDrawable(resId, context.getTheme()), animatorUpdateListener, maxAnimationTime);
    }

    public ExplosionDrawable(@NonNull Drawable drawable, @NonNull ValueAnimator.AnimatorUpdateListener animatorUpdateListener, int maxAnimationTime){
        super(drawable);
        this.mAnimatorUpdateListener = animatorUpdateListener;
        this.mMaxAnimationTime = maxAnimationTime;
        initializeExploder();
    }

    //endregion

    //region Wrapper Overridden Methods

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
        mRedactMatrix.setRotate(mCurrentAngle, pivotX, pivotY);
        mRedactMatrix.postScale(mCurrentScale, mCurrentScale);
        mRedactMatrix.postTranslate(canvas.getWidth()/2 - pivotX*mCurrentScale, canvas.getHeight()/2 - pivotY*mCurrentScale);
        canvas.setMatrix(mRedactMatrix);
        setAlpha(mCurrentAlpha);
        d.draw(canvas);
        canvas.restoreToCount(saveCount);
    }


    //endregion

    //region Private Methods

    private void initializeExploder(){
        this.mCurrentAlpha = 125;
        this.mCurrentScale = 0.5f;
        this.mCurrentAngle = 90;
        final int startValue = 0;
        final int endValue = 100;
        final int duration = 1000;
        this.mExplosionAnimator = ObjectAnimator.ofInt(this, "Explosion", startValue, endValue);
        this.mExplosionAnimator.setDuration(duration);
        this.mExplosionAnimator.setRepeatMode(ValueAnimator.RESTART);
        this.mExplosionAnimator.setRepeatCount(ValueAnimator.INFINITE);
        this.mExplosionAnimator.addUpdateListener(mAnimatorUpdateListener);
    }

    //endregion

    //region Interface Methods

    @Override
    public void setExplosion(int explosionFactor) {
        float factor = explosionFactor/100f;
        this.mCurrentAngle = Math.round(factor*360);
        this.mCurrentAlpha = 255 - Math.round(factor*255);
        this.mCurrentScale = factor + 0.5f;
        invalidateSelf();
    }

    @Override
    public void startExplosion() {
        this.mExplosionAnimator.start();
    }

    @Override
    public void endExplosion() {
        this.mExplosionAnimator.end();
    }

    //endregion
}
