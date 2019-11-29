package com.mygdx.entities.humans;

import android.os.SystemClock;
import android.util.Log;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Hide;
import com.badlogic.gdx.math.Vector2;
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
import static com.mygdx.AiStates.MessageType.HELP_BOMB_INFECTED;
import static com.mygdx.AiStates.MessageType.HELP_BOMB_INFECTED_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY_DENIED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED_REPLY;
import static com.mygdx.entities.BehaviorEnum.BOMB_INFECTED;
import static com.mygdx.entities.BehaviorEnum.INFECTED;
import static com.mygdx.entities.BehaviorEnum.NEW_ZOMBIE;
import static com.mygdx.entities.BehaviorEnum.WALK_RANDOMLY;

public class person extends zombie {

    private boolean mInfected;
    private boolean mZombie;
    private float wlkTime;

    protected int MessageMsk = 0;
    protected Evade<Vector2> evadeSB;
    protected Flee<Vector2>  fleeSB;
    protected Hide<Vector2>  hideSB;

    boolean walk = true;

    public void setEvadeSB(Evade<Vector2> evadeSB) {
        if(evadeSB!= null)
            this.evadeSB = evadeSB;
        steerEnt.setBehavior(evadeSB);
    }

    public void setEvadeSB(zombie target) {
        if(target!= null) {
            if(this.evadeSB == null) {
                //this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt(), .5f);
                this.evadeSB = new Evade<>(this.steerEnt, target.getSteerEnt());
                combinedSB.add(this.evadeSB);
                steerEnt.setBehavior(evadeSB);

            } else{
                this.evadeSB.setTarget(target.getSteerEnt());
            }
            steerEnt.setBehavior(evadeSB);
            this.setPrey(target);// temp
            evadeSB.setEnabled(true);
        }
    }

