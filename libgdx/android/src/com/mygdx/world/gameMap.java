/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */

package com.mygdx.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class gameMap {

    public void render(OrthographicCamera camera, SpriteBatch batch){
    }

    public void update(float deltaTime){
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();
    //public int getPixelWth();
    //public int getPixelWth();

/*
    public boolean doesPersonCollideWithMap(float x, float y, int col, int row) {
    }

    public boolean doesPersonCollideWithPerson(float x, float y, int w, int h)
    {
        if(x < 0 || y < 0 || ((x + w ) > getPixelWth()) || ((y + h ) > getPixelWth()))
            return true;
    }
*/
    public abstract void disposeTileMap();

    //abstract TileType()

}
