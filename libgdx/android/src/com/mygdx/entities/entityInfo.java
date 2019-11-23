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
    STATIC_OBJECT(classIdEnum.STATIC_OBJECT),
    EMT(classIdEnum.Emt),
    PBOMB(classIdEnum.PBomb);

    private classIdEnum weapon;
    private classIdEnum id;
    //private int width, hieght;

    private float xpos;
    private float ypos;

    private boolean isCpu;

    private int blastRadius;
    private int infectRadius;
    private int alertnedRadius;
    private int nonEffectsRadius;
    private int infecRatio;
    private int healthRatio;

    private float infectPts;
    private float attackPt;
    private float health;
    private float armor;

    private int walkSpeed;

    private boolean zombie;
    private boolean infected;

    private Texture Image;

    entityInfo(classIdEnum id) {

        this.id = id;
        this.weapon = classIdEnum.NOWEAPON;

       this.walkSpeed = 80;

        this.armor = 0;
        this.health = 1;
        this.attackPt = .03f;

        this.zombie = false;
        this.infected = false;
        this.isCpu = true;

        switch(id){
            case Emt:
                this.health = 2;
            case PBomb:
                this.isCpu = false;
                this.blastRadius = 40;
                this.infectRadius = 150;
                this.alertnedRadius = 250;
                this.nonEffectsRadius  = 550;
                this.infecRatio = 350;
                this.healthRatio = 300;
                break;
            case PZombie:
                this.xpos = 70;
                this.ypos = 70;
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
                //this.xpos = 75;
                //this.ypos = 25;
                break;
            case Security:
            this.armor = .50f;
               this.weapon = classIdEnum.BATON;
                break;
            case Cop:
                this.armor = .70f;
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

    public int getInfecRatio() {
        return infecRatio;
    }

    public int getHealthRatio() {
        return healthRatio;
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public int getInfectRadius() {
        return infectRadius;
    }

    public int getAlertnedRadius() {
        return alertnedRadius;
    }

    public int getNonEffectsRadius() {
        return nonEffectsRadius;
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

    public float getInfectPts() {
        return this.infectPts;
    }

    public float getAttackPt() {
        return this.attackPt;
    }

    public float getHealth() {
        return this.health;
    }

    public float getArmor() {
        return this.armor;
    }

    public int getWalkSpeed() {
        return this.walkSpeed;
    }

    public boolean isZombie() {
        return this.zombie;
    }


}
