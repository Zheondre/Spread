package com.mygdx.entities.humans;

import android.util.Log;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.AiStates.MessageType;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.world.gameMap;

import static com.mygdx.AiStates.MessageType.FOLLOW_ME;
import static com.mygdx.AiStates.MessageType.FOLLOW_ME_REPLY;
import static com.mygdx.AiStates.MessageType.GIVE_PER_LOCATION_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_ALLOCATE_MORE_COPS;
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
    private boolean tooManyEnemiesNearBy;
    private boolean  allocateMoreCops;
    private int mags; // assume infinit ammo for now ?

    private boolean shotIsClear;

    //call for back up for cops or security
    // call for emt if some one needs help
    public cop(entityInfo entityType, gameMap map) {
        super(entityType, map);

        if(entityType.getId() == classIdEnum.Cop) {
            this.setImage("cop.png");
            setImage(new Texture("cop.png"));
            setImageRight("cop.png");
            setImageRightWalk("copWalk.png");
            setImageUp("copUp.png");
            setImageUpWalk("copUp2.png");
            setImageLeft("copLeft.png");
            setImageLeftWalk("copLeft2.png");
            setImageDown("copDown.png");
            setImageDownWalk("copDown2.png");
        }

        // mmap has an instance to messagemaneger use that instea
        MessageManager.getInstance().addListeners(this,
                HELP_ZOMBIE_SPOTTED,HELP_ZOMBIE_SPOTTED_REPLY,
                HELP_INFECTED, HELP_INFECTED_REPLY,
                HELP_BACKUP_COPS, HELP_BACKUP_COPS_REPLY,
                FOLLOW_ME, FOLLOW_ME_REPLY,
                GIVE_PER_LOCATION_REPLY
        );
    }

    public void update(float dt){
        //if health too low throw a need help message for emt
        if(getHealth() < .5)
            callForHelp(HELP_COP_NEEDS_EMT);

        //if cops remaining is getting low call for backup
        if(allocateMoreCops)
            callForHelp(HELP_ALLOCATE_MORE_COPS);

        //if more than 3 zombie are near by ask for cop assitance
        if(tooManyEnemiesNearBy)
            callForHelp(HELP_BACKUP_COPS);


        if(iscpu()) {
            //if its safe to shoot, than shoot
            // might have to use raycasting here
            if(shotIsClear)
                attack();
        }

        super.update(dt);
    }

    public boolean handleMessage(Telegram msg){

        entity temp = (entity)msg.sender;

        //what should happen if the person asking for help turns into a zombie ?
        switch(msg.message) {
            case HELP_ZOMBIE_SPOTTED:
                //if close enough find zombie and termniate
                break;
            case HELP_INFECTED:
                // see if close enough to get message if so direct it to the emt
            break;
            case HELP_COP_NEEDS_EMT:
                // cop is infected need an emt
            break;
            case HELP_BACKUP_COPS:
                //getting sorounded or infected is really low call for backup,, else allocate more cops
            break;
            case FOLLOW_ME:
                // follow the commanding cop
            break;
        }
        Log.d("Cop", "Received a request");
        //get needs help message from other classe

        //can call for emt
        //can tell civilian where the emt is
        //can call for back up

        return true;
    }

    public boolean isShotIsClear() {

        if(getPrey() != null) {
            //raycast and see if the closet fixture is that entity.
        }

        return shotIsClear;
    }

    public void setShotIsClear(boolean shotIsClear) {
        this.shotIsClear = shotIsClear;
    }

    @Override
    public boolean attack() {
        return super.attack();
    }

}
