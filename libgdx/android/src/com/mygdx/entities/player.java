package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class player implements InputProcessor {

    private int points;
    private int infects;
    private int kills;
    private int converts;

    private entity host;

    private boolean switchZombie(entity host){
        return  false ; // something got fucked up
    }

    //private static final player ourInstance = new player();
/*
    public static player getInstance() {
        return ourInstance;
    }*/

    public player(entity host) {
        this.points = 0;
        this.infects = 0;
        this.kills = 0;
        this.converts = 0;
        Gdx.input.setInputProcessor(this);
        this.host = host;
    }

    public player(int points, int infects, int kills, int converts, entity host) {
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
        //basic player movement;
        if((screenX - host.getPosX()) < 0) {
           host.setMoveLeft(true);
        } else{
            host.setMoveLeft(false);
        }
        if((screenX - host.getPosX())> 0) {
            host.setMoveRight(true);
        }else {
            host.setMoveRight(false);
        }
        if((screenY - host.getPosY()) < 0) {
            host.setMoveUp(true);
        } else{
            host.setMoveUp(false);
        }
        if((screenY - host.getPosY())> 0) {
            host.setMoveDown(true);
        }else {
            host.setMoveDown(false);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
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
