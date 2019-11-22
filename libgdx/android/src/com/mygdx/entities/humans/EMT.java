package com.mygdx.entities.humans;

import android.app.Person;
import android.util.Log;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
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
import static com.mygdx.AiStates.MessageType.HELP_COP_NEEDS_EMT;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY_DENIED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED_REPLY;

public class EMT extends person {

    private int healamt;
    private int antiInfect = 5;
    private float healingTime= 1;
    private boolean healing = false;

    public EMT(entityInfo entityType, gameMap map) {

        super(entityType, map);

        if (entityType.getId() == classIdEnum.Emt)
            this.setImage("med.png");

        MessageManager.getInstance().addListeners(this,
                HELP_INFECTED, HELP_INFECTED_REPLY,
                HELP_BACKUP_COPS, HELP_BACKUP_COPS_REPLY,
                FOLLOW_ME, FOLLOW_ME_REPLY,
                GIVE_PER_LOCATION_REPLY
        );
    }

  @Override
    public void dispose(){
        super.dispose();
    }

    public void heal(){
        person temp;
        if(getPrey().getClassID() == classIdEnum.Person) {
           temp = (person)getPrey();
           temp.setInfected(false);
           if(healingTime > 0 ) {
               healing = true;
               if (temp.getInfctTime() < 1)
                   temp.setmInfctTime(temp.getInfctTime() + .2f);
               temp.setHealth(temp.getHealth() + .2f);
               healingTime -= .25;
               healing = false;
           }
        }
    }

    public void healMySelf(){
        setInfected(false);
        if(healingTime > 0 ) {
            healing = true;
            if (getInfctTime() < 1)
                setmInfctTime(getInfctTime() + .25f);
            setHealth(getHealth() + .25f);
            healingTime -= .30;
            healing = false;
        }
    }

    public boolean handleMessage(Telegram msg){
        //zombies for now will now be listening for messages
        //if(areYouAZombie())
        //takeoutlistener
        // i think we should reply with the instance that sent the request so that if multiple instances are listening,
        // that one will know that it's request was denied and the rest can ignore
        entity temp = (entity)msg.extraInfo;
        float distance = getEntDistance(temp);
        //what should happen if the person asking for help turns into a zombie ?
        switch(msg.message) {
            case HELP_ZOMBIE_SPOTTED:
                //if close enough tell authorities
                break;
            case HELP_INFECTED:
                // try to help the infected if it is safe to do so
                if(distance < 100) {
                    ((tileGameMap) getMap()).getMgMang().dispatchMessage(this, temp, HELP_INFECTED_REPLY);
                    setPursueSB((person)temp);
                }
                else {
                    //busy helping some one else so come to me
                    ((tileGameMap) getMap()).getMgMang().dispatchMessage(this, temp, HELP_INFECTED_REPLY_DENIED );
                //busy helping some one else so come to me
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

        if(healing == false)
            healingTime += .15;
    }

    /*
    public void provessMoves();
    */
}
