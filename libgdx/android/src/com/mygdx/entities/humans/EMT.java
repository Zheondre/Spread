package com.mygdx.entities.humans;

import android.app.Person;
import android.util.Log;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

import static com.mygdx.AiStates.MessageType.FOLLOW_ME;
import static com.mygdx.AiStates.MessageType.FOLLOW_ME_REPLY;
import static com.mygdx.AiStates.MessageType.GIVE_PER_LOCATION_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_ARMY;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_COPS;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_COPS_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_BOMB_INFECTED;
import static com.mygdx.AiStates.MessageType.HELP_COP_NEEDS_EMT;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY_DENIED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED_REPLY;
import static com.mygdx.AiStates.MessageType.NO_HELP_NEEDED;
import static com.mygdx.entities.BehaviorEnum.ARRIVE_BOMB_INFECTED;
import static com.mygdx.entities.BehaviorEnum.ARRIVE_INFECTED;
import static com.mygdx.entities.BehaviorEnum.EVADE_ZOMBIE;
import static com.mygdx.entities.BehaviorEnum.EVADE_ZOMBIE_ARRIVE_INFECTED;
import static com.mygdx.entities.BehaviorEnum.WALK_RANDOMLY;

public class EMT extends person {

    private int healamt;
    private int infecTime = 1;
    private float healingTime = 1;
    private boolean healing = false;
    private boolean busy = false;

    public EMT(entityInfo entityType, gameMap map) {
        super(entityType, map);
        if (entityType.getId() == classIdEnum.Emt)
            this.setImage("emt.png");
        MessageManager.getInstance().addListeners(this,
                HELP_INFECTED, HELP_BOMB_INFECTED, HELP_INFECTED_REPLY,
                HELP_BACKUP_COPS, HELP_BACKUP_COPS_REPLY,
                FOLLOW_ME, FOLLOW_ME_REPLY,
                GIVE_PER_LOCATION_REPLY
        );
    }

    public void heal(){
        person temp;
        temp = (person) getPrey();
        if(temp != null) {
            if (temp.getClassID() == classIdEnum.Person) {
                temp.setInfected(false);
                if (healingTime > 0) {
                    healing = true;
                    busy = healing;
                    if (temp.getmInfctTime() < 1)
                        temp.setmInfctTime(temp.getmInfctTime() + .2f);
                    if (temp.getHealth() < 1)
                        temp.setHealth(temp.getHealth() + .2f);
                    healingTime -= .25;
                    healing = false;
                    busy = healing;
                }
            }
        }
    }

    public void healMySelf(){
        setInfected(false);
        if(healingTime > 0 ) {
            healing = true;
            if (getmInfctTime() < 1)
                setmInfctTime(getmInfctTime() + .3f);
            if(getHealth() <1.5f)
                setHealth(getHealth() + .3f);
            healingTime -= .25;
            healing = false;
        }
    }

    public boolean handleMessage(Telegram msg){
        //zombies for now will now be listening for messages
        //if(areYouAZombie())
        //takeoutlistener
        // i think we should reply with the instance that sent the request so that if multiple instances are listening,
        // that one will know that it's request was denied and the rest can ignore
        person temp = (person) msg.sender;

        float distance = getEntDistance(temp);
        //what should happen if the person asking for help turns into a zombie ?
        //if we are close enough and we seen that person changed into a zombie evade
        //only change the evad steering behavoir if zombie is closer than the reported zombie

        switch(msg.message) {
            case NO_HELP_NEEDED:
                break;
           // case HELP_BOMB_EXPLODED;
            //break;
            case HELP_ZOMBIE_SPOTTED:
                //watch out for that zombie

                zombie ztemp = (zombie) temp.getPrey();

                if(getPrey() != ztemp) {
                    // check to see which one is closer
                    setEvadeSB(ztemp);
                    combinedSB.add(getEvadeSB());
                }

                mAlerted = EVADE_ZOMBIE;
                break;
            case HELP_INFECTED:
                // if there are armed people around the emt
                // tell infected to come towards my location
                // a group will head to the infected'slocation
                //if a zombie is near have an armed person shoot
            case HELP_BOMB_INFECTED:
                // try to help the infected if it is safe to do so
                //figure out ot
                mAlerted = ARRIVE_BOMB_INFECTED;
                if(!busy) {
                    ((tileGameMap) getMap()).getMgMang().dispatchMessage(this, temp, HELP_INFECTED_REPLY);
                    if(temp != getPrey()) {
                        setPursueSB(temp);
                        //crashing here
                        combinedSB.add(getPursueSB());
                    }
                    ((tileGameMap) getMap()).getMgMang().dispatchMessage(this, temp, HELP_INFECTED_REPLY);
                }
                else {
                    //busy helping some one else
                    ((tileGameMap) getMap()).getMgMang().dispatchMessage(this, temp, HELP_INFECTED_REPLY_DENIED);
                }
                break;
            case HELP_COP_NEEDS_EMT:
                // help the cop out
                break;
            case HELP_BACKUP_COPS:
                //getting sorounded or infected is really low call for backup,, else allocate more cop
                break;
            case FOLLOW_ME:
                // follow the commanding cop
                break;

        }
        Log.d("MED", "Received a request");
        return true;
    }

    public void update(float dt){

        // when should the emt heal its self ?
        if((getHealth() < 1f)||(getmInfctTime() < 1f))
            healMySelf();

        if(isInfected()) {
            infecTime-=.05;
            if(infecTime < 0) {
                setInfected(false);
                infecTime = 1;
            }
        }

        if((mAlerted == EVADE_ZOMBIE) || (mAlerted == ARRIVE_INFECTED) ||(mAlerted == ARRIVE_BOMB_INFECTED)) {
            steerEnt.setBehavior(combinedSB);
            steerEnt.update(dt);
            if(getPrey() != null) {
               if(18 > getEntDistance()) {
                    heal();
               }
               try {
                   // might crash here
                   if ( ((person)getPrey()).getmInfctTime() > 1) {
                      // mAlerted = EVADE_ZOMBIE;
                       getPursueSB().setEnabled(false);
                       //cant delete a steering behavoir on combined only on blended
                       setPrey(null);
                       //if no enemies around
                       mAlerted = WALK_RANDOMLY;
                   }

               } catch(NullPointerException ex) {}
            }
        }
        else
            super.update(dt);

        if(healing == false)
            if(healingTime < 1)
                healingTime += .25;

        //if we view a zombie closer then the one we have set avoid that one
    }

    /*
    public void provessMoves();
    */

    @Override
    public void dispose(){
        super.dispose();
    }
}
