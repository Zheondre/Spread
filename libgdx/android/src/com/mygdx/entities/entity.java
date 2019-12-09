package com.mygdx.entities;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.entities.humans.zombie;
import com.mygdx.world.gameMap;

import static com.mygdx.utils.entUtils.getMoveDownVec;
import static com.mygdx.utils.entUtils.getMoveLeftVec;
import static com.mygdx.utils.entUtils.getMoveRightVec;
import static com.mygdx.utils.entUtils.getMoveUpVec;
import static com.mygdx.utils.entUtils.getStopVec;
import static com.mygdx.utils.entUtils.getZombieAttack;
import static java.lang.StrictMath.abs;

public abstract class entity implements Telegraph {

    private int speed = 80;

    private Texture image;
    private Texture imageRight;
    private Texture imageRightWalk;
    private Texture imageUp;
    private Texture imageUpWalk;
    private Texture imageLeft;
    private Texture imageLeftWalk;
    private Texture imageDown;
    private Texture imageDownWalk;

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    private  int mWidth = 13;
    private  int mHeight = 18;

    protected float mVelocityY;
    protected static gameMap mMap;
    protected boolean badPath;

    protected Vector3 mPos;

    protected classIdEnum classID;

    private boolean livingObject;

    private Body body;

    protected Box2dSteering steerEnt;

    private boolean amIOnTheGound;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;

    protected boolean validPath;

    protected int wlkDirection;

    protected int prevDrct;

    protected classIdEnum weapon;

    private int mapXMax;
    private int mapYMax;

    private static TiledMapTileLayer.Cell cellx;
    private static TiledMapTileLayer.Cell celly;

    private TiledMapTileLayer collisionLayer;

    public abstract boolean attack();

    public abstract void render(SpriteBatch batch);

    public abstract boolean handleMessage(Telegram msg);

    public entity() {
        this.mVelocityY = 0;
        this.amIOnTheGound = true;
        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
        this.validPath = false;
    }

    public entity(entityInfo entType, zombie ent, gameMap Map) {
    // bullet constructor
       this.mWidth = 5;
       this.mHeight = 5;
        this.mVelocityY = 0;
        this.mMap = Map;
        this.amIOnTheGound = false; // every thing will be on the ground for now
        this.classID = entType.getId();
        this.livingObject = entType.isLivingObject();
        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
        this.validPath = false;
        this.badPath = false;
    }
    public entity(entityInfo entType, gameMap Map) {

        this.mVelocityY = 0;
        this.mMap = Map;
        this.amIOnTheGound = true; // every thing will be on the ground for now
        this.classID = entType.getId();
        this.livingObject = entType.isLivingObject();

        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
        this.validPath = false;

        float tileW, tileH, tx = 0, ty = 0;

        //mario tutorial youtube libgdx box2d
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
                tx = (float) (Math.random() * ((350 - 50) + 1)) + 50;
                ty = (float) (Math.random() * ((350 - 50) + 1)) + 50;

                goodposition = true;
                TiledMapTileLayer.Cell cellx = collisionLayer.getCell((int) ((tx) / tileW), (int) (ty / tileH));

                if(collisionLayer.getCell((int)((tx)/tileW),(int)((ty)/tileH)) != null)
                    goodposition = false;
            }

