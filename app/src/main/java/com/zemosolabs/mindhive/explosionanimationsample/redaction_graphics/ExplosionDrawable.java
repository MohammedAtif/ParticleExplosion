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

import android.animation.Animator;
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
import android.util.Log;

/**
 * @author atif
 * Created on 16/02/18.
 */

public class ExplosionDrawable extends DrawableWrapper implements RedactInterface, Animator.AnimatorListener {

    private static final String TAG = "ExplosionDrawable";

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;
    private DrawableCallbackInterface drawableCallbackInterface;
    private ObjectAnimator mExplosionAnimator;
    private final Matrix mRedactMatrix = new Matrix();

    private int mLayerIndex = -1;

    private int mIndex;
    private int mCount;
    private final int mMaxAnimationCount;

    private int mDelta;
    private int mAngleDelta;
    private int mAlphaDelta;
    private float mScaleDelta;

    private int mCurrentAngle;
    private int mCurrentAlpha;
    private float mCurrentScale;

    private int mInitialAngle = 0;
    private int mInitialAlpha = 255;
    private float mInitialScale = 0.5f;

    //region Constructors

    /**
     * Creates a new wrapper around the specified drawable.
     * @param context Context of the Activity or app
     * @param resId resource Id of the drawable file to be loaded
     * @param animatorUpdateListener Listener to listen for animation changes
     * @param maxAnimationCount Maximum animation time allowed for this animation
     */
    public ExplosionDrawable(@NonNull Context context, @DrawableRes int resId, @NonNull ValueAnimator.AnimatorUpdateListener animatorUpdateListener, @NonNull DrawableCallbackInterface drawableCallbackInterface, int maxAnimationCount) {
        this(context.getResources().getDrawable(resId, context.getTheme()), animatorUpdateListener, drawableCallbackInterface, maxAnimationCount);
    }

    /**
     * Creates a new wrapper around the specified drawable
     * @param drawable Drawable to be added to the Explosion Wrapper
     * @param animatorUpdateListener Listener to listen for animation changes
     * @param maxAnimationCount Maximum animation time allowed for this animation
     */
    public ExplosionDrawable(@NonNull Drawable drawable, @NonNull ValueAnimator.AnimatorUpdateListener animatorUpdateListener, @NonNull DrawableCallbackInterface drawableCallbackInterface, int maxAnimationCount){
        super(drawable);
        this.mAnimatorUpdateListener = animatorUpdateListener;
        this.mMaxAnimationCount = maxAnimationCount;
        this.drawableCallbackInterface = drawableCallbackInterface;
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
        float scaleFactor = mCurrentScale * canvas.getWidth() / width;
        mRedactMatrix.setRotate(mCurrentAngle, pivotX, pivotY);
        mRedactMatrix.postScale(scaleFactor, scaleFactor);
        mRedactMatrix.postTranslate(canvas.getWidth()/2 - pivotX*scaleFactor, canvas.getHeight()/2 - pivotY*scaleFactor);
        canvas.setMatrix(mRedactMatrix);
        d.setAlpha(mCurrentAlpha);
        d.draw(canvas);
        canvas.restoreToCount(saveCount);
    }


    //endregion

    //region Private Methods

    private void initializeAnimator(){
        mExplosionAnimator = ObjectAnimator.ofInt(this, "Explosion", 0, 100);
        mExplosionAnimator.setDuration(100);
        mExplosionAnimator.addUpdateListener(mAnimatorUpdateListener);
    }

    /**
     * Generates the initial values for the Exploder
     */
    private void initializeTransform(){
        mCount = RedactValueGenerator.getRandomCount(5, mMaxAnimationCount);
        mIndex = RedactValueGenerator.getRandomCount(0, mCount);
        mInitialAngle = RedactValueGenerator.getRandomAngleValue(180);
        mDelta = RedactValueGenerator.getRandomAngleValue(180) - 90;
        Log.d(TAG, "Generated Count : "+mCount+" Generated Index : "+mIndex);
    }

    private void applyTransform(){
        float k = (float) mIndex / (float) mCount;
        float mRadius = k * k;
        int mOpacity = Math.round(255 * (1 - mRadius));
        mScaleDelta = mRadius - mInitialScale;
        mAlphaDelta = mOpacity - mInitialAlpha;
        mAngleDelta = Math.round(mDelta * k);
        this.mExplosionAnimator.removeListener(this);
        this.mExplosionAnimator.addListener(this);
        this.mExplosionAnimator.start();
    }

    //endregion

    //region Interface Methods

    @Override
    public void setLayerIndex(int layerIndex) {
        this.mLayerIndex = layerIndex;
    }

    @Override
    public int getLayerIndex() {
        return mLayerIndex;
    }

    @Override
    public void setExplosion(int explosionFactor) {
        final float factor = explosionFactor/100f;
        mCurrentScale = mInitialScale + mScaleDelta * factor;
        mCurrentAlpha = Math.round(mInitialAlpha + mAlphaDelta * factor);
        mCurrentAngle = Math.round(mInitialAngle + mAngleDelta * factor);
        invalidateSelf();
    }

    @Override
    public void startExplosion() {
        initializeAnimator();
        if(mIndex >= mCount){
            initializeTransform();
            drawableCallbackInterface.moveToTop(this);
        }
        applyTransform();
    }

    @Override
    public void endExplosion() {
        this.mExplosionAnimator.removeListener(this);
        this.mExplosionAnimator.end();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mInitialScale = mCurrentScale;
        mInitialAngle = mCurrentAngle;
        mInitialAlpha = mCurrentAlpha;
        mIndex++;
        startExplosion();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    //endregion
}
