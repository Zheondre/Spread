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
    ZOMBIE(classIdEnum.Zombie),
    STATIC_OBJECT(classIdEnum.STATIC_OBJECT);

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

        this.id = id;
        this.weapon = classIdEnum.NOWEAPON;

        this.walkSpeed = 80;

        this.armor = 0;
        this.health = 100;
        this.attackPt = 3;

        this.zombie = false;
        this.infected = false;
        this.isCpu = true;

        switch(id){
            case PZombie:
                this.xpos = 25;
                this.ypos = 25;
                this.infected = true;
                this.zombie = true;
                this.isCpu = false;
                break;
            case PPerson:
                this.xpos = 250;
                this.ypos = 50;
                this.isCpu = false;
                break;
            case Zombie:
                this.xpos = 30;
                this.ypos = 50;
                //this.infectPts = 3;
                this.zombie = true;
                this.infected = true;
                this.walkSpeed = 40;
                break;
            case Person:
                //temp dont commit
                //this.xpos = 75;
                //this.ypos = 25;
                break;
            case Security:
                this.armor = 50;
                this.weapon = classIdEnum.BATON;
                break;
            case Cop:
                this.armor = 70;
                this.weapon = classIdEnum.PISTOL;
                break;
            case STATIC_OBJECT:
                this.walkSpeed = 0;
                this.armor = 0;
                this.health = 0;
                this.attackPt = 0;
                this.zombie = false;
                this.infected = false;
                this.isCpu = true;
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
