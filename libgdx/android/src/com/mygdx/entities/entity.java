package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
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

import static com.mygdx.utils.entUtils.getMoveDownVec;
import static com.mygdx.utils.entUtils.getMoveLeftVec;
import static com.mygdx.utils.entUtils.getMoveRightVec;
import static com.mygdx.utils.entUtils.getMoveUpVec;
import static com.mygdx.utils.entUtils.getStopVec;
import static com.mygdx.utils.entUtils.stopDownVec;
import static com.mygdx.utils.entUtils.stopLeftVec;
import static com.mygdx.utils.entUtils.stopRightVec;
import static com.mygdx.utils.entUtils.stopUpVec;
import static java.lang.StrictMath.abs;

public abstract class entity {

    private int speed = 80;
    // private static int xspeed = 80;
    //private static int yspeed = 80;

    private Texture image;

    private final static int mWidth = 14;
    private final static int mHeight = 15;
    private static final float mWeight = 40;

    protected float mVelocityY;
    protected static gameMap mMap;
    protected boolean badPath;

    protected Vector3 mPos;

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

    private static TiledMapTileLayer.Cell cellx;
    private static TiledMapTileLayer.Cell celly;

    private TiledMapTileLayer collisionLayer;

    public abstract boolean attack();

    public abstract void render(SpriteBatch batch);

    public entity() {
        this.mVelocityY = 0;
        this.mPos.x = 0;
        this.mPos.y = 0;
        this.amIOnTheGound = true;
        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
        this.validPath = false;
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

        boolean goodposition = false;

        collisionLayer = (TiledMapTileLayer) mMap.getMapLayers().get("Buildings");

        tileW = collisionLayer.getTileWidth();
        tileH = collisionLayer.getTileHeight();
        if (classID == classIdEnum.PZombie) {
            tx = entType.getXpos();
            ty = entType.getYpos();
        } else {
            while (!goodposition) {
                tx = (float) (Math.random() * ((100 - 20) + 20)) + 1;
                ty = (float) (Math.random() * ((100 - 20) + 20)) + 1;

                goodposition = true;
                TiledMapTileLayer.Cell cellx = collisionLayer.getCell((int) ((tx) / tileW), (int) (ty / tileH));

                if(collisionLayer.getCell((int)((tx)/tileW),(int)((ty)/tileH)) != null)
                    goodposition = false;
            }
        }
        
        this.mPos = new Vector3(tx, ty, 0);

        entBody.position.set(mPos.x, mPos.y);
        entBody.type = BodyDef.BodyType.DynamicBody;

        CircleShape cs = new CircleShape();
        cs.setRadius(5);
        fd.density = .01f;
        fd.friction = .5f;
        fd.shape = cs;

        this.body = mMap.getWorld().createBody(entBody);
        //fd.filter.groupIndex = 0;
        this.body.createFixture(fd);
        badPath = false;
    }

    public entity(entityInfo entType, gameMap Map, Rectangle rec) {

        this.mPos = new Vector3(rec.getX() + rec.getWidth() / 2, rec.getY() + rec.getHeight() / 2, 0);
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

        shape.setAsBox(rec.getWidth() / 2, rec.getHeight() / 2);

        entBody.type = BodyDef.BodyType.StaticBody;
        entBody.position.set(mPos.x, mPos.y);
        fd.shape = shape;
        this.body = mMap.getWorld().createBody(entBody);
        this.body.createFixture(fd);
        badPath = false;
    }

    public classIdEnum getClassID() {
        return classID;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(String path) {
        if (image != null)
            image.dispose();
        image = new Texture(path);
    }

    public void update(float dTime) {

        if (moveRight)
            validPath = moveX(speed * dTime);

        if (!validPath)
            body.setLinearVelocity(getStopVec()); // this might crash...

        if (moveLeft)
            validPath = moveX(-speed * dTime);

        if (!validPath)
            body.setLinearVelocity(getStopVec());

        if (moveUp)
            validPath = moveY(speed * dTime);

        if (!validPath)
            body.setLinearVelocity(getStopVec());

        if (moveDown)
            validPath = moveY(-speed * dTime);

        if(!(moveRight || moveLeft || moveUp || moveDown)) //makes sure we dont move if the buttons arnt pressed
            body.setLinearVelocity(getStopVec());

        if (!validPath)
            body.setLinearVelocity(getStopVec());
    }

    protected boolean moveX(float amount) {

        if ((mPos.x + amount) < 0)
            return false;

        if ((mPos.x + amount) > mMap.getMapWidth())
            return false;

        // if we use a steering behavior --- wont this make it over shoot ?
        if (amount > 0) {
            if (body.getLinearVelocity().x <= 55)
                body.applyLinearImpulse(getMoveLeftVec(), body.getWorldCenter(), true);
        } else {
            if (body.getLinearVelocity().x >= -55)
                body.applyLinearImpulse(getMoveRightVec(), body.getWorldCenter(), true);
        }

        return true;
    }

    protected boolean moveY(float amount) {
        //basing coordinates to real world instead of screen coordinate

        if ((mPos.y + amount) > mMap.getMapHeight())
            return false;

        if ((mPos.y + amount) < 0)
            return false;

        if (amount > 0) {
            if (body.getLinearVelocity().y <= 25)
                body.applyLinearImpulse(getMoveUpVec(), body.getWorldCenter(), true);

        } else {
            if (body.getLinearVelocity().y >= -25)
                body.applyLinearImpulse(getMoveDownVec(), body.getWorldCenter(), true);
        }

        return true;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public Vector3 getPos() {
        return mPos;
    }

    public Body getBody() {
        return body;
    }

    ;

    public void setPosX(float x) {
        mPos.x = x;
    }

    public void setPosY(float y) {
        mPos.y = y;
    }

    public float getPosX() {
        return mPos.x;
    }

    public float getPosY() {
        return mPos.y;
    }

    public float getVelocityY() {
        return mVelocityY;
    }


    public Vector3 getmPos() {
        return mPos;
    }

    public gameMap getMap() {
        return mMap;
    }

    public boolean isOnGround() {
        return amIOnTheGound;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public float getEntDistance(entity target) {
        mPos.x = getBody().getPosition().x;
        mPos.y = getBody().getPosition().y;
        float tempx = abs(target.getPosX() - this.mPos.x);
        float tempy = abs(target.getPosY() - this.mPos.y);
        return (float) Math.sqrt(tempx * tempx + tempy * tempy);
    }

}
