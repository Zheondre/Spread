/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */

package com.mygdx.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.person;
import com.mygdx.entities.player;
import com.mygdx.entities.zombie;


import java.util.ArrayList;

public abstract class gameMap {


    protected ArrayList<entity> people;
   // private player playerOneInstance = player.getInstance();
    private player playerOne;

    public player getPlayerOne() {
        return playerOne;
    }

    public gameMap(){

        people = new ArrayList<entity>();

        playerOne = new player(new zombie(entityInfo.ZPLAYER,this));
        people.add( playerOne.getHost());
        people.add(new person(entityInfo.PERSON,this));

        playerOne.setPeopleRef(people);
    }

    public void render( SpriteBatch batch){
        people.get(0).render(batch);//z player
        people.get(1).render(batch);//person
    }

    public void update(float deltaT){
        playerOne.update(deltaT); // zombie player

        people.get(1).update(deltaT);//person
    }

  //  public abstract int getPixelWidth();
   // public abstract int getPixelHeight();
    public abstract int getMapLayers();
    public abstract int getMapWidth();
    public abstract int getMapHeight();
    public abstract void disposeTileMap();
   // abstract TileType getTileTypeByLocation(int layer, float x , float y);
/*
    public boolean doesPersonCollideWithMap(float x, float y, int col, int row) {
    }

    public boolean doesPersonCollideWithPerson(float x, float y, int w, int h)
    {
        if(x < 0 || y < 0 || ((x + w ) > getPixelWth()) || ((y + h ) > getPixelWth()))
            return true;
    }
*/


}
