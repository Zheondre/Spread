package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.gameMap;

public abstract class entity  {

    private static final int speed = 80;

    protected float mVelocityY;
    protected gameMap mMap;
    protected Vector2 mPos;

    protected classIdEnum classID;

    private boolean amIOnTheGound;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;

    public entity() {
        //mMap = NULL;
        this.mVelocityY = 0;
        this.mPos.x =0;
        this.mPos.y =0;
        this.amIOnTheGound = true;
        this.moveLeft= false;
        this.moveRight= false;
        this.moveUp= false;
        this.moveDown= false;
    }

    public entity(entityInfo entType, gameMap Map) {
        this.mPos = new Vector2(entType.getXpos(), entType.getYpos());
        this.mVelocityY = 0;
        this.mMap = Map;
        this.amIOnTheGound = true; // every thing will be on the ground for now
        this.classID = entType.getId();
        this.moveLeft= false;
        this.moveRight= false;
        this.moveUp= false;
        this.moveDown= false;
    }

    public abstract void render(SpriteBatch batch);

    public void update(float dTime){

        if(moveRight)
            moveX(speed * dTime);

        if(moveLeft)
            moveX(-speed * dTime);

        if(moveUp)
            moveY(-speed * dTime);

        if(moveDown)
            moveY(speed * dTime);
    }

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

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }
}
