package com.mygdx.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.world.gameMap;

import static com.badlogic.gdx.Input.Keys.UP;

public class person extends zombie {

    private boolean mInfected;
    private boolean mZombie;

    private int mAlerted;
    private int mInfctTime;

    private float wlkTime;
    private int wlkDirection;

    public person(entityInfo entityType, gameMap map) {

        super(entityType, map);

        if(entityType.getId() == classIdEnum.Person)
            this.setImage("player.png");// we can call this person.png

        this.mZombie =  entityType.isZombie();
        this.mInfected = entityType.isInfected();
        this.mAlerted = 0;
        this.mInfctTime = 0;
        this.wlkDirection = 0;
        this.wlkTime = -1;
    }

   // @Override
    public void update(float dTime){
        if(mZombie)
            super.update(dTime);
        else
            processMoves(dTime);
    }

    public void processMoves(float dTime)
    {//virtual function
        //check alertness
        //check if hurt or if infected

        switch(mAlerted) {// we might want to change these into enums
            case 0:
                //if the next move in our current direction is invalid
                // change direction if there is an invalid path
                if(wlkTime < 0) {
                    wlkTime =  (float)(Math.random()*((8-3)+3))+1;
                    wlkDirection = (int)(Math.random()*((4-1)+1))+1;
                }else {
                    wlkTime -= dTime;

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
                    }
                }
                break;
            case 1:
                // you're hurt look for cover
            break;
            case 2:
                //oo shit i see a zombie, run or attack at a safe distance
                break;
            case 3:
                // you're infected look for a place to heal
                // hospital med or anti infect packs
                break;
            case 4:
                break;
        }
        super.update(dTime);
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
        this.mAlerted = 3;
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

    public void decreaseInfectTime(int InfctTime){
        this.mInfctTime -= InfctTime;
    }
}

