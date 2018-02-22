package com.zemosolabs.mindhive.explosionanimationsample.redaction_graphics;

import android.support.annotation.IntRange;

import java.util.Random;

/**
 * @author atif
 * Created on 16/02/18.
 */

public class RedactValueGenerator {

    public static Random randomGenerator = new Random();

    public static int getRandomCount(int min, int max){
        return min + randomGenerator.nextInt(max - min);
    }

    public static int getRandomAngleValue(@IntRange(from = 0, to = 359) int max){
        return randomGenerator.nextInt(max);
    }
}
