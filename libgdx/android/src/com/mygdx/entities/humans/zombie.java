package com.mygdx.entities.humans;
import android.os.SystemClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.mygdx.entities.BehaviorEnum;
import com.mygdx.entities.Box2dSteering;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.objects.bullet;
import com.mygdx.utils.SteeringUtils;
import com.mygdx.utils.viewQueryCallBack;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

import java.util.ArrayList;

import static com.mygdx.entities.BehaviorEnum.TEST_DONT_MOVE;
import static com.mygdx.entities.BehaviorEnum.WALK_RANDOMLY;
import static com.mygdx.utils.entUtils.getZombieAttack;
import static com.mygdx.utils.entUtils.stopDownVec;
import static com.mygdx.utils.entUtils.stopLeftVec;
import static com.mygdx.utils.entUtils.stopRightVec;
import static com.mygdx.utils.entUtils.stopUpVec;
import static java.lang.StrictMath.abs;

public class zombie extends entity {

    private viewQueryCallBack viewCB;

    protected int peopleConverted;
    protected int infections;

    protected float attackPt;
    protected float armorPts;
    protected float shootingRadius;
    protected float health;
    protected float mInfctTime;

    protected classIdEnum weapon;

    protected BehaviorEnum mAlerted;

    private static final int biteTimeSetting = 5;

    private float reviveTime = 20;
    private float bitetime = biteTimeSetting;

    public void setWlkTime(float wlkTime) {
        this.wlkTime = wlkTime;
    }

    private float wlkTime;
    private float preyDistance;

    private boolean isAlive;
    private boolean mIsCpu;
    private boolean doISeeANoneZombie;
    private boolean areWeBiting = false;

    private entity prey;

    protected ArrayList<bullet> bullets;

    protected Pursue<Vector2>  pursueSB;
    protected Wander<Vector2>  wanderSB;
    protected Arrive<Vector2>  arriveSB;

    public PrioritySteering<Vector2> combinedSB; // sb chosen based on the returned accelleration
    public BlendedSteering<Vector2> blendedSB; //sb chosen based on the set weight

    public Wander<Vector2> getWanderSB() {
        return wanderSB;
    }
    public Arrive<Vector2> getArriveSB() {
        return arriveSB;
    }
    public Pursue<Vector2> getPursueSB() {
        return pursueSB;
    }

    public void setWonderSB(Box2dSteering entSb) {
        if((entSb != null)) {
           // wanderSB = new Wander<Vector2>(entSb).;
            steerEnt.setBehavior(wanderSB);//might put this as its own function
        }
    }

    public void setArriveSB(Arrive<Vector2> arriveSB) {
        this.arriveSB = arriveSB;
    }

    public void setArriveSB(Box2dSteering prey) {
        if((prey != null)) {
            arriveSB = new Arrive<Vector2>(steerEnt, prey)
                    .setTimeToTarget(.1f)
                    .setArrivalTolerance(50f)// arrival tolerance doesnt seem to work
                    .setDecelerationRadius(1000f);
            steerEnt.setBehavior(arriveSB);//might put this as its own function
        }
    }

    public void setArrivePrey(Box2dSteering prey) { // redundant ?
        if(!(prey == null) && !(arriveSB == null))
            arriveSB.setTarget(prey);
    }

    public void setPursueSB(Pursue<Vector2> pursueSB) {
        if((pursueSB != null))
          this.pursueSB = pursueSB;
    }

    public void setPursueSB(person prey) {
        if((prey != null)) {
            if(this.pursueSB == null) {
                pursueSB = new Pursue<Vector2>(steerEnt, prey.getSteerEnt(), .5f);
                combinedSB.add(pursueSB);
                //steerEnt.setBehavior(pursueSB);//might put this as its own function
            } else {
                this.pursueSB.setTarget(prey.getSteerEnt());
                this.pursueSB.setEnabled(true);
            }
            this.prey = prey;
        }
    }

    public void setPursuePrey(Box2dSteering prey){
        if(pursueSB != null)
            pursueSB.setTarget(prey);
    }

