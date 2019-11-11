package com.mygdx.entities;

import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Hide;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.gameMap;

import static com.badlogic.gdx.Input.Keys.UP;

public class person extends zombie {

    private boolean mInfected;
    private boolean mZombie;

    protected int mAlerted;
    private float mInfctTime;

    private float wlkTime;
    private int wlkDirection;

    public Evade<Vector2> getEvadeSB() {
        return evadeSB;
    }

    public void setEvadeSB(Evade<Vector2> evadeSB) {
        if(evadeSB!= null)
            this.evadeSB = evadeSB;
    }

    public void setEvadeSB(zombie target) {
        if(target!= null) {
            //this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt(), .5f);
            this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt());
            this.setPrey(target);// temp
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
                mInfctTime -= .04;
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
                //avoid object collision
                break;
            case 1:
                //evade
                // zombie has been spotted but not infected
                // run or attack at a safe distance
                // you're hurt look for cover, find hospital or cop ect
            break;
            case 2:
                //evade or seek help
                steerEnt.update(dTime);
                //zombie has been spotted or you have been attacked
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

        this.mInfctTime = 0;
        this.mInfected = false;
        this.mAlerted = 3;
        this.mZombie = true;
        //this.setClsId(classIdEnum.Zombie);
        this.setClsId(classIdEnum.ConvertedPer);
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
        this.mAlerted = 2;
    }

    public void setInfected(boolean Infected, zombie zom) {
        this.setEvadeSB(zom);
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

