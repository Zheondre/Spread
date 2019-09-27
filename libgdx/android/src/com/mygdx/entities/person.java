package com.mygdx.entities;

import com.mygdx.world.gameMap;

public class person extends zombie {

    private boolean mInfected;
    private boolean mZombie;

    private int mAlerted;
    private int mInfctTime;

    public person(entityInfo entityType, gameMap map) {

        super(entityType, map);

        this.mZombie =  entityType.isZombie();
        this.mInfected = entityType.isInfected();
        this.mAlerted = 0;
        this.mInfctTime = 0;

    }

   // @Override
    public void update(float dTime){

        if(this.iscpu()){
            processMoves(dTime);
        } else {
            super.update(dTime);
        }
    }

    public void processMoves(float dTime)
    {//virtual function
        //check alertness
        //check if hurt or if infected

        switch(mAlerted) {// we might want to change these into enums
            case 0:
                //over a random amount of time
                //walk randomly
                break;
            case 1:
                // you're hurt look for cover
            break;
            case 2:
                //oo shit i see a zombie, run or attack at a safe distance
                break;
            case 3:
                // you're infected look for a place to heal
                break;
            case 4:

                break;
        }
    }

    public boolean areYouAZombie() {
        return mZombie;
    }

    public void turnIntoAZombie() {
        this.mZombie = true;
    }

    public boolean isInfected() {
        return mInfected;
    }

    public void setInfected(boolean Infected) {
        this.mInfected = Infected;

    }

    public int getAlerted() {
        return mAlerted;
    }

    public void setAlerted(int Alerted) {
        this.mAlerted = Alerted;
    }

    public int getInfctTime() {
        return mInfctTime;
    }

    public void setmInfctTime(int InfctTime) {
        this.mInfctTime = InfctTime;
    }
}

