package com.mygdx.entities;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.world.gameMap;


//public interface movements {
   //  final Vector2 StopVec = new Vector2(0, 0);
//}
public abstract class entity  {

    private static final int speed = 80;

    protected float mVelocityY;
    protected gameMap mMap;
    protected boolean badPath;

    protected Vector3 mPos;

    private Vector2 StopVec; // we dont need multiple instances of this so move it from the class later

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

        //mario tutorial youtube libgdx box2d
        //box2d
        BodyDef entBody;
        entBody = new BodyDef();//why didnt they make different constructors for this lol...
        entBody.position.set(mPos.x,mPos.y);
        //the ent might not be a person so make this dynamic later
        entBody.type = BodyDef.BodyType.DynamicBody;
        this.body = mMap.getWorld().createBody(entBody);

        FixtureDef fd = new FixtureDef();
        CircleShape cs = new CircleShape();
        cs.setRadius(7);
        fd.density = .01f;
        fd.shape = cs;
        //fd.filter.groupIndex = 0;
        this.body.createFixture(fd);
        this.StopVec = new Vector2(0,0);
        badPath = false;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void setImage(String path);

    public abstract Arrive<Vector2> getArriveSB();

    public abstract void setArriveSB(Arrive<Vector2> arriveSB);
    public abstract void setArriveSB(Box2dSteering prey);
    public abstract void setArrivePrey(Box2dSteering prey);

    public abstract void setPursueSB(entity prey);

    public abstract Pursue<Vector2> getPursueSB();

    public abstract Box2dSteering getSteerEnt();

    public void update(float dTime){

        if(moveRight)
            validPath = moveX(speed * dTime);

        if(!validPath) {
            body.setLinearVelocity(StopVec);

        }
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

        mPos.x = body.getPosition().x - 7;
        mPos.y = body.getPosition().y - 7.5f;

    }

    public Vector3 getPos() { return mPos; }

//i think these controls should be moved to the player class
    protected boolean moveX(float amount){
        // Every moving entity will check for collisions

        float tileW, tileH;
         if((mPos.x + amount) < 0)
                return false;

        if((mPos.x + amount) > mMap.getMapWidth())
                return false;

        //we have to either loop through all the layers that contain blocked objects or merge them into one.
        collisionLayer = (TiledMapTileLayer) mMap.getMapLayers().get("Buildings");

        tileW = collisionLayer.getTileWidth();
        tileH = collisionLayer.getTileHeight();

        TiledMapTileLayer.Cell cellx = collisionLayer.getCell((int)((mPos.x +amount)/tileW),(int)(mPos.y/tileH));

        if(collisionLayer.getCell((int)((mPos.x +amount)/tileW),(int)(mPos.y/tileH)) != null)
            return false;

        //hard coding picture width, will need to change later
        if(collisionLayer.getCell((int)((mPos.x +amount +14)/tileW),(int)(mPos.y/tileH)) != null)
            return false;

        if(amount > 0) {
            if (body.getLinearVelocity().x <= 60)
                body.applyLinearImpulse(new Vector2(25.8f, 0), body.getWorldCenter(), true);
        }else{
            if (body.getLinearVelocity().x >= -60)
                body.applyLinearImpulse(new Vector2(-25.8f, 0), body.getWorldCenter(), true);
        }

       // mPos.x = mPos.x + amount;
        return true;
    }

    protected boolean moveY(float amount){
//basing coordinates to real world instead of screen coordinates
        float tileW, tileH;
        if((mPos.y + amount) > mMap.getMapHeight())
            return false;

        if((mPos.y + amount) < 0)
            return false;

        collisionLayer = (TiledMapTileLayer) mMap.getMapLayers().get("Buildings");

        tileW = collisionLayer.getTileWidth();
        tileH = collisionLayer.getTileHeight();

        TiledMapTileLayer.Cell celly = collisionLayer.getCell((int)mPos.x,(int)(mPos.y+amount));

        if(collisionLayer.getCell((int)(mPos.x/tileW),(int)((mPos.y+amount)/tileH)) != null)
            return false;

        if(amount > 0) {
            if (body.getLinearVelocity().y <= 30)
                body.applyLinearImpulse(new Vector2(0, 15.8f), body.getWorldCenter(), true);

        }else{
            if (body.getLinearVelocity().y >= -30)
                body.applyLinearImpulse(new Vector2(0, -15.8f), body.getWorldCenter(), true);
        }

      // mPos.y = mPos.y + amount;
       return true;
    }

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
