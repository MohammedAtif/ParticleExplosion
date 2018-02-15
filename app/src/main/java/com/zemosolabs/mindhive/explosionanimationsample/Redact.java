package com.zemosolabs.mindhive.explosionanimationsample;

import android.support.annotation.IntRange;

/**
 * @author atif
 *         Created on 15/02/18.
 */

public interface Redact {
    void setRedaction(@IntRange(from = 0, to = 100) int redactFactor);
}