            if (classID == classIdEnum.Emt) {
                //Debug
                //tx = 250;
                //ty = 60;
            }
        }

        collisionLayer = null;
        this.mPos = new Vector3(tx, ty, 0);

        entBody.type = BodyDef.BodyType.DynamicBody;

        entBody.position.set(mPos.x, mPos.y);

        CircleShape cs = new CircleShape();
        cs.setRadius(5);

        fd.density = .05f;
        fd.friction = .5f;
        fd.shape = cs;

        this.body = mMap.getWorld().createBody(entBody);
        this.steerEnt = new Box2dSteering(this.body, 5);
        //fd.filter.groupIndex = 0;
        this.body.setActive(true);
        this.body.createFixture(fd).setUserData(this);
        badPath = false;
    }

    public entity(entityInfo entType, gameMap Map, Rectangle rec) {
        // Buildings
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
        //need this for raycast detection.. should we include static objects ...?
        this.body.createFixture(fd).setUserData(this);
        badPath = false;
    }

    public void dispose(){
        image = null;
        imageRight.dispose();
        imageRightWalk.dispose();
        imageUp.dispose();
        imageUpWalk.dispose();
        imageLeft.dispose();
        imageLeftWalk.dispose();
        imageDown.dispose();
        imageDownWalk.dispose();
        mPos = null;
        // becarefull bellow if some one is stilling pointing to this object it wont be freed
        steerEnt = null;
        body.destroyFixture(body.getFixtureList().first());
        body = null;  // check on this

    }

    public Box2dSteering getSteerEnt() {
        return steerEnt;
    }

    public classIdEnum getClassID() {
        return classID;
    }

    public void setClassID(classIdEnum classID) {
        this.classID = classID;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Texture getImage() {
        return image;
    }

    public void makeImageNull(){
        image = null;
    }

    public void setImage(String path) {
        //anamation #1
        if (image != null)
            image.dispose();
        image = new Texture(path);
        imageRight = image;

    }

    public void setImage(Texture txt){
        if(txt != null)
            image = txt;
    }

    public void setImageRight(String path) {
        //anamation #1
        if (imageRight != null)
            imageRight.dispose();
        imageRight = new Texture(path);


    }
    public void setImageRightWalk(String path) {
        //anamation #2
        if (imageRightWalk != null)
            imageRightWalk.dispose();
        imageRightWalk = new Texture(path);
    }

    public void setImageUp(String path) {
        //anamation #2
        if (imageUp != null)
            imageUp.dispose();
        imageUp = new Texture(path);
    }

    public void setImageUpWalk(String path) {
        //anamation #2
        if (imageUpWalk != null)
            imageUpWalk.dispose();
        imageUpWalk = new Texture(path);
    }

    public void setImageLeft(String path) {
        //anamation #2
        if (imageLeft != null)
            imageLeft.dispose();
        imageLeft = new Texture(path);
    }

    public void setImageLeftWalk(String path) {
        //anamation #2
        if (imageLeftWalk != null)
            imageLeftWalk.dispose();
        imageLeftWalk = new Texture(path);
    }

    public void setImageDown(String path) {
        //anamation #2
        if (imageDown != null)
            imageDown.dispose();
        imageDown = new Texture(path);
    }

    public void setImageDownWalk(String path) {
        //anamation #2
        if (imageDownWalk != null)
            imageDownWalk.dispose();
        imageDownWalk = new Texture(path);
    }

    public void changeImage(boolean chngeim, int state){
        if((imageRight != null)) {
            if (state == 1) {
                if (chngeim)
                    image = imageRight;
                else
                    image = imageRightWalk;
            }
            if (state == 2) {
                if (chngeim)
                    image = imageUp;
                else
                    image = imageUpWalk;
            }
            if (state == 3) {
                if (chngeim)
                    image = imageLeft;
                else
                    image = imageLeftWalk;
            }
            if (state == 4) {
                if (chngeim)
                    image = imageDown;
                else
                    image = imageDownWalk;
            }
        }
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

    public void stopMoving(){
        moveRight = false;
        moveLeft = false;
        moveDown = false;
        moveUp = false;
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

    public void setBody(Body body) { this.body = body; }
    public Body getBody() {
        return body;
    }

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
        float tempx = abs((target.getPosX() + target.getWidth()) -(this.mPos.x + this.getWidth()/2));
        float tempy = abs((target.getPosY() + target.getHeight()) - (this.mPos.y + this.getHeight()/2));
        return (float) Math.sqrt(tempx * tempx + tempy * tempy);
    }

    public float getEntDistance(zombie shooter, entity target) {

        float tempx = abs((target.getPosX() + (float)target.getWidth()/2)
                - (shooter.getPosX() + (float)shooter.getWidth()/2));

        float tempy = abs((target.getPosY() + (float)target.getHeight()/2)
                - (shooter.getPosY() + (float)shooter.getHeight()/2));

        return (float) Math.sqrt(tempx * tempx + tempy * tempy);
    }
    public boolean isLivingObject() {
        return livingObject;
    }

    public int getWlkDirection() {
        return wlkDirection;
    }

    public int getPrevDrct() {
        return prevDrct;
    }

    public void setPrevDrct(int prevDrct) {
        this.prevDrct = prevDrct;
    }

}
