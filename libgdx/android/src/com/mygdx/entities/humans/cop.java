package com.mygdx.entities.humans;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.world.gameMap;

import static com.mygdx.AiStates.MessageType.FOLLOW_ME;
import static com.mygdx.AiStates.MessageType.FOLLOW_ME_REPLY;
import static com.mygdx.AiStates.MessageType.GIVE_PER_LOCATION_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_ARMY;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_COPS;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_COPS_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_COP_NEEDS_EMT;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED_REPLY;

public class cop extends security {

    private byte donuts;
    private entity mesReq;
    //call for back up for cops or security
    // call for emt if some one needs help
    public cop(entityInfo entityType, gameMap map) {
        super(entityType, map);

        if(entityType.getId() == classIdEnum.Cop)
            this.setImage("cop.png");

        // mmap has an instance to messagemaneger use that instea
        MessageManager.getInstance().addListeners(this,
                HELP_ZOMBIE_SPOTTED,HELP_ZOMBIE_SPOTTED_REPLY,
                HELP_INFECTED, HELP_INFECTED_REPLY,
                HELP_BACKUP_COPS, HELP_BACKUP_COPS_REPLY,
                FOLLOW_ME, FOLLOW_ME_REPLY,
                GIVE_PER_LOCATION_REPLY);

    }

    public void update(float dt){
        //if health too low throw a need help message for emt
        //if cops remaining is getting low call for backup
    }

    @Override
    public boolean attack() {

        if(iscpu()) {

        } else {

            //if its safe to shoot, than shoot
            // might have to use raycasting here
        }

        //return super.attack();
        return true;
    }

    public boolean handleMessage(Telegram msg){
        //zombies for now will now be listening for messages
        //if(areYouAZombie())
            //takeoutlistener

        //what should happen if the person asking for help turns into a zombie ?
        switch(msg.message) {
            case HELP_ZOMBIE_SPOTTED:
                break;
            case HELP_INFECTED:
            break;
            case HELP_COP_NEEDS_EMT:
            break;
            case HELP_BACKUP_COPS:
            break;
            case HELP_BACKUP_ARMY:
            break;
            case FOLLOW_ME:
            break;
        }

        //get needs help message from other classe

        //can call for emt
        //can tell civilian where the emt is
        //can call for back up

        return true;
    }
    public void callforBackUp(){


    }
}
