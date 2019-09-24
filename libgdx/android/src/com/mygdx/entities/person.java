package com.mygdx.entities;

import com.mygdx.world.gameMap;

public class person extends zombie {

    private boolean mInfected;
    private boolean mZombie;

    private int mAlerted;
    private int mInfctTime;

    public person(boolean iscpu, int classId, gameMap map) {

        super(iscpu, classId, map);
        if (classId < 0) {
            //depending on the class we might have a weapon and different attack points
            this.mZombie = false;
            this.mInfected = false;
        } else {
            //this is a zombie;
            this.mZombie = true;
            this.mInfected = true;
        }

        this.mAlerted = 0;
        this.mInfctTime = 0;
    }

    public void processMoves()
    {//virtual function

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

