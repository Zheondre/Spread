package com.mygdx.entities;

import android.view.MotionEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ParticleControllerInfluencer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.gameMap;

import org.w3c.dom.Entity;

import java.util.*;
import java.util.Random;

public class zombie extends entity {

    protected int peopleConverted;
    protected int infections;
    protected int attackPt;
    protected int health;
    protected classIdEnum weapon;

    Texture image;

    private int mWidth = 14;
    private int mHeight = 15;
    private int wlkDirection;
    private float mWeight = 40;

    private boolean mIsCpu;
    private boolean doISeeANoneZombie;

    Box2dSteering steerEnt;

    public Box2dSteering getSteerEnt() {
        return steerEnt;
    }

    protected Pursue<Vector2>  pursueSB;
    protected Wander<Vector2>  wanderSB;
    protected Arrive<Vector2>  arriveSB;

//changeSb();
    public Arrive<Vector2> getArriveSB() {
        return arriveSB;
    }

    public void setArriveSB(Arrive<Vector2> arriveSB) {
        this.arriveSB = arriveSB;
    }

    public void setArriveSB(Box2dSteering prey) {
        if(!(prey == null))
            arriveSB = new Arrive<Vector2>(steerEnt,prey)
                    .setTimeToTarget(.5f)
                    .setArrivalTolerance(2f)
                    .setDecelerationRadius(4);
        steerEnt.setBehavior(arriveSB);//might put this as its own function
    }

    public void setArrivePrey(Box2dSteering prey) {
        if(!(prey == null) && !(arriveSB == null))
            arriveSB.setTarget(prey);

    }

    public Pursue<Vector2> getPursueSB() {
        return pursueSB;
    }

    public void setPursueSB(Pursue<Vector2> pursueSB) {
        if(!(pursueSB == null))
          this.pursueSB = pursueSB;
    }

    public void setPursueSB(Box2dSteering prey) {
        if(!(prey == null))
             pursueSB = new Pursue<Vector2>(steerEnt,prey,0.3f);
    }

    public void setPursuePrey(Box2dSteering prey){
        if(!(prey == null) && !(pursueSB == null))
            pursueSB.setTarget(prey);
    }

    public zombie(entityInfo entType, gameMap map) {
        super(entType, map);
        /*
        we will need to position characters in different locations based on the map and class  id
        we can figure this out later
         */

        this.classID = entType.getId();

        if(classID == classIdEnum.Zombie || classID == classIdEnum.PZombie)
            image = new Texture("zombie.png");

        this.infections = 0;
        this.attackPt = entType.getAttackPt();//
        this.health = entType.getHealth();
        this.weapon = entType.getWeapon();
        this.mIsCpu = entType.isCpu();

        this.doISeeANoneZombie = false;
        //this.randomWalkTime;

        this.steerEnt = new Box2dSteering(super.getBody(),10);

        if(this.mIsCpu){
            //put player towards the beginning of map if its not a new game
            //if its a new game dont draw the spite yet we got to set a bomb before hand

            // Wander<Vector2> wanderSB = new Wander<Vector2>(this.steerEnt);
        }
    }

    public boolean biteNonZombie(person victum){

        if(victum == null)
            return false;

        if(!victum.isInfected())
            victum.setInfected(true);

        victum.decreaseInfectTime(5);
        victum.decreaseHlth(5);

        return true;
    }

  //  @Override
    public void update(float dTime){

        steerEnt.update(dTime);

        if(this.mIsCpu) {

            if(this.classID == classIdEnum.Zombie) {

                if (doISeeANoneZombie) {
                    //goAfterNonZombie
                    //if we are close enough attack
                    // or follow the leader if instructed on oding so
                } else {
                    int xtemp = (int) (Math.random() * ((40 - 2) + 1)) + 2;
                    int ytemp = (int) (Math.random() * ((40 - 2) + 1)) + 2;
                    if (xtemp % 2 == 0)
                        xtemp *= -1;
                    if (ytemp % 2 == 0)
                        ytemp *= -1;
                    //moveX(xtemp * dTime);
                    //moveY(ytemp * dTime);
                }
            } else {
                super.update(dTime);
            }
        } else {
            //player has control
            super.update(dTime);
            //need to also move the camera
        }
       // MotionEvent event;
      //  super.setPosX(event.getX());
    }

    public void setImage(String path){
        image = new Texture(path);
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

    public void decreaseHlth(int amount){
        this.health -= amount;
    }

    public classIdEnum getClsId() {
        return classID;
    }

    public void setClsId(classIdEnum clsId) {
        this.classID = clsId;
    }
}
