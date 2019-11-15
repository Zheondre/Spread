package com.mygdx.states;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.entity;
import com.mygdx.game.Application;
import com.mygdx.managers.GameStateManager;
import com.mygdx.entities.humans.player;

import java.util.ArrayList;

public abstract class GameState {


    //References
    protected GameStateManager gsm;
    protected Application app;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    protected ArrayList<entity> people;
    private player playerOne;

    protected GameState(GameStateManager gsm)
    {
        people = new ArrayList<entity>();
        //playerOne = new player(new zombie(entityInfo.ZPLAYER, this));
        //people.add( playerOne.getHost());
        this.gsm = gsm;
        this.app = gsm.application();
        //batch = app.getBatch();
        //camera = app.getCamera();

    }

    public void resize(int w, int h)
    {
        //camera.setToOrtho(false, w, h);
    }

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);
    //public void render(OrthographicCamera camera, SpriteBatch batch);
    public abstract void dispose();
    public abstract void handleInput();
}
