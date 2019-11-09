/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */

package com.mygdx.world;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.person;
import com.mygdx.entities.zombie;
import com.mygdx.entities.entity;

import java.util.ArrayList;

public abstract class gameMap {

    public gameMap(){
    }

    public abstract ArrayList<person> getPeople();
    public abstract ArrayList<zombie> getZombies();

    public abstract void render(SpriteBatch batch);
    public abstract void update(float deltaT);
    public abstract void disposeTileMap();

    public abstract int getPixelWidth();
    public abstract int getPixelHeight();
    public abstract int getMapWidth();
    public abstract int getMapHeight();

    public abstract MapLayers getMapLayers();

    public abstract World getWorld();

}
