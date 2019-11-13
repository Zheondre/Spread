package com.mygdx.managers;

//import android.app.Application;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Application;
import com.mygdx.states.About_Screen;
import com.mygdx.states.GameState;
import com.mygdx.states.Main_Menu;
import com.mygdx.states.Play_Screen;
import com.mygdx.states.Settings_Screen;
import com.mygdx.states.Splash_Screen;

import java.util.Stack;

public class GameStateManager {

    private final Application app;
    private Stack<GameState> states;
    public enum State {
        SPLASH,
        MAINMENU,
        SETTINGS,
        ABOUT,
        PLAY
    }

    public GameStateManager(final Application app)
    {
        this.app = app;
        this.states = new Stack<GameState>();
        this.setState(State.SPLASH);
    }

    public Application application()
    {
        return app;
    }


    public void update(float delta)
    {
        states.peek().update(delta);
    }

    public void render(SpriteBatch sb)
    {
        states.peek().render(sb);
    }

    public void dispose()
    {
        for(GameState gs : states)
        {
            gs.dispose();
        }
        states.clear();
    }

    public void resize(int w, int h)
    {
        states.peek().resize(w,h);
    }

    public void setState(State state)
    {
        if(states.size() >= 1) {
            states.pop().dispose();
        }
        states.push(getState(state));
    }

    private GameState getState(State state)
    {
        switch(state)
        {
            case SPLASH: return new Splash_Screen(this);
            case MAINMENU: return new Main_Menu(this);
            case ABOUT: return new About_Screen(this);
            case PLAY: return new Play_Screen(this);
            case SETTINGS: return new Settings_Screen(this);
        }
        return null;
    }
}
