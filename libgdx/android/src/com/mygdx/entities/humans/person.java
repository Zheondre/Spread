package com.mygdx.entities.humans;

import android.os.SystemClock;
import android.util.Log;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Hide;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.AiStates.MessageType;
import com.mygdx.entities.BehaviorEnum;
import com.mygdx.entities.Box2dRaycastCollisionDetector;
import com.mygdx.entities.Box2dSteering;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.objects.bomb;
import com.mygdx.entities.objects.gun;
import com.mygdx.utils.SteeringUtils;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

import com.badlogic.gdx.ai.steer.behaviors.RaycastObstacleAvoidance;

import static com.mygdx.AiStates.MessageType.GIVE_PER_LOCATION;
import static com.mygdx.AiStates.MessageType.HELP_BOMB_INFECTED;
import static com.mygdx.AiStates.MessageType.HELP_BOMB_INFECTED_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY;
import static com.mygdx.AiStates.MessageType.HELP_INFECTED_REPLY_DENIED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED;
import static com.mygdx.AiStates.MessageType.HELP_ZOMBIE_SPOTTED_REPLY;
import static com.mygdx.entities.BehaviorEnum.BOMB_INFECTED;
import static com.mygdx.entities.BehaviorEnum.EVADE_ZOMBIE;
import static com.mygdx.entities.BehaviorEnum.INFECTED;
import static com.mygdx.entities.BehaviorEnum.NEW_ZOMBIE;
import static com.mygdx.entities.BehaviorEnum.PURSUE_ZOMBIE;
import static com.mygdx.entities.BehaviorEnum.WALK_RANDOMLY;
import com.mygdx.utils.bombAnimation;


public class person extends zombie {

    private boolean mInfected;
    private boolean mZombie;
    protected entity selectedWeapon;

    protected int MessageMsk = 0; // reminder only 32 messages can be placed in the mask
    protected Evade<Vector2> evadeSB;
    protected Flee<Vector2>  fleeSB;
    protected Hide<Vector2>  hideSB;
    protected RaycastObstacleAvoidance<Vector2> raycastObstacleAvoidanceSB;
    protected Box2dRaycastCollisionDetector raycastCollisionDetector;

    public boolean firstVictim = false;
    public bombAnimation bombDrop = new bombAnimation();

    boolean walk = true;

    private float wlkTime;
    private int wlkDirection;

    protected float helpDuration = 1;

    public void setEvadeSB(zombie target) {
        if(target!= null) {
            if(this.evadeSB == null) {
                //this.evadeSB = new Evade<>(this.steerEnt,target.getSteerEnt(), .5f);
                this.evadeSB = new Evade<>(this.steerEnt, target.getSteerEnt());
                combinedSB.add(this.evadeSB);
            } else{
                this.evadeSB.setTarget(target.getSteerEnt());
            }
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
             } else {
                this.evadeSB.setTarget(target.getSteerEnt());
            }
            this.setPrey(target);// temp
            evadeSB.setEnabled(true);
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
        }

        /////https://github.com/libgdx/gdx-ai/blob/master/tests/src/com/badlogic/gdx/ai/tests/steer/box2d/tests/Box2dRaycastObstacleAvoidanceTest.java
        RayConfigurationBase<Vector2> rayConfiguration = new CentralRayWithWhiskersConfiguration<Vector2>(steerEnt, SteeringUtils.pixelsToMeters(1700),
                    SteeringUtils.pixelsToMeters(1400), 22.5f * MathUtils.degreesToRadians);
        raycastCollisionDetector = new Box2dRaycastCollisionDetector((tileGameMap)mMap);
        raycastObstacleAvoidanceSB = new RaycastObstacleAvoidance<Vector2>(steerEnt, rayConfiguration,
                raycastCollisionDetector, SteeringUtils.pixelsToMeters(1700));
        combinedSB.add(raycastObstacleAvoidanceSB);
        /////////////////////////////
        this.mInfctTime = 1;

        this.weapon = entityType.getWeapon();

        // need to support sords but just gonna put guns for now if we choose this
        if(weapon != classIdEnum.NOWEAPON)
            selectedWeapon = new gun(classIdEnum.PISTOL, this);