    public zombie(entityInfo entType, gameMap map) {
        super(entType, map);

        this.isAlive = true;
        this.classID = entType.getId();
        this.armorPts = entType.getArmor();

        if(classID == classIdEnum.Zombie || classID == classIdEnum.PZombie) {
            setImage(new Texture("zombie.png"));
            setImageRight("zombie.png");
            setImageRightWalk("zombie2.png");
            setImageUp("zombieUp.png");
            setImageUpWalk("zombieUp2.png");
            setImageLeft("zombieLeft.png");
            setImageLeftWalk("zombieLeft2.png");
            setImageDown("zombieDown.png");
            setImageDownWalk("zombieDown2.png");
            mInfctTime = 0;
        }
        this.infections = 0;
        this.attackPt = entType.getAttackPt();//
        this.health = entType.getHealth();
        this.weapon = entType.getWeapon();
        this.mIsCpu = entType.isCpu();

        this.doISeeANoneZombie = false;

/*
        this.wanderSB = new Wander<Vector2>(steerEnt) //
                .setFaceEnabled(true) // We want to use Face internally (independent facing is on)
                .setAlignTolerance(1f) // Used by Face
                .setDecelerationRadius(10f) // Used by Face
                .setTimeToTarget(.5f) // Used by Face
                .setWanderOffset(50f) //
                .setWanderOrientation(400f) //
                .setWanderRadius(300f) //
                .setWanderRate(MathUtils.PI2 * 8);
*/
        //add raycasting object avoidence or object avoidence sb as default ?

        this.combinedSB = new PrioritySteering<Vector2>(steerEnt, .001f);
        steerEnt.setBehavior(combinedSB);
        if(this.mIsCpu){
            //put player towards the beginning of map if its not a new game
            //if its a new game dont draw the spite yet we got to set a bomb before hand
           // steerEnt.setBehavior(wanderSB);
        } else {
           // viewCB = new viewQueryCallBack();
        }
    }

    protected void walkRandomly(float dt){
        //if the next move in our current direction is invalid
        // change direction if there is an invalid path
        int debugbreakpoint;
        if(this.classID == classIdEnum.ConvertedPer)
            debugbreakpoint = 0;

        if(!this.validPath)
            wlkDirection = (int)(Math.random()*((5-1)+1))+1;

        if(wlkTime < 0) {
            wlkTime =  (float)(Math.random()*((4-1)+1))+1;
            wlkDirection = (int)(Math.random()*((5-1)+1))+1;
        }else {
            wlkTime -= dt;
            this.setMoveUp(false);
            this.setMoveRight(false);
            this.setMoveDown(false);
            this.setMoveLeft(false);
            switch (wlkDirection) {
                case 1:
                    this.setMoveUp(true);
                    break;
                case 2:
                    this.setMoveRight(true);
                    break;
                case 3:
                    this.setMoveDown(true);
                    break;
                case 4:
                    this.setMoveLeft(true);
                    break;
                default:
            }
        }
    }

    public boolean attack(){
        // TODO make sure the person we are attacking is in the direction we are looking
        person per = findSomeOneToChase();

       if(per != null)
           if(preyDistance < 20) {
               biteNonZombie(per);
               return true;
           }

        return false;
    }

    public boolean biteNonZombie(person victum){

        if(victum == null)
            return false;
        if(!victum.isInfected())
            victum.setInfected(true, this);
        else
            if(victum.getPrey() != this) {
                // change or add to the behaviors
                victum.changeEvadeTarget(this);
            }
        areWeBiting = true;
        victum.decreaseInfectTime(.10f);
        victum.decreaseHlth(.05f);

        setImage(getZombieAttack()); // might have to change this
        return true;
    }

    public boolean handleMessage(Telegram msg) {
        return true;
    }

   public void dispose(){
       pursueSB = null;
       arriveSB = null;
       combinedSB = null;
        super.dispose();
    }

