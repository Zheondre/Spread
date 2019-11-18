package com.mygdx.entities.humans;

import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Hide;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.BehaviorEnum;
import com.mygdx.entities.Box2dSteering;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.objects.bomb;
import com.mygdx.world.gameMap;

import static com.mygdx.entities.BehaviorEnum.INFECTED;
import static com.mygdx.entities.BehaviorEnum.NEW_ZOMBIE;

public class person extends zombie {

    private boolean mInfected;
    private boolean mZombie;

    //protected int mAlerted;
    private float mInfctTime;

    private float wlkTime;
    private int wlkDirection;

    public Evade<Vector2> getEvadeSB() {
        return evadeSB;
    }

    public void setEvadeSB(Evade<Vector2> evadeSB) {
        if(evadeSB!= null)
            this.evadeSB = evadeSB;
        steerEnt.setBehavior(evadeSB);
    }

    public void setEvadeSB(zombie target) {
        if(target!= null) {
            //this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt(), .5f);
            this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt());
            this.setPrey(target);// temp
            steerEnt.setBehavior(evadeSB);
        }
    }

    public void setEvadeSB(bomb target) {
        if(target!= null) {
            //this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt(), .5f);
            this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt());
            this.setPrey(target);// temp
            steerEnt.setBehavior(evadeSB);
        }
    }

    public void changeEvadeTarget(Box2dSteering target){
        if(target != null)
            this.evadeSB.setTarget(target);
    }

    public void changeEvadeTarget(zombie target){
        if(target != null) {
            this.evadeSB.setTarget(target.getSteerEnt());
            this.setPrey(target);
        }
    }

    public entity whoBitMe(){
       return this.getPrey();
    }

    protected Evade<Vector2> evadeSB;
    protected Flee<Vector2>  fleeSB;
    protected Hide<Vector2>  hideSB;

    //seek or flee evad, face
    public person(entityInfo entityType, gameMap map) {

        super(entityType, map);

        if(entityType.getId() == classIdEnum.Person || entityType.getId() == classIdEnum.PPerson)
            this.setImage("player.png");

        this.weapon = entityType.getWeapon();
        this.mZombie =  entityType.isZombie();
        this.mInfected = entityType.isInfected();
        this.mAlerted = BehaviorEnum.WALK_RANDOMLY;
        this.mInfctTime = 100;
        this.wlkDirection = 0;
        this.wlkTime = -1;
    }

   // @Override
    public void update(float dTime){

        if(mZombie)
            super.update(dTime);
        else if(classID == classIdEnum.PPerson)
            super.update(dTime);
        else
            processMoves(dTime);
    }

    public void processMoves(float dTime)
    {//virtual function
        //check alertness
        //check if hurt or if infected
        if(!mZombie) {
            if (mInfected) {
                mInfctTime -= .03;
                if (mInfctTime < 0)
                    turnIntoAZombie();
            }
        }
        switch(mAlerted) {  //change steering ent based on alertness
            case TEST_WANDER_SB:
                // test wander steering ent
                if(getSteerEnt().getBehavior() != getWanderSB()){
                   getSteerEnt().setBehavior(getWanderSB());
                }
                break;
            case WALK_RANDOMLY:
                walkRandomly(dTime);
                //avoid object collision
                break;
            case EVADE_ZOMBIE:
                //zombie has been spotted
                //run away or attack at a safe distance
                //if we dont have the correct steering ent change
                if(getSteerEnt().getBehavior() != getEvadeSB()){
                    getSteerEnt().setBehavior(getEvadeSB());
                }
            break;
            case INFECTED:

                //if a zombie is too close evade
                if(false)
                    if(getSteerEnt().getBehavior() != getEvadeSB()){
                        // make sure to check if we have the evade sb allocated
                        getSteerEnt().setBehavior(getEvadeSB());
                    }
                else if(false) { // other wise seek the cure point when told by police or security
                       /*
                        if(getSteerEnt().getBehavior() != getSeekSB()){
                            // make sure to check if we have the seek sb allocated
                            getSteerEnt().setBehavior(getSeekSB());
                        }
                        */
                    }
                //if we dont have the correct steering ent change
                break;
            case NEW_ZOMBIE:
                // turned into a zombie so act as such.
                break;
            case ARRIVE_ZOMBIE:
                //pursue at a safe distance and attack or shoot
                break;
        }
        super.update(dTime);
    }

    public boolean areYouAZombie() {
        return mZombie;
    }

    public void turnIntoAZombie() {

        this.mInfctTime = 0;
        this.mInfected = false;
        this.mAlerted = NEW_ZOMBIE;
        this.mZombie = true;
        //this.setClsId(classIdEnum.Zombie);
        this.setClsId(classIdEnum.ConvertedPer);//Debug
        this.setImage("zombie.png");
        this.setPrey(null);

        mMap.getZombies().add(this); // if we multi thread use a semiphore
        mMap.setCnvrtdEntRdy(this);//temp code
    }

    public boolean isInfected() {
        return mInfected;
    }

    public void setInfected(boolean Infected) {
        this.mInfected = Infected;
        this.mAlerted = INFECTED;
    }

    public void setInfected(boolean Infected, bomb zom) {
        this.setEvadeSB(zom);
        this.mInfected = Infected;
        this.mAlerted = INFECTED;
    }

    public void setInfected(boolean Infected, zombie zom) {
        this.setEvadeSB(zom);
        this.mInfected = Infected;
        this.mAlerted = INFECTED;
    }


    public float getInfctTime() {
        return mInfctTime;
    }

    public void setmInfctTime(float InfctTime) {
        this.mInfctTime = InfctTime;
    }

    public void decreaseInfectTime(int InfctTime){
        this.mInfctTime -= InfctTime;
    }
}

