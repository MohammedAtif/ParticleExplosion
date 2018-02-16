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
import android.view.View;

import com.zemosolabs.mindhive.explosionanimationsample.R;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author atif
 *         Created on 16/02/18.
 */

public class RedactionDrawable extends LayerDrawable implements ValueAnimator.AnimatorUpdateListener{

    private Context mContext;

    private final int mDefaultAnimationTime = 1000; //1 second
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
        return addLayer(drawable, mDefaultAnimationTime);
    }


    public int addLayer(Drawable drawable, int maxAnimationTime){
        if(drawable instanceof ExplosionDrawable) {
            return super.addLayer(drawable);
        }else{
            return super.addLayer(new ExplosionDrawable(drawable, this, maxAnimationTime));
        }
    }


    //endregion

    //region Public Controllers

    /**
     * Generates the animation drawable with default list of drawables
     */
    public void generateDeafultLayers(){
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.blobs_01, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.blobs_02, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.blobs_03, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.blobs_04, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.blobs_05, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.blobs_06, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.explosion_01, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.explosion_03, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.explosion_04, this, mDefaultAnimationTime));
        this.addLayer(new ExplosionDrawable(mContext, R.drawable.explosion_08, this, mDefaultAnimationTime));
    }

    /**
     * Adds the view to the drawable
     * @throws IllegalStateException Throws IllegalStateException if the View is already added as listener
     * @param view View in which the animation has to be displayed
     */
    public void addAnimationtarget(@NonNull View view){
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

    //region Interface Methods

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        for(SoftReference<View> target : mAnimationTargets){
            target.get().invalidate();
        }
    }

    //endregion

}
