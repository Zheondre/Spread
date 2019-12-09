package com.mygdx.utils;

import android.graphics.Color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public final class SteeringUtils {
    public final static ShapeRenderer debugRenderer = new ShapeRenderer();

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

    ///// https://stackoverflow.com/questions/21835062/libgdx-draw-line //////
    public static void DrawDebugLine(Vector2 start, Vector2 end, int lineWidth, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(3);
    }

    public static void DrawDebugLine(Vector2 start, Vector2 end, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(5);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(com.badlogic.gdx.graphics.Color.CYAN);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(5);
    }

}
