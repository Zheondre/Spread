/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */


package com.mygdx.world;

import android.util.Log;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class tileGameMap extends gameMap {

    TiledMap m_TileMap;
    OrthogonalTiledMapRenderer m_TileMapRender;

    public tileGameMap() {

        m_TileMap = new TmxMapLoader().load("house_road.tmx"); // we will have to make this dynamic based on user map selection
        m_TileMapRender = new OrthogonalTiledMapRenderer(m_TileMap);
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch){

       m_TileMapRender.setView(camera);
       m_TileMapRender.render();

       batch.setProjectionMatrix(camera.combined);
       batch.begin();
       super.render(camera, batch);
       batch.end();
    }

    @Override
    public void update(float delta){ //update what the method name should say what we are updating
    }

    @Override
    public void disposeTileMap(){
        m_TileMap.dispose();
    }

    @Override
    public int getWidth(){
       return 0;
    }

    @Override
    public int getHeight(){
       return 0;
    }

    @Override
    public int getLayers(){
        return 0;
    }
}
