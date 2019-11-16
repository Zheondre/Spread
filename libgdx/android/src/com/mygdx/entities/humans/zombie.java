package com.mygdx.entities.humans;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.Box2dSteering;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.humans.person;
import com.mygdx.world.gameMap;

import static com.mygdx.utils.entUtils.stopDownVec;
import static com.mygdx.utils.entUtils.stopLeftVec;
import static com.mygdx.utils.entUtils.stopRightVec;
import static com.mygdx.utils.entUtils.stopUpVec;
import static java.lang.StrictMath.abs;

public class zombie extends entity {

    protected int peopleConverted;
    protected int infections;
    protected int attackPt;
    protected int health;

    protected classIdEnum weapon;

    private static final int biteTimeSetting = 5;
    private float bitetime = biteTimeSetting;
    private float wlkTime;
    private int wlkDirection;

    private boolean mIsCpu;
    private boolean doISeeANoneZombie;

    //private Box2dSteering prey;
    private entity prey;

    public float getPreyDistance() {
        return preyDistance;
    }

    private float preyDistance;

   protected Box2dSteering steerEnt;

    protected Pursue<Vector2>  pursueSB;
    protected Wander<Vector2>  wanderSB;
    protected Arrive<Vector2>  arriveSB;

    public Box2dSteering getSteerEnt() {
        return steerEnt;
    }
    public Wander<Vector2> getWanderSB() {
        return wanderSB;
    }
    public Arrive<Vector2> getArriveSB() {
        return arriveSB;
    }
    public Pursue<Vector2> getPursueSB() {
        return pursueSB;
    }

    //changeSb();

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
            pursueSB = new Pursue<Vector2>(steerEnt, prey.getSteerEnt(),.5f);
            steerEnt.setBehavior(pursueSB);//might put this as its own function
            this.prey = prey;
        }
    }

    public void setPursuePrey(Box2dSteering prey){
        if(pursueSB != null)
            pursueSB.setTarget(prey);
    }

    public void setPrey(entity prey){ this.prey = prey; }
    public entity getPrey(){ return this.prey; }

    public zombie(entityInfo entType, gameMap map) {
        super(entType, map);
        /*
        we will need to position characters in different locations based on the map and class  id
        we can figure this out later
         */

        this.classID = entType.getId();

        if(classID == classIdEnum.Zombie || classID == classIdEnum.PZombie)
            setImage("zombie.png");

        this.infections = 0;
        this.attackPt = entType.getAttackPt();//
        this.health = entType.getHealth();
        this.weapon = entType.getWeapon();
        this.mIsCpu = entType.isCpu();

        this.doISeeANoneZombie = false;
        //this.randomWalkTime;
        this.steerEnt = new Box2dSteering(super.getBody(),10);

        this.wanderSB = new Wander<Vector2>(steerEnt) //
                .setFaceEnabled(true) // We want to use Face internally (independent facing is on)
                .setAlignTolerance(1f) // Used by Face
                .setDecelerationRadius(20f) // Used by Face
                .setTimeToTarget(20f) // Used by Face
                .setWanderOffset(50f) //
                .setWanderOrientation(100f) //
                .setWanderRadius(100f) //
                .setWanderRate(MathUtils.PI2 * 30);

        if(this.mIsCpu){
            //put player towards the beginning of map if its not a new game
            //if its a new game dont draw the spite yet we got to set a bomb before hand
            steerEnt.setBehavior(wanderSB);
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
            wlkTime =  (float)(Math.random()*((5-2)+2))+1;
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
        //preyDistance
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

        victum.decreaseInfectTime(5);
        victum.decreaseHlth(1);

        return true;
    }

  //  @Override
    public void update(float dTime){

        if(this.mIsCpu) {

            //check if there are any special messages
            switch(this.classID) {
                case Person:
                    if(steerEnt.getBehavior() != this.getWanderSB())
                        steerEnt.update(dTime);
                    else
                        super.update(dTime);
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

                    if (doISeeANoneZombie) {

                        float tempD = getEntDistance();
                        if(tempD < 13 && tempD > -1){
                            // what if two zombies are pointing at the same converted person/////
                            // to do, just send a message to all zombie listeners hunting for prey to stop when prey is a zombie

                            if(((person)this.getPrey()).areYouAZombie()){
                                // if we multi thread use a semiphore // i dont think this should be handled here
                                this.setPrey(null);
                                doISeeANoneZombie = false;
                                //increment score count
                            }  else {
                                if (steerEnt.getLinearVelocity().x > 0)
                                    steerEnt.body.applyLinearImpulse(stopLeftVec(), steerEnt.body.getWorldCenter(), true);

                                if (steerEnt.getLinearVelocity().x < 0)
                                    steerEnt.body.applyLinearImpulse(stopRightVec(), steerEnt.body.getWorldCenter(), true);

                                if (steerEnt.getLinearVelocity().y > 0)
                                    steerEnt.body.applyLinearImpulse(stopDownVec(), steerEnt.body.getWorldCenter(), true);

                                if (steerEnt.getLinearVelocity().y < 0)
                                    steerEnt.body.applyLinearImpulse(stopUpVec(), steerEnt.body.getWorldCenter(), true);

                                if (bitetime == biteTimeSetting)
                                    biteNonZombie((person) this.getPrey());
                                else {
                                    bitetime -= .05;
                                    if (bitetime < 0)
                                        bitetime = biteTimeSetting;
                                }
                            }
                        } else {

                            if(this.getPrey() != null)
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
        if(image != null)
            batch.draw(image,mPos.x, mPos.y, getWidth(), getHeight());
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

    public boolean iscpu() { return mIsCpu; }

    public void setCpuStatus(boolean IsCpu) { this.mIsCpu = IsCpu; }

    public int getInfections() { return infections; }

    public void setInfections(int infections) { this.infections = infections; }

    public int getAttackPt() { return attackPt; }

    public void setAttackPt(int attackPt) { this.attackPt = attackPt; }

    public void setHealth(int health) { this.health = health; }

    public void setWeapon(classIdEnum weapon) { this.weapon = weapon; }

    public void decreaseHlth(int amount){ this.health -= amount; }

    public classIdEnum getClsId() { return classID; }

    public void setClsId(classIdEnum clsId) { this.classID = clsId; }
}
