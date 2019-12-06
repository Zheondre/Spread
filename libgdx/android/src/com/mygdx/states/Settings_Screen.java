package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.managers.GameStateManager;
import com.badlogic.gdx.Preferences;

public class Settings_Screen extends GameState {

    float count = 0f;
    private Texture main_menu_button;
    private int HighScore;
    private int infects;
    private int kills;
    private int converts;
    BitmapFont font = new BitmapFont();

    private Preferences prefs;


    public Settings_Screen(GameStateManager gsm)
    {
        super(gsm);
        main_menu_button = new Texture("mainmenuButton.png");
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

    @Override
    public void update(float delta) {

    handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();

        font.getData().setScale(10);
        font.draw(spriteBatch, "GAME STATS", 450, 1050);
        font.draw(spriteBatch, "High Score: " + Integer.toString(HighScore), 450, 500);

        spriteBatch.draw(main_menu_button, Gdx.graphics.getWidth() / 2 - (main_menu_button.getWidth() / 2), Gdx.graphics.getHeight() / 18 - (main_menu_button.getHeight()/2));
        spriteBatch.end();

    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
        {
            Vector2 tmp = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Rectangle textureBounds = new Rectangle(
                    Gdx.graphics.getWidth() / 2 -(main_menu_button.getWidth() / 2),
                    Gdx.graphics.getHeight() - (main_menu_button.getHeight()),
                    main_menu_button.getWidth() * 2,
                    main_menu_button.getHeight() * 2);
            if(textureBounds.contains(tmp.x, tmp.y))
            {
                gsm.setState(GameStateManager.State.MAINMENU);
                dispose();
            }
        }
    }
}
