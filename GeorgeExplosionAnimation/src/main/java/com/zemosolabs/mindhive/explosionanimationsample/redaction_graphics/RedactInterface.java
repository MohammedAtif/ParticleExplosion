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

import android.support.annotation.IntRange;

/**
 * @author atif
 *         Created on 15/02/18.
 */

public interface RedactInterface {

    void setLayerIndex(int layerIndex);
    int getLayerIndex();

    /**
     * <p>Set the redaction value for the animation at given instance of time.</p>
     * <p>Must always range between 0 to 100</p>
     * <p></p>
     * <p>Mostly used by the {@link android.animation.ObjectAnimator} to update the animation</p>
     * @param redactFactor time value for the redaction
     */
    void setExplosion(@IntRange(from = 0, to = 100) int redactFactor);

    /**
     * Starts the explosion animation for the drawable
     */
    void startExplosion();

    /**
     * Stops the explosion animation for the drawable
     */
    void endExplosion();
}
