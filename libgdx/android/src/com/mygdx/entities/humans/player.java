package com.mygdx.entities.humans;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.entities.entity;
import java.util.ArrayList;

public class player implements InputProcessor {

    private int points;
    private int infects;
    private int kills;
    private int converts;
    private int myIndex;
    private int tIndex;

    private float screenX;
    private float screenY;

    private float CamXPos;
    private float CamYPos;

    private OrthographicCamera playCam;

    private entity host;
    private entity tempHost = null;

    private ArrayList<person> peopleRef;

    private boolean touchUp;
    private boolean attackButton;
    private boolean bombExploded = false;
    private boolean isAttackPressed = false;

    private Vector3 pos;

    public player(entity host) {
        float wdth = Gdx.graphics.getWidth();
        float hght = Gdx.graphics.getHeight();
        playCam = new OrthographicCamera();
        playCam.setToOrtho(false, wdth - 1300, hght - 800);
        playCam.update();
        this.points = 0;
        this.infects = 0;
        this.kills = 0;
        this.converts = 0;
        Gdx.input.setInputProcessor(this);
        this.host = host;
        this.attackButton = false;
    }

    public player(int points, int infects, int kills, int converts, zombie host) {
        float wdth = Gdx.graphics.getWidth();
        float hght = Gdx.graphics.getHeight();
        playCam = new OrthographicCamera();
        playCam.setToOrtho(false, wdth - 800, hght - 825);
        playCam.update();

        this.points = points;
        this.infects = infects;
        this.kills = kills;
        this.converts = converts;
        this.host = host;
        Gdx.input.setInputProcessor(this);
    }

    public float getCamXPos() {
        return CamXPos;
    }

    public float getCamYPos() {
        return CamYPos;
    }

    public void setAttackPressed(boolean attackPressed) {
        isAttackPressed = attackPressed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points){
        this.points += points;
    }

    public int getInfects() {
        return infects;
    }

    public void setInfects(int infects) {
        this.infects = infects;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getConverts() {
        return converts;
    }

    public void setConverts(int converts) {
        this.converts = converts;
    }

    public entity getHost() {
        return host;
    }

    public void setHost(zombie host) {
        this.host = host;
    }

    public void setPeopleRef(ArrayList<person> peopleRef) {
        this.peopleRef = peopleRef;
    }

    private boolean switchZombie(zombie host) {
        return false; // something got fucked up
    }

    public int ptsMgr(entity ent){
       int ptsHolder = 0;
        switch(ent.getClassID()) {
            case PBomb:
            case Zombie:
            case Person:
            case PZombie:
            case PPerson:
            case ConvertedPer:
                ptsHolder = 3;
                break;
            case Security:
            case Emt:
                ptsHolder = 7;
                break;
            case Cop:
                ptsHolder = 10;
                break;
            case Swat:
            case Medic:
                ptsHolder = 13;
                break;
            case Army:
            case Hazmat:
                ptsHolder = 20;
                break;
        }
        return ptsHolder;
    }

    public void update(float dTime) {
        host.update(dTime);

        if(isAttackPressed)
        {
            //TODO place anamation
            if(host.attack()){
                points += ptsMgr(host);
            }
            isAttackPressed = false;
        }

        checkForSwitch();

        if(host.getBody().getPosition().x < 260) playCam.position.x = 260;
        else playCam.position.x = host.getBody().getPosition().x;

        if(host.getBody().getPosition().y < 120) playCam.position.y = 120;
        else playCam.position.y = host.getBody().getPosition().y;

        CamXPos = playCam.position.x;
        CamYPos = playCam.position.y;

        playCam.update();
    }

    public void setTempHost(entity tempHost) {
        this.tempHost = tempHost;
    }

    public void checkForSwitch(){
        if(tempHost != null) {
            host = tempHost;
            //if bomb instance, dipose of it after the switch
            // we should wait after the bomb animation is done then call dispose
            //tempHost.disPose();
            //tempHost = null;
        }
    }
    public OrthographicCamera getPlayCam() {
        return playCam;
    }

    public void setPlayCam(OrthographicCamera playCam) {
        this.playCam = playCam;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        //Log.d("TouchDown", "Screen Touched In X " + screenX+ " Y "+ screenY);
        // Log.d("Player Position", "X "+host.getPosX()+ " Y "+host.getPosY());
        int entSize;
        touchUp = false;
        Vector3 pos = new Vector3(screenX, screenY, 0);

        playCam.unproject(pos);

        this.screenX = pos.x;
        this.screenY = pos.y;

        float tempy = (float) screenY - host.getPosY();
        float tempx = (float) screenX - host.getPosX();

        // (float) ((Math.atan2()) * 180.0d / Math.PI));

        //float degs = (float) ( (Math.atan2(tempy,tempx)* 180.0d / Math.PI));

        //Log.d("degs", "The angle is " +degs);
        Log.d("TouchDown", "Screen Touched In X " + this.screenX + " Y " + this.screenY);


//Touch Screen Movements - Start
        /*
        if((this.screenX - host.getPosX()) < -20) {
            host.setMoveLeft(true);
        }

        if((this.screenX - host.getPosX())> 20) {
            host.setMoveRight(true);
        }
        //
        if((this.screenY - host.getPosY()) < -20) {
            host.setMoveDown(true);
        }
        if((this.screenY - host.getPosY())> 20) {
            host.setMoveUp(true);
        }*/


//Touch Screen Movements - END


        //if longpressed then we are trying to get the zombie
/*
            if(leftButtonPressed)
               host.setMoveLeft(true);

            if(rightButtonPressed)
                host.setMoveRight(true);

            if(downButtonPressed)
                host.setMoveDown(true);

            if(upButtonPressed)
                host.setMoveUp(true);

            if(LZombButtonPressed){
                myIndex = getPlayerIndex();
                tIndex = myIndex;
                entSize = peopleRef.size();

                tIndex--;

                if(tIndex < 0)
                    tIndex = entSize-1;

                while( (!((person)peopleRef.get(tIndex)).areYouAZombie()) ){
                     if(tIndex - 1 < 0)
                         tIndex = entSize - 1;
                     else
                        tIndex--;
                }
                 tHost = peopleRef.get(tIndex);
            }

            if(RZombButtonPressed){
                myIndex = getPlayerIndex();
                tIndex = myIndex;
                entSize = peopleRef.size();

                tIndex++;

                if(tIndex > entSize - 1)
                    tIndex = 0;

                while( (!((person)peopleRef.get(tIndex)).areYouAZombie()) ){
                    if(tIndex + 1> entSize -1)
                        tIndex = 0;
                    else
                        tIndex++;
                }
                tHost = peopleRef.get(tIndex);
             }

            if(LZombButtonPressed || RZombButtonPressed) {
                 //need to change class id
                 if (myIndex!=tIndex) {
                     ((zombie) host).setCpuStatus(true);
                     host = tHost;
                     host.setCpuStatus(false);
                     myIndex = tIndex;
                 }
            }
*/

        //if(peopleRef.get(myindex).classID == entityInfo.ZOMBIE)
        //playCam.translate(host.getPosX(),host.getPosY());
        return true;
    }

    private int getPlayerIndex() {
        int i;
        for (i = 0; i < peopleRef.size(); i++) ;
        peopleRef.get(i).equals(host);
        return i;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //need to convert the screen coordinates to world coordinates
      /*
        touchUp = true;
        host.setMoveLeft(false);
        host.setMoveRight(false);
        host.setMoveUp(false);
        host.setMoveDown(false);
        host.getBody().setLinearVelocity(getStopVec()); */
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}



