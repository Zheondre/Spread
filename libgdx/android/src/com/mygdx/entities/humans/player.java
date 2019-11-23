package com.mygdx.entities.humans;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.objects.bomb;

import java.util.ArrayList;

public class player implements InputProcessor {

    private int points;
    private int HighScore;
    private int infects;
    private int kills;
    private int converts;
    private int myIndex;
    private int tIndex;

    private float saveTime = 1;
    private float screenX;
    private float screenY;

    private float CamXPos;
    private float CamYPos;

    private OrthographicCamera playCam;

    private entity host;
    private entity tempHost = null;

    private bomb bombRef;

    private ArrayList<zombie> ZombieRef;

    private boolean touchUp;
    private boolean attackButton;
    private boolean bombExploded = false;
    private boolean isAttackPressed = false;
    private boolean leftCyclePressed = false;
    private boolean rightCyclePressed = false;

    private Vector3 pos;

    private Preferences prefs;

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

        if(host.getClassID() == classIdEnum.PBomb)
            this.bombRef = (bomb)host;

        this.host = host;
        this.attackButton = false;

        prefs = Gdx.app.getPreferences("Spread_Stats_FILE");

        if (!prefs.contains("converts")) {
            prefs.putInteger("converts", 0);
            converts = 0;
        }

        if (prefs.contains("highScore")) {
            HighScore = prefs.getInteger("highScore");
        } else {
            prefs.putInteger("highScore", 0);
            HighScore = 0;
        }
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

    public int getHighScore() {
        return HighScore;
    }

    public void setBombRef(bomb bombRef) {
        this.bombRef = bombRef;
    }

    public bomb getBombRef() {
        return bombRef;
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

    public void setZombieRef(ArrayList<zombie> ZombieRef) {
        this.ZombieRef = ZombieRef;
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
            if(host.getClassID() == classIdEnum.PBomb)
                bombExploded = true;
            //TODO place anamation
            if(host.attack()){
                points += ptsMgr(host);
            }
            isAttackPressed = false;
        }

        int entSize;

        zombie tHost = null;
        if(leftCyclePressed){
            myIndex = getZombieIndex();
            tIndex = myIndex;
            entSize = ZombieRef.size();

            tIndex--;

            if(tIndex < 0)
                tIndex = entSize-1;

            while( (!((person)ZombieRef.get(tIndex)).areYouAZombie()) ){
                if(tIndex - 1 < 0)
                    tIndex = entSize - 1;
                else
                    tIndex--;
            }
            tHost = ZombieRef.get(tIndex);
        }

        if(rightCyclePressed){
            myIndex = getZombieIndex();
            tIndex = myIndex;
            entSize = ZombieRef.size();

            tIndex++;

            if(tIndex > entSize - 1)
                tIndex = 0;

            while( (!((person)ZombieRef.get(tIndex)).areYouAZombie()) ){
                if(tIndex + 1> entSize -1)
                    tIndex = 0;
                else
                    tIndex++;
            }
            tHost = ZombieRef.get(tIndex);
        }

        if(leftCyclePressed || rightCyclePressed) {
            //need to change class id
            if (myIndex!=tIndex) {
                if(tHost != null) {
                    ((zombie) host).setClassID(classIdEnum.ConvertedPer);
                    ((zombie) host).setCpuStatus(true);
                    host = tHost;
                    ((zombie) host).setCpuStatus(false);

                    myIndex = tIndex;
                }
            }
        }

        if(bombExploded) {
            //TODO if no one turns into a zombie after an exposion after 30 seconds you failed the game try again
            //TODO hide bomb or showing explosion animation
            if(host.getClassID() != classIdEnum.ConvertedPer)
                if(host.getMap().getZombies().size() > 0) {

                    host = host.getMap().getZombies().get(0);
                    ((zombie)host).setCpuStatus(false);
                    host.setClassID(classIdEnum.PZombie);
                    bombExploded = false;
                }
        }

        if(host.getBody().getPosition().x < 260) playCam.position.x = 260;
        else playCam.position.x = host.getBody().getPosition().x;

        if(host.getBody().getPosition().y < 120) playCam.position.y = 120;
        else playCam.position.y = host.getBody().getPosition().y;

        CamXPos = playCam.position.x;
        CamYPos = playCam.position.y;

        playCam.update();

        saveTime-= .0001;
        if (saveTime < 0) {
            saveTime = 1;
            /*
            totalkills += current kills
            totalplaytime += playtime;
             */
            int tempSize = host.getMap().getZombies().size();

            if(converts < tempSize) {
                converts = tempSize;
                tempSize = prefs.getInteger("converts");
                tempSize+= converts;
                prefs.putInteger("converts", converts);
            }

            if(HighScore < points) {
                HighScore = points;
                prefs.putInteger("highScore", HighScore);
                prefs.flush();
            }
        }
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

        //Log.d("TouchDown", "Screen Touched In X " + this.screenX + " Y " + this.screenY);
        //Touch Screen code was taken out 11.23.19 use this date as a reference to find the commmit : ZC
        //if longpressed then we are trying to get the zombie
        return true;
    }

    private int getZombieIndex() {
        int i;
        for (i = 0; i < ZombieRef.size(); i++) ;
        ZombieRef.get(i).equals(host);
        return i;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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