    public void setEvadeSB(bomb target) {
        if(target!= null) {
            if (this.evadeSB == null){
                //this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt(), .5f);
                this.evadeSB = new Evade<>(this.steerEnt, target.getSteerEnt());
                combinedSB.add(evadeSB);
                steerEnt.setBehavior(evadeSB);
             } else {
                this.pursueSB.setTarget(target.getSteerEnt());
            }
            this.setPrey(target);// temp
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

    //seek or flee evad, face
    public person(entityInfo entityType, gameMap map) {
        super(entityType, map);

        if(entityType.getId() == classIdEnum.Person || entityType.getId() == classIdEnum.PPerson) {
            this.setImage("player.png");
            this.setImageB("player2.png");
            MessageManager.getInstance().addListeners(this,HELP_ZOMBIE_SPOTTED_REPLY, HELP_INFECTED_REPLY, GIVE_PER_LOCATION,HELP_ZOMBIE_SPOTTED_REPLY, HELP_INFECTED_REPLY_DENIED);

        }
        this.mInfctTime = 1;
        this.weapon = entityType.getWeapon();
        this.mZombie =  entityType.isZombie();
        this.mInfected = entityType.isInfected();
        this.mAlerted = BehaviorEnum.WALK_RANDOMLY;
        this.wlkDirection = 0;
        this.wlkTime = -1;
    }

    public boolean handleMessage(Telegram msg){
        entity temp;

        switch(msg.message) {
            case HELP_ZOMBIE_SPOTTED:
                //zombie is in raycast view flee but allet athoreties to location
                break;
            case HELP_ZOMBIE_SPOTTED_REPLY:
                if(msg.receiver == this)
                    Log.d("Person", "EMT Replied to owner's Email");
                break;
            case HELP_INFECTED_REPLY:

                if(getPursueSB() == null) {
                    // add to combined
                } else {
                    // change the entity the steering behavoir is pointing at
                }

                //ask for help
                break;
            case HELP_BOMB_INFECTED_REPLY:
                if(msg.receiver == this) { //make a functoin of this so we dont duplicate code this is getting messy
                    //walk towards the emt or cop and  evade the position of the blast
                    temp = (entity)msg.sender;
                    setPursueSB((person)temp);
                    Log.d("Person", "EMT Replied to owner's Email");
                }
                break;
            case HELP_INFECTED_REPLY_DENIED:
                //if I didnt make the request just ignore
            case GIVE_PER_LOCATION:
                // go to a location told by athorities
                break;
        }
        return true;
    }

    public boolean callForHelp(int msVal){
        if( (0) == (MessageMsk & (1 <<msVal)) ) { // ask for help every like 10 seconds until help arrives ?
            MessageMsk = (MessageMsk | (1 <<msVal));
            ((tileGameMap) getMap()).getMgMang().dispatchMessage(this, msVal);
            return true;
        } else
            return false;
    }

   // @Override
    public void update(float dTime){

        if(mZombie)
            super.update(dTime);
        else if(classID == classIdEnum.PPerson) //debug player is controlling person
            super.update(dTime);
        else
            processMoves(dTime);
        if(((SystemClock.elapsedRealtime() / 250) % 2) == 1)
            changeImage(true);
        else changeImage(false);
    }

    public void processMoves(float dTime)
    {//virtual function
        //check alertness
        //check if hurt or if infected
        if(!mZombie) {
            if (mInfected) {
                mInfctTime -= .001;
                if (mInfctTime < 0)
                    turnIntoAZombie();
            } else {
                if(mInfctTime >= 1)
                    MessageMsk = 0; // temp for now
                   // MessageMsk = (MessageMsk | (1 <<HELP_INFECTED)); set this bit to 0

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
            case EVADE_ZOMBIE_ARRIVE_INFECTED:
                //combine both steering behavoir
               break ;

            case BOMB_INFECTED:
                callForHelp(HELP_BOMB_INFECTED);
                Log.d("Person", "Infected By bomb I need an Emt !");
                getSteerEnt().setBehavior(blendedSB);
                /*
                if(getSteerEnt().getBehavior() != getEvadeSB()){
                    // make sure to check if we have the evade sb allocated
                    getSteerEnt().setBehavior(getEvadeSB());
                }
                */

            break;

            case INFECTED:
                //if a zombie is too close evade
                callForHelp(HELP_INFECTED);
                Log.d("Person", "I need an Emt !");
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
                // turned into a zombie so act as such.
                break;
            case ARRIVE_ZOMBIE:
                //pursue at a safe distance and attack or shoot
                break;
        }
        super.update(dTime);
    }

    public void turnIntoAZombie() {

        this.mInfctTime = 0;
        this.mInfected = false;
        this.mAlerted = WALK_RANDOMLY;
        this.mZombie = true;
        //this.setClsId(classIdEnum.Zombie);
        this.setClassID(classIdEnum.ConvertedPer);//Debug
        this.setImage("zombie.png");
        this.setImageB("zombie2.png");
        this.setPrey(null);

        //put a status bit here to make sure we only call once
        MessageManager.getInstance().removeListener(this,HELP_ZOMBIE_SPOTTED_REPLY, HELP_INFECTED_REPLY, GIVE_PER_LOCATION,HELP_ZOMBIE_SPOTTED_REPLY, HELP_INFECTED_REPLY_DENIED);

        mMap.getZombies().add(this); // if we multi thread use a semiphore
        mMap.setCnvrtdEntRdy(this);
    }

    public void setInfected(boolean Infected) {
        if(Infected)
            this.mAlerted = INFECTED;
        else
            this.mAlerted = WALK_RANDOMLY;

        this.mInfected = Infected;
    }

    public void setInfected(boolean Infected, zombie zom) {
        this.setEvadeSB(zom);
        this.mInfected = Infected;
        this.mAlerted = INFECTED;
    }

    public void setInfected(boolean Infected, bomb Bomb) {
        if(classID != classIdEnum.Emt){
        } else{
            this.setEvadeSB(Bomb);
        }
        this.mInfected = Infected;
        this.mAlerted = BOMB_INFECTED;
    }

    public Evade<Vector2> getEvadeSB() {
        return evadeSB;
    }

    public void decreaseInfectTime(float InfctTime){ this.mInfctTime -= InfctTime; }

    public void dispose(){ super.dispose(); }

    public entity whoBitMe(){ return this.getPrey(); }

    public boolean isInfected() {
        return mInfected;
    }

    public boolean areYouAZombie() {
        return mZombie;
    }
}