        //  @Override
    public void update(float dTime){

        if(health < 0) {
            // DEATH LOGIC
            isAlive = false;
            reviveTime -= .05;
            stopMoving();
            mPos.x = this.getBody().getPosition().x - 7;
            mPos.y = this.getBody().getPosition().y - 7.5f;
            if(reviveTime < 0) {// crashed
                ((tileGameMap)mMap).getReadyForDeletion().add(this);
            }
            super.update(dTime);
            return;
        }
        if(this.mIsCpu) {
            switch(this.classID) {
                case Person:
                case Security:
                case Cop:
                case Emt:
                    if(mAlerted == WALK_RANDOMLY)
                        super.update(dTime);
                    else if(mAlerted == TEST_DONT_MOVE) {
                        stopMoving();
                        super.update(dTime);
                    }
                     else {
                        steerEnt.update(dTime);
                    }
                    break;

                case ConvertedPer: // debug
                    int xxx = 0; // debug
                case Zombie:

                    if(this.getPrey() == null)
                        doISeeANoneZombie = false;
                    else if(((person)this.getPrey()).areYouAZombie())
                        doISeeANoneZombie = false;

                    //loop through noinfected victums
                    //also need to see if they are in out view but for now we will base this off of ditance
                    //if close enough chase, if too close stop and attack
                    if(this.isMoveRight()) {

                        if (((SystemClock.elapsedRealtime() * 1000) % 2) == 1)
                            changeImage((((SystemClock.elapsedRealtime() * 1000) % 2) == 1), 1);
                    }
                    if(this.isMoveUp()) {
                        if (((SystemClock.elapsedRealtime() * 1000) % 2) == 1)
                            changeImage((((SystemClock.elapsedRealtime() * 1000) % 2) == 1), 2);
                    }
                    if(this.isMoveLeft()) {
                        if (((SystemClock.elapsedRealtime() * 1000) % 2) == 1)
                            changeImage((((SystemClock.elapsedRealtime() * 1000) % 2) == 1), 3);
                    }
                    if(this.isMoveDown()) {
                        if (((SystemClock.elapsedRealtime() * 1000) % 2) == 1)
                            changeImage((((SystemClock.elapsedRealtime() * 1000) % 2) == 1), 4);
                    }

                    if (doISeeANoneZombie) {

                        float tempD = getEntDistance();
                        if(tempD < 13 && tempD > -1){
                            // what if two zombies are pointing at the same converted person/////
                            // to do, just send a message to all zombie listeners hunting for prey to stop when prey is a zombie

                            if(((person)this.getPrey()).areYouAZombie()){
                                // if we multi thread use a semiphore // i dont think this should be handled here
                                this.setPrey(null);
                                doISeeANoneZombie = false;
                                pursueSB.setEnabled(false);
                                //increment score count
                            }  else {
                                stopMovingEnt();
                                if (bitetime == biteTimeSetting)
                                    biteNonZombie((person) this.getPrey());
                                else {
                                    bitetime -= .30;
                                    if (bitetime < 0)
                                        bitetime = biteTimeSetting;
                                }
                            }
                        } else {

                            if(this.getPrey() != null)
                                if(steerEnt != null)
                                    steerEnt.update(dTime);
                        }
                        //goAfterNonZombie if we are close enough attack
                        // or follow the leader if instructed on oding so
                    } else {
                        person shortEnt = findSomeOneToChase();

                        if(shortEnt!= null) {
                            doISeeANoneZombie = true;
                            if(getPursueSB()== null)
                                setPursueSB(shortEnt);
                            else
                                setPursuePrey(shortEnt.getSteerEnt());
                        } else {
                            walkRandomly(dTime);
                            super.update(dTime);
                        }
                    }
                    break;

                    default:
                        super.update(dTime);
                }
        } else {

            ///// temp /////
            float tempX = this.getPosX();
            float tempY = this.getPosY();
            float radius = 5;
            //mAlerted = TEST_DONT_MOVE;
            //Debug
           // DrawDebugLine(new Vector2(tempX - radius*2,tempY - radius*5), new Vector2(tempX - radius*2, tempY- 3), ((tileGameMap)mMap).getPlayerOne().getPlayCam().combined); //left line
            //DrawDebugLine(new Vector2(tempX + radius*4,tempY - radius*5), new Vector2(tempX + radius*4, tempY-3), ((tileGameMap)mMap).getPlayerOne().getPlayCam().combined);

            //player has control
            super.update(dTime);
        }
        //Update Picture position to box2d position
        mPos.x = this.getBody().getPosition().x - 7;
        mPos.y = this.getBody().getPosition().y - 7.5f;
    }

