package com.mygdx.entities.humans;

import android.os.SystemClock;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Hide;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.AiStates.MessageType;
import com.mygdx.entities.BehaviorEnum;
import com.mygdx.entities.Box2dSteering;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.objects.bomb;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

import static com.mygdx.AiStates.MessageType.GIVE_PER_LOCATION;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY_DENIED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED_REPLY;
import static com.mygdx.entities.BehaviorEnum.INFECTED;
import static com.mygdx.entities.BehaviorEnum.NEW_ZOMBIE;

public class person extends zombie {

    private boolean mInfected;
    private boolean mZombie;

    boolean walk = true;
    protected int MessageMsk = 0;
    //protected int mAlerted;
    //private float mInfctTime;

    private float wlkTime;
    private int wlkDirection;

    public Evade<Vector2> getEvadeSB() {
        return evadeSB;
    }

    public void setEvadeSB(Evade<Vector2> evadeSB) {
        if(evadeSB!= null)
            this.evadeSB = evadeSB;
        steerEnt.setBehavior(evadeSB);
    }

    public void setEvadeSB(zombie target) {
        if(target!= null) {
            //this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt(), .5f);
            this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt());
            this.setPrey(target);// temp
            steerEnt.setBehavior(evadeSB);
        }
    }

    public void setEvadeSB(bomb target) {
        if(target!= null) {
            //this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt(), .5f);
            this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt());
            this.setPrey(target);// temp
            steerEnt.setBehavior(evadeSB);
        }
    }

    public void changeEvadeTarget(Box2dSteering target){
        if(target != null)
            this.evadeSB.setTarget(target);
    }

    public void changeEvadeTarget(zombie target){
        if(target != null) {
            this.evadeSB.setTarget(target.getSteerEnt());
            this.setPrey(target);
        }
    }

    public entity whoBitMe(){
       return this.getPrey();
    }

    protected Evade<Vector2> evadeSB;
    protected Flee<Vector2>  fleeSB;
    protected Hide<Vector2>  hideSB;

    //seek or flee evad, face
    public person(entityInfo entityType, gameMap map) {

        super(entityType, map);

        if(entityType.getId() == classIdEnum.Person || entityType.getId() == classIdEnum.PPerson) {
            this.setImage("player.png");
            setImageRight("player.png");
            setImageRightWalk("player2.png");
            setImageUp("playerUp.png");
            setImageUpWalk("playerUp2.png");
            setImageLeft("playerLeft.png");
            setImageLeftWalk("playerLeft2.png");
            setImageDown("playerDown.png");
            setImageDownWalk("playerDown2.png");
            //this.setImageB("player2.png");
            MessageManager.getInstance().addListeners(this,HELP_ZOMBIE_SPOTTED_REPLY, HELP_INFECTED_REPLY, GIVE_PER_LOCATION,HELP_ZOMBIE_SPOTTED_REPLY, HELP_INFECTED_REPLY_DENIED);
            this.mInfctTime = 1;
        } else if (entityType.getId() != classIdEnum.Emt) {

        }
        this.weapon = entityType.getWeapon();
        this.mZombie =  entityType.isZombie();
        this.mInfected = entityType.isInfected();
        this.mAlerted = BehaviorEnum.WALK_RANDOMLY;
        this.wlkDirection = 0;
        this.wlkTime = -1;
    }

    public boolean handleMessage(Telegram msg){

        switch(msg.message) {
            case HELP_ZOMBIE_SPOTTED:
                //zombie is in raycast view flee but allet athoreties to location
                break;
            case HELP_ZOMBIE_SPOTTED_REPLY:
                break;
            case HELP_INFECTED_REPLY:
                //ask for help
                break;
            case HELP_INFECTED_REPLY_DENIED:
                //if I didnt make the request just ignore
            case GIVE_PER_LOCATION:
                // go to a location told by athorities
                break;
        }
        return true;
    }

    public boolean callForHelp( int msVal){
        if( (0) == (MessageMsk & (1 <<msVal)) ) { // ask for help every like 10 seconds until help arrives ?
            MessageMsk = (MessageMsk | (1 <<msVal));
            ((tileGameMap) getMap()).getMgMang().dispatchMessage(this, msVal);
            return true;
        } else
            return false;
    }

    public void dispose(){
        super.dispose();
    }

   // @Override
    public void update(float dTime){

        if(mZombie)
            super.update(dTime);
        else if(classID == classIdEnum.PPerson) //debug player is controlling person
            super.update(dTime);
        else
            processMoves(dTime);
        /*if(((SystemClock.elapsedRealtime() / 250) % 2) == 1)
            changeImage(true, 2);
        else changeImage(false, 1);*/

        if(this.isMoveRight()) {
            if (((SystemClock.elapsedRealtime() / 250) % 2) == 1)
                changeImage(true, 1);
            else changeImage(false, 1);
        }
        if(this.isMoveUp()) {
            if (((SystemClock.elapsedRealtime() / 250) % 2) == 1)
                changeImage(true, 2);
            else changeImage(false, 2);
        }
        if(this.isMoveLeft()) {
            if (((SystemClock.elapsedRealtime() / 250) % 2) == 1)
                changeImage(true, 3);
            else changeImage(false, 3);
        }
        if(this.isMoveDown()) {
            if (((SystemClock.elapsedRealtime() / 250) % 2) == 1)
                changeImage(true, 4);
            else changeImage(false, 4);
        }


    }

    public void processMoves(float dTime)
    {//virtual function
        //check alertness
        //check if hurt or if infected
        if(!mZombie) {
            if (mInfected) {
                mInfctTime -= .03;
                if (mInfctTime < 0)
                    turnIntoAZombie();
            }
        }
        switch(mAlerted) {  //change steering ent based on alertness
            case TEST_WANDER_SB:
                // test wander steering ent
                if(getSteerEnt().getBehavior() != getWanderSB()){
                   getSteerEnt().setBehavior(getWanderSB());
                }
                break;
            case WALK_RANDOMLY:
                walkRandomly(dTime);
                //avoid object collision
                break;
            case EVADE_ZOMBIE:
                //zombie has been spotted
                //run away or attack at a safe distance
                //if we dont have the correct steering ent change
                callForHelp(MessageType.HELP_ZOMBIE_SPOTTED);
                if(getSteerEnt().getBehavior() != getEvadeSB()){
                    getSteerEnt().setBehavior(getEvadeSB());
                }
            break;
            case INFECTED:

                //if a zombie is too close evade
                callForHelp(MessageType.HELP_INFECTED);

                if(false)
                    if(getSteerEnt().getBehavior() != getEvadeSB()){
                        // make sure to check if we have the evade sb allocated
                        getSteerEnt().setBehavior(getEvadeSB());
                    }
                else if(false) { // other wise seek the cure point when told by police or security
                       /*
                        if(getSteerEnt().getBehavior() != getSeekSB()){
                            // make sure to check if we have the seek sb allocated
                            getSteerEnt().setBehavior(getSeekSB());
                        }
                        */
                    }
                //if we dont have the correct steering ent change
                break;
            case NEW_ZOMBIE:
                //put a status bit here to make sure we only call once
                MessageManager.getInstance().removeListener(this ,HELP_ZOMBIE_SPOTTED_REPLY, HELP_INFECTED_REPLY, GIVE_PER_LOCATION,HELP_ZOMBIE_SPOTTED_REPLY );
                // turned into a zombie so act as such.
                break;
            case ARRIVE_ZOMBIE:
                //pursue at a safe distance and attack or shoot
                break;
        }
        super.update(dTime);
    }

    public boolean areYouAZombie() {
        return mZombie;
    }

    public void turnIntoAZombie() {

        this.mInfctTime = 0;
        this.mInfected = false;
        this.mAlerted = NEW_ZOMBIE;
        this.mZombie = true;
        //this.setClsId(classIdEnum.Zombie);
        this.setClassID(classIdEnum.ConvertedPer);//Debug
        this.setImage("zombie.png");
        this.setImageRight("zombie.png");
        this.setImageRightWalk("zombie2.png");
        this.setImageUp("zombieUp.png");
        this.setImageUpWalk("zombieUp2.png");
        this.setImageLeft("zombieLeft.png");
        this.setImageLeftWalk("zombieLeft2.png");
        this.setImageDown("zombieDown.png");
        this.setImageDownWalk("zombieDown2.png");
        this.setPrey(null);

        mMap.getZombies().add(this); // if we multi thread use a semiphore
        mMap.setCnvrtdEntRdy(this);
    }

    public boolean isInfected() {
        return mInfected;
    }

    public void setInfected(boolean Infected) {
        this.mInfected = Infected;
        this.mAlerted = INFECTED;
    }

    public void setInfected(boolean Infected, bomb zom) {
        this.setEvadeSB(zom);
        this.mInfected = Infected;
        this.mAlerted = INFECTED;
    }

    public void setInfected(boolean Infected, zombie zom) {
        this.setEvadeSB(zom);
        this.mInfected = Infected;
        this.mAlerted = INFECTED;
    }

    public float getInfctTime() {
        return mInfctTime;
    }

    public void setmInfctTime(float InfctTime) {
        this.mInfctTime = InfctTime;
    }

    public void decreaseInfectTime(float InfctTime){
        this.mInfctTime -= InfctTime;
    }
}

