package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.managers.GameStateManager;

public class Main_Menu extends GameState {

    float count = 0f;
    private Texture settings_button;
    private Texture about_button;
    private Texture level_select_button;
    private Texture spreadLogo;

    public Main_Menu(GameStateManager gsm)
    {
        super(gsm);
        settings_button = new Texture("settingsButton.png");
        about_button = new Texture("aboutButton.png");
        level_select_button = new Texture("levelSelectButton.png");
        spreadLogo = new Texture("spreadLogo.png");

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
        spriteBatch.draw(settings_button, Gdx.graphics.getWidth() / 2 - (settings_button.getWidth() / 2), Gdx.graphics.getHeight() / 6 - (settings_button.getHeight()/2));
        spriteBatch.draw(about_button, Gdx.graphics.getWidth() / 2 - (about_button.getWidth() / 2), Gdx.graphics.getHeight() / 3 - (about_button.getHeight()/2));
        spriteBatch.draw(level_select_button, Gdx.graphics.getWidth() / 2 - (level_select_button.getWidth() / 2), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 2) - (level_select_button.getHeight()/2));
        spriteBatch.draw(spreadLogo, Gdx.graphics.getWidth() / 2 - (spreadLogo.getWidth() / 2), Gdx.graphics.getHeight() - spreadLogo.getHeight() - 50);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
        {
            Vector2 tmp = new Vector2(Gdx.input.getX(),Gdx.input.getY());
            Rectangle textureBounds_settings = new Rectangle(Gdx.graphics.getWidth() / 2 -(settings_button.getWidth() / 2), Gdx.graphics.getHeight() / 2 - (settings_button.getHeight()/2), settings_button.getWidth() * 2, settings_button.getHeight() * 2);
            if(textureBounds_settings.contains(tmp.x, tmp.y))
            {
                gsm.setState(GameStateManager.State.SETTINGS);
                dispose();
            }
            Rectangle textureBounds_about = new Rectangle(Gdx.graphics.getWidth() / 2 - (about_button.getWidth() / 2), (Gdx.graphics.getHeight()) - (Gdx.graphics.getHeight() / 3) - (about_button.getHeight()/2), about_button.getWidth() * 2, about_button.getHeight() * 2);
            if(textureBounds_about.contains(tmp.x, tmp.y))
            {
                gsm.setState(GameStateManager.State.ABOUT);
                dispose();
            }
            Rectangle textureBounds_play = new Rectangle(Gdx.graphics.getWidth() / 2 - (level_select_button.getWidth() / 2), (Gdx.graphics.getHeight() / 3) - (level_select_button.getHeight()/2), level_select_button.getWidth() * 2, level_select_button.getHeight() * 2);
            if(textureBounds_play.contains(tmp.x, tmp.y))
            {
                gsm.setState(GameStateManager.State.PLAY);
                dispose();
            }
        }
    }
}
