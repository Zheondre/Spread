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

   private static final int speed = 80;

    protected int infections;
    protected int attackPt;
    protected int health;
    protected int weapon;

    Texture image;

    private int mWidth = 14;
    private int mHeight = 31;
    private float mWeight = 40;

    private boolean mIsCpu;

    public zombie(boolean IsCpu, int classID, gameMap map) {
        super((float)Math.random(),(float)Math.random(), classID, map);
        //random methods dont seem to work
        /*
        we will need to position characters in different locations based on the map and class  id
        we can figure this out later
         */

        image = new Texture("player.png");// need to make this dynamic

        this.classID = classID;
        this.infections = 0;
        this.attackPt = 3;
        this.health = 100;
        this.weapon = 0;
        this.mIsCpu = IsCpu;

        if(this.mIsCpu){
            //put player towards the beginning of map if its not a new game
            //if its a new game dont draw the spite yet we got to set a bomb before hand
        }
    }

    /*
    public boolean onTouchEvent(MotionEvent e){

        super.setPosX(e.getX());
        super.setPosY(e.getY());
        switch(e.getAction()){
        }
        return true;
    }
    */
    //@Override
    public void update(float dTime){

        if(!this.mIsCpu) {
            //checkfortouchevents
            //need to also move the camera
            //mMap.update(dTime);
            if(Gdx.input.isKeyPressed(Keys.UP))
                moveX(speed * dTime);

            if(Gdx.input.isKeyPressed(Keys.DOWN))
                moveX(-speed * dTime);

            if(Gdx.input.isKeyPressed(Keys.LEFT))
                moveY(-speed * dTime);

            if(Gdx.input.isKeyPressed(Keys.RIGHT))
                moveY(speed * dTime);
        } else {
            //proceedBehavoir
        }
       // MotionEvent event;
      //  super.setPosX(event.getX());
    }

   // @Override
    public void render(SpriteBatch batch){
        //mMap.update();
        batch.draw(image,mPos.x, mPos.y, mWidth, mHeight);
      //  batch.end();
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

    public void setWeapon(byte weapon) {
        this.weapon = weapon;
    }

    public int getClsId() {
        return classID;
    }

    public void setClsId(int clsId) {
        this.classID = clsId;
    }
        //public zombie* instance() {}
}
