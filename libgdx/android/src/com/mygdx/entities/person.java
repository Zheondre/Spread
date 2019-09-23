package com.mygdx.entities;

public class person extends zombie {
//civilain
    private boolean mZombie;
    private boolean mInfected;
    private boolean mIsCpu;
    private int mAlerted;
    private int mInfctTime;

// going to need a third constructor for the security class
    public person(int classId, boolean isCpu) {

        super(classId,0,3,100,0);

        if(classId < 0) {
            //depending on the class we might have a weapon and different attack points
            this.mZombie = false;
            this.mInfected = false;
        }
        else {
        //this is a zombie;
            this.mZombie = true;
            this.mInfected = true;
        }

        this.mIsCpu = isCpu;
        this.mAlerted = 0;
        this.mInfctTime = 0;
    }

    public void processMoves()
    {//virtual function

    }

    public boolean isZombie() {
        return mZombie;
    }

    public void setZombie(boolean Zombie) {
        this.mZombie = Zombie;
    }

    public boolean isInfected() {
        return mInfected;
    }

    public void setInfected(boolean Infected) {
        this.mInfected = Infected;
    }

    public boolean isIsCpu() {
        return mIsCpu;
    }

    public void setIsCpu(boolean IsCpu) {
        this.mIsCpu = IsCpu;
    }

    public int getmAlerted() {
        return mAlerted;
    }

    public void setmAlerted(int Alerted) {
        this.mAlerted = Alerted;
    }

    public int getmInfctTime() {
        return mInfctTime;
    }

    public void setmInfctTime(int InfctTime) {
        this.mInfctTime = InfctTime;
    }
}

