/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */


package com.mygdx.world;

import android.util.Log;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class tileGameMap extends gameMap {

    private TiledMap m_TileMap;
    private OrthogonalTiledMapRenderer m_TileMapRender;
    private int mapWidth;
    private int mapHeight;
   // private int tileWidth;
    //private int tileHeight;


    public tileGameMap() {

        m_TileMap = new TmxMapLoader().load("house_road.tmx"); // we will have to make this dynamic based on user map selection
        m_TileMapRender = new OrthogonalTiledMapRenderer(m_TileMap);
        MapProperties MapProp = m_TileMap.getProperties();

        mapWidth =  MapProp.get("width", Integer.class)* MapProp.get("tilewidth", Integer.class);
        mapHeight =  MapProp.get("height", Integer.class)* MapProp.get("tileheight", Integer.class);
    }

    @Override
    public void render( SpriteBatch batch){

       m_TileMapRender.setView(this.getPlayerOne().getPlayCam());
       m_TileMapRender.render();

       batch.setProjectionMatrix(this.getPlayerOne().getPlayCam().combined);
       batch.begin();
       super.render(batch);
       batch.end();
    }

    @Override
    public void update(float delta){ //update what the method name should say what we are updating
        super.update(delta);
    }

    @Override
    public void disposeTileMap(){
        m_TileMap.dispose();
    }

    @Override
    public int getMapWidth(){
       return mapWidth;
    }

    @Override
    public int getMapHeight(){
       return mapHeight;
    }

    @Override
    public int getMapLayers(){
        return 0;
    }


}
