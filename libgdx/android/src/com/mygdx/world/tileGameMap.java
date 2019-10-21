/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */


package com.mygdx.world;

import android.util.Log;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.person;
import com.mygdx.entities.player;
import com.mygdx.entities.zombie;

import java.util.ArrayList;

public class tileGameMap extends gameMap {

    private TiledMap m_TileMap;
    private OrthogonalTiledMapRenderer m_TileMapRender;
    private int mapWidth;
    private int mapHeight;

    private int tileWidth;
    private int tileHeight;

    protected ArrayList<entity> people;
    protected ArrayList<zombie> zombies;

    private player playerOne;

    public tileGameMap() {

        m_TileMap = new TmxMapLoader().load("house_road.tmx"); // we will have to make this dynamic based on user map selection
        m_TileMapRender = new OrthogonalTiledMapRenderer(m_TileMap);
        MapProperties MapProp = m_TileMap.getProperties();

        mapWidth =  MapProp.get("width", Integer.class)* MapProp.get("tilewidth", Integer.class);
        mapHeight =  MapProp.get("height", Integer.class)* MapProp.get("tileheight", Integer.class);

        people = new ArrayList<entity>();

        playerOne = new player(new zombie(entityInfo.ZPLAYER,this));
        people.add(playerOne.getHost());
        people.add(new person(entityInfo.PERSON,this));

        playerOne.setPeopleRef(people);

        //m_TileMap.getLayers()
    }

    @Override
    public void render(SpriteBatch batch){
       m_TileMapRender.setView(this.playerOne.getPlayCam());
       m_TileMapRender.render();

       batch.setProjectionMatrix(this.playerOne.getPlayCam().combined);
       batch.begin();

       people.get(0).render(batch);//z player
        // people.get(1).render(batch);//person
       batch.end();
    }

    @Override
    public void update(float deltaT){ //update what the method name should say what we are updating
        playerOne.update(deltaT); // zombie player
        //people.get(1).update(deltaT);//person
    }

    @Override
    public void disposeTileMap(){
        m_TileMap.dispose();
    }

    @Override
    public int getMapWidth(){
       return mapWidth; }

    @Override
    public int getMapHeight(){ return mapHeight; }

    @Override
    public MapLayers getMapLayers(){ return m_TileMap.getLayers(); }

    public int getPixelWidth(){ return 0; }

    public int getPixelHeight(){ return 0; }

    public player getPlayerOne() {
        return playerOne;
    }

    public boolean doesPersonCollideWithMap(float x, float y, int w, int h)
    {
        //if(x < 0 || y < 0 || ((x + w ) > getPixelWth()) || ((y + h ) > getPixelWth()))
            return false;
    }


}
