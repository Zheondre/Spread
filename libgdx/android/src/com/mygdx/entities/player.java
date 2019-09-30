package com.mygdx.entities;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class player implements InputProcessor {

    private int points;
    private int infects;
    private int kills;
    private int converts;

    private float screenX;
    private float screenY;

    private OrthographicCamera playCam;

    private entity host;

    private ArrayList<entity> peopleRef;

    private boolean switchZombie(entity host){
        return  false ; // something got fucked up
    }

    //private static final player ourInstance = new player();
/*
    public static player getInstance() {
        return ourInstance;
    }*/

    public player(entity host) {
        float wdth = Gdx.graphics.getWidth();
        float hght = Gdx.graphics.getHeight();
        playCam = new OrthographicCamera();
        playCam.setToOrtho(false, wdth - 800, hght - 800);
        playCam.update();

        this.points = 0;
        this.infects = 0;
        this.kills = 0;
        this.converts = 0;
        Gdx.input.setInputProcessor(this);
        this.host = host;
    }

    public player(int points, int infects, int kills, int converts, entity host) {

        float wdth = Gdx.graphics.getWidth();
        float hght = Gdx.graphics.getHeight();
        playCam = new OrthographicCamera();
        playCam.setToOrtho(false, wdth - 800, hght - 800);
        playCam.update();

        this.points = points;
        this.infects = infects;
        this.kills = kills;
        this.converts = converts;
        this.host = host;
        Gdx.input.setInputProcessor(this);

    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public void setHost(entity host) {
        this.host = host;
    }


    public void update(float dTime){
        host.update(dTime);
        playCam.update();
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

        //Gdx.input.
        //Log.d("TouchDown", "Screen Touched In X " + screenX+ " Y "+ screenY);
     // Log.d("Player Position", "X "+host.getPosX()+ " Y "+host.getPosY());
        //basic player movement;

        Vector3 pos = new Vector3(screenX, screenY, 0);

        playCam.unproject(pos);

        this.screenX = pos.x;
        this.screenY = pos.y;

        float tempy = (float)screenY - host.getPosY();
        float tempx =(float)screenX-host.getPosX();


       // (float) ((Math.atan2()) * 180.0d / Math.PI));

        //float degs = (float) ( (Math.atan2(tempy,tempx)* 180.0d / Math.PI));

        //Log.d("degs", "The angle is " +degs);
        Log.d("TouchDown", "Screen Touched In X " + this.screenX+ " Y "+ this.screenY);
        if((this.screenX - host.getPosX()) < -25)
           host.setMoveLeft(true);

        if((this.screenX - host.getPosX())> 25)
            host.setMoveRight(true);
        //*/
        if((this.screenY - host.getPosY()) < -25)
            host.setMoveDown(true);

        if((this.screenY - host.getPosY())> 25)
            host.setMoveUp(true);

        //playCam.translate(host.getPosX(),host.getPosY());
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //need to convert the screen coordinates to world coordinates

        host.setMoveLeft(false);
        host.setMoveRight(false);
        host.setMoveUp(false);
        host.setMoveDown(false);

        /*
        if((this.screenX - host.getPosX()) < 0)
            host.setMoveLeft(false);

        if((this.screenX - host.getPosX())> 0)
            host.setMoveRight(false);

        if((this.screenY - host.getPosY()) < 0)
            host.setMoveUp(false);

        if((this.screenY - host.getPosY())> 0)
            host.setMoveDown(false);
*/
        //playCam.translate(host.getPosX(),host.getPosY());
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
