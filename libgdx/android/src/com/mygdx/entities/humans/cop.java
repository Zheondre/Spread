package com.mygdx.entities.humans;

import android.util.Log;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.mygdx.AiStates.MessageType;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.utils.SteeringUtils;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

import static com.mygdx.AiStates.MessageType.FOLLOW_ME;
import static com.mygdx.AiStates.MessageType.FOLLOW_ME_REPLY;
import static com.mygdx.AiStates.MessageType.GIVE_PER_LOCATION_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_ALLOCATE_MORE_COPS;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_ARMY;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_COPS;
import static com.mygdx.AiStates.MessageType.HELP_BACKUP_COPS_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_COP_NEEDS_EMT;
import static com.mygdx.AiStates.MessageType.HELP_EMT_NEEDS_COP;
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
        if(!areYouAZombie()) {
            if (getHealth() < .5)
                callForHelp(HELP_COP_NEEDS_EMT);

            // let gae manager handle
            //if cops remaining is getting low call for backup
            if (allocateMoreCops)
                callForHelp(HELP_ALLOCATE_MORE_COPS);

            //if more than 3 zombie are near by ask for cop assitance
            if (tooManyEnemiesNearBy)
                callForHelp(HELP_BACKUP_COPS);

            float tEndDis;
            if ((getPursueSB() != null) && (getPrey() != null) ) {
                if (getPursueSB().isEnabled()) {
                    tEndDis = getEntDistance(getPrey());
                    if (35 > tEndDis) {
                        stopMovingEnt();
                        if(getPrey().getClassID() == classIdEnum.PBomb)
                            ;//getPursueSB().setEnabled(false);
                    }
                }
            }
            if(iscpu()) {
                if(aim())
                    attack();
            }
        }
        super.update(dt);
    }

    public boolean handleMessage(Telegram msg){

        entity temp = (entity)msg.sender;

        //what should happen if the person asking for help turns into a zombie ?
        switch(msg.message) {
            case HELP_ZOMBIE_SPOTTED:
                // TODO pass zomb spot location

                // we can go to the person who needs help to provide cover

            //pursueSB.
                // or we can follow the emt that accepted the request ?

                //if close enough find zombie and termniate
                break;
            case HELP_INFECTED:
                // see if close enough to get message if so direct it to the emt
            break;

            case HELP_EMT_NEEDS_COP:
                break ;

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

    boolean aim(){
       // boolean t = false;
        if(getPrey() != null) {
            if(getPrey().getClassID() == classIdEnum.ConvertedPer ||getPrey().getClassID() == classIdEnum.PZombie ) {
                if(((zombie)getPrey()).getHealth() < 0) {
                    getPursueSB().setEnabled(false);
                    setPrey(null);// might crash
                    return false;
                }
                //getBody().getWorld().rayCast(raycastCollisionDetector.getCallback(), this.mPos.x, this.mPos.y,
                 //       getPrey().getPos().x, getPrey().getPos().y);
            return true;
            }
        }
        return false;
    }

    @Override
    public boolean attack() {
        return super.attack();
    }

}
