package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static java.lang.Math.abs;

public class bombAnimation {
    Texture walkSheet;
    SpriteBatch spriteBatch;
    float stateTime;
    Animation<TextureRegion> walkAnimation;
    int i = 10;


    public bombAnimation()
    {
        walkSheet = new Texture("explosion.png");

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth(),
                walkSheet.getHeight());

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[9];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 9; j++) {
                walkFrames[index++] = tmp[0][0];
            }
        }


        walkAnimation = new Animation<TextureRegion>(0.2f, walkFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    public void render(float x, float y, int radius) {

        if(!walkAnimation.isAnimationFinished(stateTime)) {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, false);



            spriteBatch.begin();
            if (i > 1) i--;

            spriteBatch.draw(currentFrame, x - walkAnimation.getKeyFrameIndex(stateTime) * 20, y - walkAnimation.getKeyFrameIndex(stateTime) * 20, radius / abs((walkAnimation.getKeyFrameIndex(stateTime) - 10) + 1), radius / abs((walkAnimation.getKeyFrameIndex(stateTime) - 10) + 1));

            spriteBatch.end();
        }
    }
}
