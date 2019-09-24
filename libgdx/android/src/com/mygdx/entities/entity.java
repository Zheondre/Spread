package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.gameMap;

import org.w3c.dom.Entity;

public abstract class entity {

    protected float mVelocityY;
    protected gameMap mMap;
    protected Vector2 mPos;
    protected boolean amIOnTheGound = true ;

    protected int classID;

    public enum whoAreYou{
        Zombie,
        Person,
        Security,
        Cop,
        Army,
        Emt,
        Medic,
        Hazmat,
        Master,
        Debug,
    }

    public entity() {
        //mMap = NULL;
        mVelocityY = 0;
        mPos.x =0;
        mPos.y =0;
        this.amIOnTheGound = true;
    }

    public entity(float x, float y, int classID, gameMap Map) {
        this.mPos = new Vector2(x, y);
        this.mVelocityY = 0;
        this.mMap = Map;
        this.amIOnTheGound = true; // every thing will be on the ground for now
        this.classID = classID;
    }

    public abstract void render(SpriteBatch batch);

    public Vector2 getPos() {
        return mPos;
    }


    protected void moveX(float amount){// will check for collisions for every entity
    float newX = mPos.x + amount;
    //if(!thereIsNotACollision && !WeArntOutSideTheMap)
      //  this.mPos.x = newX;
    }

    protected void moveY(float amount){// will check for collisions for every entity
        float newY = mPos.y + amount;
        //if(!thereIsNotACollision && !WeArntOutSideTheMap)
          //  this.mPos.y= newY;
    }

    public void setPosX(float x){ mPos.x = x; }
    public void setPosY(float y){ mPos.y = y; }

    public float getPosX(){ return mPos.x; }
    public float getPosY(){ return mPos.y; }
    public float getVelocityY() { return mVelocityY; }

    public gameMap getMap() { return mMap; }

    public boolean isOnGround() { return amIOnTheGound; }

    public void update(float dTime) {
       // if()

    }
}
