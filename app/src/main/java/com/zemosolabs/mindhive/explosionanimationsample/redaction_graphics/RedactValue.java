package com.zemosolabs.mindhive.explosionanimationsample.redaction_graphics;

import android.support.annotation.DrawableRes;

/**
 * @author atif
 * Created on 17/02/18.
 */

public class RedactValue {
    private int count;
    private int steps;
    @DrawableRes private int resId;

    public RedactValue(int count, int steps, @DrawableRes int resId) {
        this.count = count;
        this.steps = steps;
        this.resId = resId;
    }

    public int getCount() {
        return count;
    }

    public int getSteps() {
        return steps;
    }

    public int getResId() {
        return resId;
    }
}
