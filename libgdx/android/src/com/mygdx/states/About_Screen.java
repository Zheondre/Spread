package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.managers.GameStateManager;

public class About_Screen extends GameState {

    private Texture main_menu_button;
    private Texture aboutInfo;
    Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    Texture walkSheet;
    SpriteBatch spriteBatch;
    float stateTime;
    int animationX = -150;


    public About_Screen(GameStateManager gsm)
    {
        super(gsm);
        main_menu_button = new Texture("mainmenuButton.png");
        aboutInfo = new Texture("aboutInfo.png");
        walkSheet = new Texture("zombieAnimationSheet.png");

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / 9,
                walkSheet.getHeight());

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[9];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 9; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }


        walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }


    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClearColor(190/255f,77/255f,227/255f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        if(animationX > Gdx.graphics.getWidth() + 10) animationX = -150;
        animationX += 7;
        spriteBatch.draw(currentFrame, animationX, -32);


        spriteBatch.draw(main_menu_button, Gdx.graphics.getWidth() / 2 - (main_menu_button.getWidth() / 2), Gdx.graphics.getHeight() / 18 - (main_menu_button.getHeight()/2));
        spriteBatch.draw(aboutInfo, Gdx.graphics.getWidth() / 2 - (aboutInfo.getWidth() / 2), Gdx.graphics.getHeight() / 2 - (aboutInfo.getHeight()/2));
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        aboutInfo.dispose();
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