        this.mZombie =  entityType.isZombie();
        this.mInfected = entityType.isInfected();
        this.mAlerted = BehaviorEnum.WALK_RANDOMLY;
        this.wlkDirection = 0;
        this.wlkTime = -1;
    }

    public boolean handleMessage(Telegram msg){
        entity temp;
// TODO need to clear help messages when help has arrived
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
        } else {
            //helpDuration-= .01;
            //if(helpDuration < 0)

        }
            return false;
    }
	
    public boolean attack(){
        if(selectedWeapon != null)
            selectedWeapon.attack(); //TODO update this

        if(areYouAZombie())
           return super.attack();

        return false;
    }
   // @Override
    public void update(float dTime){

        if(mZombie)
            super.update(dTime);
        else if(classID == classIdEnum.PPerson) //debug player is controlling person
            super.update(dTime);
        else {
            processMoves(dTime);
        }
        //breakpoint

        // i think the ray cast gets the closest entity..
        if(raycastCollisionDetector.getCallback().getFoundEnts().size() > 0)
            raycastCollisionDetector.getCallback().getFoundEnts().clear();

        steerEnt.update(dTime);

        zombie tEnt;
        if(raycastCollisionDetector.getCallback().getFoundEnts().size() > 0) {
            tEnt = (zombie)raycastCollisionDetector.getCallback().getFoundEnts().get(0);

            switch(classID) {
                case Emt:
                    if(getPursueSB() != null) {
                        // if the person we asked for helped turned into a zombie
                        if(getPursueSB().getTarget().equals(tEnt.getSteerEnt())) {
                            getPursueSB().setEnabled(false);
                        }
                    }
                case Person:
                    if(weapon == classIdEnum.NOWEAPON) {
                        mAlerted = EVADE_ZOMBIE;
                        setEvadeSB(tEnt);
                    } else {

                    }
                    break;
                case Cop:
                    mAlerted = PURSUE_ZOMBIE;
                    setPursueSB((person)tEnt);
            }

            raycastCollisionDetector.getCallback().getFoundEnts().clear();
        }


        /*if(((SystemClock.elapsedRealtime() / 250) % 2) == 1)
            changeImage(true, 2);
        else changeImage(false, 1);*/

        if(this.isMoveRight()) {
            if (((SystemClock.elapsedRealtime() / 250) % 2) == 1)
                changeImage(true, 1);
            else changeImage(false, 1);
        }
        else if(this.isMoveUp()) {
            if (((SystemClock.elapsedRealtime() / 250) % 2) == 1)
                changeImage(true, 2);
            else changeImage(false, 2);
        }
        else if(this.isMoveLeft()) {
            if (((SystemClock.elapsedRealtime() / 250) % 2) == 1)
                changeImage(true, 3);
            else changeImage(false, 3);
        }
        else if(this.isMoveDown()) {
            if (((SystemClock.elapsedRealtime() / 250) % 2) == 1)
                changeImage(true, 4);
            else changeImage(false, 4);
        }
        else changeImage(true, 1);

        if(firstVictim)
        {
            bombDrop.render(100, 100, 2000);
        }
    }

    public void processMoves(float dTime)
    {
        //check alertness
        //check if hurt or if infected
        if(!mZombie) {
            if (mInfected) {
                mInfctTime -= .001;
                if (mInfctTime < 0)
                    turnIntoAZombie();
            }
        } else {
			if(mInfctTime >= 1) {
			    int tMsk = ~((1 << HELP_INFECTED)|(1 << HELP_BOMB_INFECTED));
                MessageMsk = (MessageMsk & tMsk);
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
                break;
            case EVADE_ZOMBIE:
                callForHelp(MessageType.HELP_ZOMBIE_SPOTTED);
            break;
            case EVADE_ZOMBIE_ARRIVE_INFECTED:
                //combine both steering behavoir
                //callForHelp(MessageType.HELP_ZOMBIE_SPOTTED);
               break ;

            case BOMB_INFECTED:
                callForHelp(HELP_BOMB_INFECTED);
                Log.d("Person", "Infected By bomb I need an Emt !");
            break;

            case INFECTED:
                callForHelp(HELP_INFECTED);
                Log.d("Person", "I need an Emt !");
            // ask to be helped or go to an emt
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
        this.setImageRight("zombie.png");
        this.setImageRightWalk("zombie2.png");
        this.setImageUp("zombieUp.png");
        this.setImageUpWalk("zombieUp2.png");
        this.setImageLeft("zombieLeft.png");
        this.setImageLeftWalk("zombieLeft2.png");
        this.setImageDown("zombieDown.png");
        this.setImageDownWalk("zombieDown2.png");
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

