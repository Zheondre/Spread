package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.world.gameMap;

public abstract class entity  {

    private static final int speed = 80;

    protected float mVelocityY;
    protected gameMap mMap;

    public Vector3 getmPos() {
        return mPos;
    }

    protected Vector3 mPos;

    protected classIdEnum classID;

    private boolean amIOnTheGound;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;

    private boolean validPath;

    private int mapXMax;
    private int mapYMax;

    private TiledMapTileLayer.Cell cellx;
    private TiledMapTileLayer.Cell celly;

    private TiledMapTileLayer collisionLayer;

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
        this.validPath= false;
    }

    public entity(entityInfo entType, gameMap Map) {
        this.mPos = new Vector3(entType.getXpos(), entType.getYpos(),0);
        this.mVelocityY = 0;
        this.mMap = Map;
        this.amIOnTheGound = true; // every thing will be on the ground for now
        this.classID = entType.getId();
        this.moveLeft= false;
        this.moveRight= false;
        this.moveUp= false;
        this.moveDown= false;
        this.validPath= false;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void setImage(String path);

    public void update(float dTime){

        if(moveRight)
            validPath = moveX(speed * dTime);

        if(moveLeft)
            validPath = moveX(-speed * dTime);

        if(moveUp)
            validPath = moveY(speed * dTime);

        if(moveDown)
            validPath = moveY(-speed * dTime);
    }

    public Vector3 getPos() {
        return mPos;
    }

    protected boolean moveX(float amount){
        // Every moving entity will check for collisions

       // mMap.getMapLayers().get("Buildings").getProperties().

        if((mPos.x + amount) < 0)
                return false;

        if((mPos.x + amount) > mMap.getMapWidth())
                return false;

        //we have to either loop through all the layers that contain blocked objects or merge them into one.
        collisionLayer = (TiledMapTileLayer) mMap.getMapLayers().get("Buildings");

        TiledMapTileLayer.Cell cellx = collisionLayer.getCell((int)(mPos.x +amount),(int)mPos.y);

        if(collisionLayer.getCell((int)(mPos.x +amount),(int)mPos.y) != null) {
         int xy = 0;
         int yx =0;

            return false;
        }
        /*
        if(collisionLayer.getCell((int)(mPos.x +amount),
               (int)mPos.y).getTile().getProperties().containsKey("blocked"))
           return false;
*/
        mPos.x = mPos.x + amount;
        return true;

    }

    protected boolean moveY(float amount){
//basing coordinates to real world instead of screen coordinates

        if((mPos.y + amount) > mMap.getMapHeight())
            return false;

        if((mPos.y + amount) < 0)
            return false;

        collisionLayer = (TiledMapTileLayer) mMap.getMapLayers().get("Buildings");

      //int tiledId =  collisionLayer.getCell(1,1).getTile().getId();
    TiledMapTileLayer.Cell celly = collisionLayer.getCell((int)mPos.x,(int)(mPos.y+amount));

    if(collisionLayer.getCell((int)mPos.x,(int)(mPos.y+amount)) != null){
            int xy = 0;
            int yx =0;

            return false;
        }
/*
        if(collisionLayer.getCell((int)mPos.x,
                (int)(mPos.y+amount)).getTile().getProperties().containsKey("blocked"))
            return false; */

       mPos.y = mPos.y + amount;
       return true;
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
