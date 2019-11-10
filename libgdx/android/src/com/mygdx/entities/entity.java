package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.world.gameMap;

public abstract class entity  {

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private  int speed = 80;
   // private static int xspeed = 80;
    //private static int yspeed = 80;

    protected float mVelocityY;
    protected gameMap mMap;
    protected boolean badPath;

    protected Vector3 mPos;

    private Vector2 StopVec; // we dont need multiple instances of this so move it from the class later

    public classIdEnum getClassID() {
        return classID;
    }

    protected classIdEnum classID;

    private Body body;

    private boolean amIOnTheGound;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;

    protected boolean validPath;

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
        this.StopVec = new Vector2(0,0);
    }

    public entity(entityInfo entType, gameMap Map) {

        this.mVelocityY = 0;
        this.mMap = Map;
        this.amIOnTheGound = true; // every thing will be on the ground for now
        this.classID = entType.getId();
        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
        this.validPath = false;

        float tileW, tileH, tx = 0, ty = 0;

        //mario tutorial youtube libgdx box2d
        //box2d
        BodyDef entBody = new BodyDef();
        FixtureDef fd = new FixtureDef();

        this.StopVec = new Vector2(0,0);

        boolean goodposition = false;

        collisionLayer = (TiledMapTileLayer) mMap.getMapLayers().get("Buildings");

        tileW = collisionLayer.getTileWidth();
        tileH = collisionLayer.getTileHeight();

        while(!goodposition){
            tx = (float) (Math.random()*((150-40)+40))+1;
            ty = (float) (Math.random()*((150-30)+30))+1;

            goodposition = true;
            TiledMapTileLayer.Cell cellx = collisionLayer.getCell((int)((tx)/tileW),(int)(ty/tileH));
int tscale = 40;
/*
            if(collisionLayer.getCell((int)((tx)/tileW),(int)((ty)/tileH)) != null)
                goodposition = false;
            if(collisionLayer.getCell((int)((tx + tscale)/tileW),(int)((ty + tscale)/tileH)) != null)
                goodposition = false;
            if(collisionLayer.getCell((int)((tx - tscale)/tileW),(int)((ty - tscale)/tileH)) != null)
                goodposition = false;
            if(collisionLayer.getCell((int)((tx - tscale)/tileW),(int)((ty + tscale)/tileH)) != null)
                goodposition = false;
            if(collisionLayer.getCell((int)((tx + tscale)/tileW),(int)((ty - tscale)/tileH)) != null)
                goodposition = false;
            if(collisionLayer.getCell((int)((tx + tscale)/tileW),(int)((ty) /tileH)) != null)
                goodposition = false;
            if(collisionLayer.getCell((int)((tx)/tileW),(int)((ty+tscale)/tileH)) != null)
                goodposition = false;
            if(collisionLayer.getCell((int)((tx - tscale)/tileW),(int)((ty) /tileH)) != null)
                goodposition = false;
            if(collisionLayer.getCell((int)((tx)/tileW),(int)((ty-tscale)/tileH)) != null)
                goodposition = false;
            */

            //find a better way than this
        }

        this.mPos = new Vector3(tx,ty,0);

        entBody.position.set(mPos.x, mPos.y);
        entBody.type = BodyDef.BodyType.DynamicBody;

        CircleShape cs = new CircleShape();
        cs.setRadius(7);
        fd.density = .01f;
        fd.friction = .5f;
        fd.shape = cs;

        this.body = mMap.getWorld().createBody(entBody);
        //fd.filter.groupIndex = 0;
        this.body.createFixture(fd);
        badPath = false;
    }


    public entity(entityInfo entType, gameMap Map, Rectangle rec) {

        this.mPos = new Vector3(rec.getX() + rec.getWidth()/2, rec.getY() + rec.getHeight()/2,0);
        this.mVelocityY = 0;
        this.mMap = Map;
        this.amIOnTheGound = true; // every thing will be on the ground for now

        this.classID = entType.getId();
        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
        this.validPath = false;

        BodyDef entBody = new BodyDef();
        FixtureDef fd = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(rec.getWidth()/2,rec.getHeight()/2);

        entBody.type = BodyDef.BodyType.StaticBody;
        entBody.position.set(mPos.x, mPos.y);
        fd.shape = shape;
        this.body = mMap.getWorld().createBody(entBody);
        this.body.createFixture(fd);
        badPath = false;
    }

    public abstract void render(SpriteBatch batch);
   // public abstract void setImage(String path);

    public void update(float dTime){

        if(moveRight)
            validPath = moveX(speed * dTime);

        if(!validPath)
            body.setLinearVelocity(StopVec);

        if(moveLeft)
            validPath = moveX(-speed * dTime);

        if(!validPath)
            body.setLinearVelocity(StopVec);

        if(moveUp)
            validPath = moveY(speed * dTime);

        if(!validPath)
            body.setLinearVelocity(StopVec);

        if(moveDown)
            validPath = moveY(-speed * dTime);

        if(!validPath)
            body.setLinearVelocity(StopVec);
    }

//i think these controls should be moved to the player class
    protected boolean moveX(float amount){

         if((mPos.x + amount) < 0)
                return false;

        if((mPos.x + amount) > mMap.getMapWidth())
                return false;

        // if we use a steering behavior --- wont this make it over shoot ?
        if(amount > 0) {
            if (body.getLinearVelocity().x <= 55)
                body.applyLinearImpulse(new Vector2(25.8f, 0), body.getWorldCenter(), true);
        }else{
            if (body.getLinearVelocity().x >= -55)
                body.applyLinearImpulse(new Vector2(-25.8f, 0), body.getWorldCenter(), true);
        }

        return true;
    }

    protected boolean moveY(float amount){
        //basing coordinates to real world instead of screen coordinate

        if((mPos.y + amount) > mMap.getMapHeight())
            return false;

        if((mPos.y + amount) < 0)
            return false;

        if(amount > 0) {
            if (body.getLinearVelocity().y <= 25)
                body.applyLinearImpulse(new Vector2(0, 15.8f), body.getWorldCenter(), true);

        }else{
            if (body.getLinearVelocity().y >= -25)
                body.applyLinearImpulse(new Vector2(0, -15.8f), body.getWorldCenter(), true);
        }

       return true;
    }

    public Vector3 getPos() { return mPos; }

    public Body getBody(){ return body; };

    public void setPosX(float x){ mPos.x = x; }
    public void setPosY(float y){ mPos.y = y; }

    public float getPosX(){ return mPos.x; }
    public float getPosY(){ return mPos.y; }
    public float getVelocityY(){ return mVelocityY; }

    public Vector3 getmPos() { return mPos; }

    public gameMap getMap(){ return mMap; }

    public boolean isOnGround(){ return amIOnTheGound; }

    public boolean isMoveLeft() { return moveLeft; }

    public void setMoveLeft(boolean moveLeft) { this.moveLeft = moveLeft; }

    public boolean isMoveRight() { return moveRight; }

    public void setMoveRight(boolean moveRight) { this.moveRight = moveRight; }

    public boolean isMoveUp() { return moveUp; }

    public void setMoveUp(boolean moveUp) { this.moveUp = moveUp; }

    public boolean isMoveDown() { return moveDown; }

    public void setMoveDown(boolean moveDown) { this.moveDown = moveDown; }
}
