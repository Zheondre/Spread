package com.mygdx.utils;

import com.badlogic.gdx.math.Vector2;

public final class SteeringUtils {
    public static float vect2Angle(Vector2 vec){
        return (float)Math.atan2(-vec.x,vec.y);
    }
    public static Vector2 angle2vec(Vector2 vec,float angle){
        vec.x = -(float)Math.sin(angle);
        vec.y = (float)Math.cos(angle);
        return vec;
    }
    public static float pixelsToMeters (int pixels) {
        return (float)pixels * 0.02f;
    }
}
