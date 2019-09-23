package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.world.gameMap;

public class zombie extends entity {

    //  private int poxZ;

   //private static final int speed = 80;

    protected int infections;
    protected int attackPt;
    protected int health;
    protected int weapon;

    protected int classID;

    Texture image;

    private int mWidth = 14;
    private int mHeight = 31;
    private float mWeight = 40;


    public zombie(int classID, gameMap map, float x, float y) {
        super(x,y,map);

        image = new Texture("player.png");// need to make this dynamic

        this.classID = classID;
        this.infections = 0;
        this.attackPt = 3;
        this.health = 100;
        this.weapon = 0;
    }

/*
    public update(){ }
  */
   // @Override
    public void render(SpriteBatch batch){

        batch.draw(image,mPos.x, mPos.y, mWidth, mHeight);
        batch.end();
    }

    public void attack(){}


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
