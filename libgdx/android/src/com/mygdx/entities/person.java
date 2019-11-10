package com.mygdx.entities;

import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Hide;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.gameMap;

import static com.badlogic.gdx.Input.Keys.UP;

public class person extends zombie {

    //public boolean moved = false;

    private boolean mInfected;
    private boolean mZombie;

    protected int mAlerted;
    private float mInfctTime;

    private float wlkTime;
    private int wlkDirection;

    protected Evade<Vector2> evadeSB;
    protected Flee<Vector2>  fleeSB;
    protected Hide<Vector2>  hideSB;

    //seek or flee evad, face
    public person(entityInfo entityType, gameMap map) {

        super(entityType, map);

        if(entityType.getId() == classIdEnum.Person || entityType.getId() == classIdEnum.PPerson)
            this.setImage("player.png");// we can call this person.png

        this.mZombie =  entityType.isZombie();
        this.mInfected = entityType.isInfected();
        this.mAlerted = 0;
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
                mInfctTime -= .05;
                if (mInfctTime < 0)
                    turnIntoAZombie();
            }
        }
        switch(mAlerted) {// we might want to change these into enums
            case 5:
                // test wander steering ent
                if(getSteerEnt().getBehavior() != getWanderSB()){
                   getSteerEnt().setBehavior(getWanderSB());
                }
                break;
            case 0:
                walkRandomly(dTime);
                break;
            case 1:
                //oo shit i see a zombie, run or attack at a safe distance
                // you're hurt look for cover, find hospital or cop ect
            break;
            case 2:
                // you're hurt/infected
                // look for help, find hospital, cop, emt, or healthpacts
                break;
            case 3:
                // turned into a zombie so act as such.
                break;
        }
        super.update(dTime);
    }

    public boolean areYouAZombie() {
        return mZombie;
    }

    public void turnIntoAZombie() {
        this.moved = true;
        this.mInfctTime= 0;
        this.mInfected = false;
        this.mAlerted = 3;
        this.mZombie = true;
        this.setClsId(classIdEnum.Zombie);
        this.setImage("zombie.png");

       // mMap.getPeople().remove(this);
        mMap.getZombies().add(this); // if we multi thread use a semiphore
        mMap.setMoveReady(true);//temp code
    }

    public boolean isInfected() {
        return mInfected;
    }

    public void setInfected(boolean Infected) {
        this.mInfected = Infected;
        this.mAlerted = 2;
    }

    public int getAlerted() {
        return mAlerted;
    }

    public void setAlerted(int Alerted) {
        this.mAlerted = Alerted;
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

