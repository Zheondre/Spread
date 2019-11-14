package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.managers.GameStateManager;

public class Splash_Screen extends GameState {

    float count = 0f;
    private Texture play_button;
    private Texture spreadLogo;


    public Splash_Screen(GameStateManager gsm)
    {
        super(gsm);
        play_button = new Texture("playButton.png");
        spreadLogo = new Texture("spreadLogo.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
        {
            Vector2 tmp = new Vector2(Gdx.input.getX(),Gdx.input.getY());
            Rectangle textureBounds = new Rectangle(Gdx.graphics.getWidth() / 2 -(play_button.getWidth() / 2), Gdx.graphics.getHeight() / 2 - (play_button.getHeight()/2), play_button.getWidth() * 2, play_button.getHeight() * 2);
            if(textureBounds.contains(tmp.x, tmp.y))
            {
                gsm.setState(GameStateManager.State.MAINMENU);
                dispose();
            }
        }
    }


    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(play_button, Gdx.graphics.getWidth() / 2 - (play_button.getWidth() / 2), Gdx.graphics.getHeight() / 2 - (play_button.getHeight()/2) - 250);
        spriteBatch.draw(spreadLogo, Gdx.graphics.getWidth() / 2 - (spreadLogo.getWidth() / 2), Gdx.graphics.getHeight() - spreadLogo.getHeight() - 50);
        spriteBatch.end();

    }

    @Override
    public void dispose() {

    }


}
