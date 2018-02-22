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

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.zemosolabs.mindhive.explosionanimationsample.R;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author atif
 *         Created on 16/02/18.
 */

public class RedactionDrawable extends LayerDrawable implements ValueAnimator.AnimatorUpdateListener, DrawableCallbackInterface {

    private final static String TAG = "RedactionDrawable";

    private Context mContext;

    private int layerCount = 0;

    private final static int DEFAULT_ANIMATION_COUNT = 50; //number of steps
    private List<RedactValue> redactValues = new ArrayList<>();
    private List<SoftReference<View>> mAnimationTargets = new ArrayList<>();

    //region Constructors

    /**
     * Creates a new layer drawable with the list of specified layers.
     * @param layers a list of drawables to use as layers in this new drawable,
     *               must be non-null
     * @param context Context of the Activity or App
     */
    public RedactionDrawable(Context context, @NonNull Drawable[] layers) {
        super(layers);
        this.mContext = context;
    }

    /**
     * Creates a new layer drawable with empty array for the drawables
     * @param context Context of the Activity or App
     */
    public RedactionDrawable(Context context){
        super(new Drawable[0]);
        this.mContext= context;
    }

    //endregion

    //region Layer Drawable Overridden Methods

    @Override
    public int addLayer(Drawable drawable) {
        return addLayer(drawable, DEFAULT_ANIMATION_COUNT);
    }


    public int addLayer(Drawable drawable, int maxAnimationTime){
        layerCount++;
        Log.d(TAG, "Total layer count is : "+layerCount);
        int layerIndex = -1;
        if(drawable instanceof ExplosionDrawable) {
            layerIndex = super.addLayer(drawable);
            ((ExplosionDrawable)drawable).setLayerIndex(layerIndex);
        }else{
            ExplosionDrawable explosionDrawable = new ExplosionDrawable(drawable, this, this, maxAnimationTime);
            layerIndex =  super.addLayer(explosionDrawable);
            explosionDrawable.setLayerIndex(layerIndex);
        }
        return layerIndex;
    }


    //endregion

    //region Public Controllers

    /**
     * Generates the animation drawable with default list of drawables
     */
    public void generateDefaultLayers(){
        generateDefaultArray();
        for(RedactValue redactValue : redactValues){
            for(int i=0; i<redactValue.getCount(); i++){
                this.addLayer(new ExplosionDrawable(mContext, redactValue.getResId(), this, this, redactValue.getSteps()));
            }
        }
    }

    public void startAnimation(){
        int size = getNumberOfLayers();
        for(int i=0; i<size; i++){
            ExplosionDrawable explosionDrawable = (ExplosionDrawable) getDrawable(i);
            if(explosionDrawable != null) {
                explosionDrawable.startExplosion();
            }
        }
    }

    public void endAnimation(){
        for(int i=0; i<layerCount; i++){
            ExplosionDrawable explosionDrawable = (ExplosionDrawable) getDrawable(i);
            if(explosionDrawable != null) {
                explosionDrawable.endExplosion();
            }
        }
    }

    /**
     * Adds the view to the drawable
     * @throws IllegalStateException Throws IllegalStateException if the View is already added as listener
     * @param view View in which the animation has to be displayed
     */
    public void addAnimationTarget(@NonNull View view){
        for(SoftReference<View> target : mAnimationTargets){
            if(view.equals(target.get())){
                throw new IllegalStateException("Current View is already attached to the drawable");
            }
        }
        mAnimationTargets.add(new SoftReference<>(view));
    }

    /**
     * Remvoes the view from the drawable
     * @param view View which no longer exists on the screen or no longer need the Animation
     */
    public void removeAnimationTarget(@NonNull View view){
        int size = mAnimationTargets.size();
        for(int i = 0; i<size; i++){
            SoftReference<View> target = mAnimationTargets.get(i);
            if(view.equals(target.get())){
                mAnimationTargets.remove(i);
                return;
            }
        }
    }

    //endregion

    //region Private Methods

    private void generateDefaultArray(){
        redactValues.clear();
        redactValues.add(new RedactValue(3, 100, R.drawable.explosion_01));
        redactValues.add(new RedactValue(1, 50, R.drawable.explosion_03));
        redactValues.add(new RedactValue(1, 75, R.drawable.explosion_04));
        redactValues.add(new RedactValue(2, 40, R.drawable.explosion_08));
        redactValues.add(new RedactValue(2, 70, R.drawable.blobs_01));
        redactValues.add(new RedactValue(1, 40, R.drawable.blobs_02));
        redactValues.add(new RedactValue(1, 100, R.drawable.blobs_03));
        redactValues.add(new RedactValue(1, 100, R.drawable.blobs_04));
        redactValues.add(new RedactValue(3, 70, R.drawable.blobs_05));
        redactValues.add(new RedactValue(1, 40, R.drawable.blobs_06));
    }

    //endregion

    //region Interface Methods

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        for(SoftReference<View> target : mAnimationTargets){
            target.get().invalidate();
        }
    }

    @Override
    public void moveToTop(ExplosionDrawable explosionDrawable) {
        this.setDrawable(explosionDrawable.getLayerIndex(), null);
        this.addLayer(explosionDrawable);
    }

    //endregion

}