   // @Override
    public void render(SpriteBatch batch){
        Texture image = getImage();
        if((image != null) && (mMap.getPlayerHealth() != null)) {

            batch.draw(image, mPos.x, mPos.y, getWidth(), getHeight());

            //libgdx tutorial health bar + exposions

            //TODO CLEAN UP CODE BELLOW
            if((getClassID() == classIdEnum.PZombie) || (getClassID() == classIdEnum.ConvertedPer) || (getClassID() == classIdEnum.Zombie) ) {
                //TODO move to the right of the screen
                //if there is armor show one bar for that then show grey once armor is at 0;

                if(areWeBiting) {
                    changeImage(false, 1);
                    areWeBiting = false;
                }

                // this puts the bar under the moving buttons
                //batch.draw(mMap.getPlayerHealth(),  ((tileGameMap)mMap).getPlayerOne().getCamXPos(), ((tileGameMap)mMap).getPlayerOne().getCamYPos(), ((Gdx.graphics.getWidth() - 1300) / 3) * getHealth(), 6);
            } else if (getClassID() != classIdEnum.ConvertedPer) {

                if(getHealth() > .95f)
                    batch.setColor(Color.CLEAR);
                else if(getHealth() > .8f)
                    batch.setColor(Color.WHITE);
                else if(getHealth() > .3f)
                    batch.setColor(Color.YELLOW);
                else
                    batch.setColor(Color.PURPLE);
                if(mInfctTime > 0)
                    batch.draw(mMap.getPlayerHealth(),  getPosX() + 8, getPosY() +25, 22* mInfctTime, 3);

            }
            if(getHealth() > .95f)
                batch.setColor(Color.CLEAR);
            else if(getHealth() > .8f)
                batch.setColor(Color.GREEN);
            else if(getHealth() > .3f)
                batch.setColor(Color.ORANGE);
            else
                batch.setColor(Color.RED);

            if(getHealth() > 0)
                batch.draw(mMap.getPlayerHealth(),  getPosX() + 8, getPosY() +20, 22* getHealth(), 3);

            //TODO make sure armor points decrease first before health
            if(armorPts > 0 ) {
                batch.setColor(Color.DARK_GRAY);
                batch.draw(mMap.getPlayerHealth(), getPosX() + 8, getPosY() + 20, 22 *armorPts, 3);
            }
            batch.setColor(Color.WHITE);

            batch.draw(image, mPos.x, mPos.y, getWidth(), getHeight()); // had to add twice cause one of the chars wasnt showing the health bar
        }
    }

    public float getEntDistance() {
        mPos.x = super.getBody().getPosition().x;
        mPos.y = super.getBody().getPosition().y;
        if(this.prey == null) {
            doISeeANoneZombie = false;
            return -1;
        }
        float tempx = abs(this.prey.getPosX() - this.mPos.x);
        float tempy = abs(this.prey.getPosY() - this.mPos.y);
        return (float)Math.sqrt(tempx * tempx + tempy * tempy);
    }

    public person findSomeOneToChase(){
        person shortEnt = null;
        float tth = 80;
        float tempd2;

        for(person nonzombie : mMap.getPeople()){
            if(!nonzombie.areYouAZombie()) {
                tempd2 = getEntDistance(nonzombie);
                if (tempd2 < tth) {
                    tth = tempd2;
                    shortEnt = nonzombie;
                }
            }
        }

        if(shortEnt != null)
            preyDistance = tth;

        return shortEnt;
    }

    public float getPreyDistance() {
        return preyDistance;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public float getHealth() {
        return health;
    }

    public boolean iscpu() { return mIsCpu; }

    public void setCpuStatus(boolean IsCpu) {
        this.doISeeANoneZombie = false;
        this.mIsCpu = IsCpu;
        this.stopMoving();
    }

    public int getInfections() { return infections; }

    public void setInfections(int infections) { this.infections = infections; }

    public float getAttackPt() { return attackPt; }

    public void setAttackPt(float attackPt) { this.attackPt = attackPt; }

    public void setHealth(float health) { this.health = health; }

    public void setWeapon(classIdEnum weapon) { this.weapon = weapon; }

    public void decreaseHlth(float amount){ this.health -= amount; }

    public void increaseHlth(float amount) {this.health += amount;}

    public void decreaseInfcTime(float amount){this.mInfctTime -= amount;}

    public void increaseInfcTime(float amount){this.mInfctTime += amount;}

    public void setmInfctTime(float mInfctTime) {
        this.mInfctTime = mInfctTime;
    }

    public float getmInfctTime() {
        return mInfctTime;
    }

    public void setmAlerted(BehaviorEnum mAlerted) {
        this.mAlerted = mAlerted;
    }

    public float getArmorPts() {
        return armorPts;
    }
    public void dcrseArmor(float pts){
        this.armorPts -= pts;
    }
    public void setPrey(entity prey){ this.prey = prey; }
    public entity getPrey(){ return this.prey; }

   public void stopMovingEnt(){

    if (steerEnt.getLinearVelocity().x > 0)
        steerEnt.body.applyLinearImpulse(stopLeftVec(), steerEnt.body.getWorldCenter(), true);

    if (steerEnt.getLinearVelocity().x < 0)
        steerEnt.body.applyLinearImpulse(stopRightVec(), steerEnt.body.getWorldCenter(), true);

    if (steerEnt.getLinearVelocity().y > 0)
        steerEnt.body.applyLinearImpulse(stopDownVec(), steerEnt.body.getWorldCenter(), true);

    if (steerEnt.getLinearVelocity().y < 0)
        steerEnt.body.applyLinearImpulse(stopUpVec(), steerEnt.body.getWorldCenter(), true);
    }


}
