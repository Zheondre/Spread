package com.mygdx.entities;

import android.view.MotionEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ParticleControllerInfluencer;
import com.mygdx.world.gameMap;

import java.util.*;
import java.util.Random;

public class zombie extends entity {

    protected int infections;
    protected int attackPt;
    protected int health;
    protected classIdEnum weapon;

    Texture image;

    private int mWidth = 14;
    private int mHeight = 31;
    private float mWeight = 40;

    private boolean mIsCpu;

    public zombie(entityInfo entType, gameMap map) {
        super(entType, map);
        /*
        we will need to position characters in different locations based on the map and class  id
        we can figure this out later
         */

        image = new Texture("player.png");// need to make this dynamic

        this.classID = entType.getId();
        this.infections = 0;
        this.attackPt = entType.getAttackPt();//
        this.health = entType.getHealth();
        this.weapon = entType.getWeapon();
        this.mIsCpu = entType.isCpu();

        //this.randomWalkTime;

        if(this.mIsCpu){
            //put player towards the beginning of map if its not a new game
            //if its a new game dont draw the spite yet we got to set a bomb before hand
        }
    }

  //  @Override
    public void update(float dTime){

        int xtemp = (int)(Math.random()*((40-2)+1))+2;
        int ytemp = (int)(Math.random()*((40-2)+1))+2;
        if(xtemp % 2 == 0)
        {
            xtemp *= -1;
        }
        if(ytemp % 2 == 0)
        {
            ytemp *= -1;
        }

        if(this.mIsCpu) {
            moveX(xtemp * dTime);
            moveY(ytemp * dTime);
            //need to also move the camera
            //mMap.update(dTime);

        } else {
            super.update(dTime);
        }
       // MotionEvent event;
      //  super.setPosX(event.getX());
    }

   // @Override
    public void render(SpriteBatch batch){
        batch.draw(image,mPos.x, mPos.y, mWidth, mHeight);
    }

    public void attack(){}

    public boolean iscpu() {
        return mIsCpu;
    }

    public void setCpuStatus(boolean IsCpu) {
        this.mIsCpu = IsCpu;
    }

    public int getWidth(){
        return mWidth;
    }

    public int getHeight(){
        return mHeight;
    }

    public int getInfections() {
        return infections;
    }

    public void setInfections(int infections) {
        this.infections = infections;
    }

    public int getAttackPt() {
        return attackPt;
    }

    public void setAttackPt(int attackPt) {
        this.attackPt = attackPt;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setWeapon(classIdEnum weapon) {
        this.weapon = weapon;
    }

    public classIdEnum getClsId() {
        return classID;
    }

    public void setClsId(classIdEnum clsId) {
        this.classID = clsId;
    }
}
