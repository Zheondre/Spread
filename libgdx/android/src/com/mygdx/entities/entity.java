package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.gameMap;

public abstract class entity {

    protected float mVelocityY;
    protected gameMap mMap;
    protected Vector2 mPos;
    private boolean amIOnTheGound = true ;
    protected classIdEnum classID;

    public entity() {
        //mMap = NULL;
        this.mVelocityY = 0;
        this.mPos.x =0;
        this.mPos.y =0;
        this.amIOnTheGound = true;
    }

    public entity(entityInfo entType, gameMap Map) {
        this.mPos = new Vector2(entType.getXpos(), entType.getYpos());
        this.mVelocityY = 0;
        this.mMap = Map;
        this.amIOnTheGound = true; // every thing will be on the ground for now
        this.classID = entType.getId();
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update(float dTime);

    public Vector2 getPos() {
        return mPos;
    }

    protected void moveX(float amount){// will check for collisions for every entity

        mPos.x = mPos.x + amount;

    //if(!thereIsNotACollision && !WeArntOutSideTheMap)
      //  this.mPos.x = newX;
    }

    protected void moveY(float amount){// will check for collisions for every entity

        mPos.y = mPos.y + amount;
        //if(!thereIsNotACollision && !WeArntOutSideTheMap)
          //  this.mPos.y= newY;
    }

    public void setPosX(float x){ mPos.x = x; }
    public void setPosY(float y){ mPos.y = y; }

    public float getPosX(){ return mPos.x; }
    public float getPosY(){ return mPos.y; }
    public float getVelocityY(){ return mVelocityY; }

    public gameMap getMap(){ return mMap; }

    public boolean isOnGround(){ return amIOnTheGound; }

}
