package com.zemosolabs.mindhive.explosionanimationsample.redaction_graphics;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

import java.util.Random;

/**
 * @author atif
 * Created on 16/02/18.
 */

public class RedactValueGenerator {

    public static Random randomGenerator = new Random();

    public static int getRandomRange(int min, int max){
        return min + randomGenerator.nextInt(max-min);
    }

    public static int getRandomAlphaValue(@IntRange(from = 10, to = 255) int min, @IntRange(from = 100, to = 255) int max){
        return min + randomGenerator.nextInt(max - min);
    }

    public static float getRandomScaleValue(@FloatRange(from = 0, to = 2) float max){
        float randomFloat = randomGenerator.nextFloat();
        if(randomFloat > max){
            return max;
        }else{
            return randomFloat;
        }
    }

    public static int getRandomAngleValue(@IntRange(from = 0, to = 359) int max){
        return randomGenerator.nextInt(max);
    }
}
