package com.mygdx.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public final class entUtils {

    static final float yforce = 35.8f;
    static final float xforce = 25.8f;
 //15.8f move up Move Y
    //-15.6f move down MoveY
    static final float yStoppingforce = 40f;
    static final float xStoppingforce = 40f;

    private static final Vector2 StopVec = new Vector2(0,0);
    private static final Vector2 moveRightVec = new Vector2(-xforce, 0);
    private static final Vector2 moveLeftVec = new Vector2(xforce, 0);
    private static final Vector2 moveUpVec = new Vector2(0,yforce);
    private static final Vector2 moveDownVec = new Vector2(0,-yforce);

    private static final Vector2 stopRightVec = new Vector2(-xforce, 0);
    private static final Vector2 stopLeftVec = new Vector2(xforce, 0);
    private static final Vector2 stopUpVec = new Vector2(0,yforce);
    private static final Vector2 stopDownVec = new Vector2(0,-yforce);

    private static final Texture zombieAttack = new  Texture("zombieAttack.png");
    //private static final Texture Bullet = new  Texture("blank.png"); // this was crashing
    private static final Texture Bullet = null;


    entUtils(){
    }


    public static Texture getBullet() {
        return Bullet;
    }

    public static void dispose(){
        //dispose all vecs

        zombieAttack.dispose();
        Bullet.dispose();
    }

   public static Texture getZombieAttack() { return zombieAttack; }

   public static Vector2 getStopVec() { return StopVec; }

    public static Vector2 getMoveLeftVec() {
        return moveLeftVec;
    }

    public static Vector2 getMoveRightVec() {
        return moveRightVec;
    }

    public static Vector2 getMoveUpVec() { return moveUpVec; }

    public static Vector2 getMoveDownVec() {
        return moveDownVec;
    }

    public static Vector2 stopLeftVec() {
        return stopLeftVec;
    }

    public static Vector2 stopRightVec() {
        return stopRightVec;
    }

    public static Vector2 stopUpVec() { return stopUpVec; }

    public static Vector2 stopDownVec() {
        return stopDownVec;
    }
}
