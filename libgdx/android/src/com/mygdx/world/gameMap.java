/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */

package com.mygdx.world;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;

public abstract class gameMap {

    public gameMap(){
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update(float deltaT);
    public abstract void disposeTileMap();

    public abstract int getPixelWidth();
    public abstract int getPixelHeight();
    public abstract int getMapWidth();
    public abstract int getMapHeight();

    public abstract MapLayers getMapLayers();

    public abstract boolean doesPersonCollideWithMap(float x, float y, int col, int row);

}
