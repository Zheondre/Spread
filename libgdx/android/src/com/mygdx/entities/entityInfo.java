package com.mygdx.entities;
/*
Use this to change game settings
so we dont have to change start up settings in every file

If something is missing update file please
 */


//Enum with custommized values
// should we change base on level here or in classes?

//should we include file path's in here or in side actual class ?

import com.badlogic.gdx.graphics.Texture;

public enum entityInfo {

    ZPLAYER(classIdEnum.PZombie),
    CPlAYER(classIdEnum.PPerson),
    PERSON(classIdEnum.Person),
    COP(classIdEnum.Cop),
    SECURITY(classIdEnum.Security),
    ZOMBIE(classIdEnum.Zombie);

    private classIdEnum weapon;
    private classIdEnum id;
    //private int width, hieght;

    private float xpos;
    private float ypos;

    private boolean isCpu;

    private int infectPts;
    private int attackPt;
    private int health;
    private int armor;

    private int walkSpeed;

    private boolean zombie;
    private boolean infected;

    private Texture Image;

    entityInfo(classIdEnum id) {

        //this.width = width;
        //this.hieght = hieght;

        this.id = id;
        this.weapon = classIdEnum.NOWEAPON;

        this.xpos = (float)Math.random();
        this.ypos = (float)Math.random();

        this.walkSpeed = 80;

        this.armor = 0;
        this.health = 100;
        this.attackPt = 3;

        this.zombie = false;
        this.infected = false;
        this.isCpu = true;

        switch(id){
            case PZombie:
                this.xpos = 150;
                this.ypos = 150;
                this.infected = true;
                this.zombie = true;
                this.isCpu = false;
                break;
            case PPerson:
                this.xpos = 0;
                this.ypos = 0;
                this.isCpu = false;
                break;
            case Zombie:
                this.infectPts = 3;
                this.zombie = true;
                this.infected = true;
                this.walkSpeed = 40;
                break;
            case Person:
                //temp dont commit
                this.xpos = 75;
                this.ypos = 75;
                break;
            case Security:
                this.armor = 40;
                this.weapon = classIdEnum.BATON;
                break;
            case Cop:
                this.armor = 70;
                this.weapon = classIdEnum.PISTOL;
                break;
            default:
                //throw an error, unknown type
        }
    }

    public boolean isInfected() {
        return infected;
    }

    public classIdEnum getWeapon() {
        return this.weapon;
    }

    public classIdEnum getId() {
        return this.id;
    }

    public float getXpos() {
        return this.xpos;
    }

    public float getYpos() {
        return this.ypos;
    }

    public boolean isCpu() {
        return this.isCpu;
    }

    public int getInfectPts() {
        return this.infectPts;
    }

    public int getAttackPt() {
        return this.attackPt;
    }

    public int getHealth() {
        return this.health;
    }

    public int getArmor() {
        return this.armor;
    }

    public int getWalkSpeed() {
        return this.walkSpeed;
    }

    public boolean isZombie() {
        return this.zombie;
    }


}
