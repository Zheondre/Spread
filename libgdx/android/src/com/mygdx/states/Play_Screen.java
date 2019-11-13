package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.managers.GameStateManager;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

public class Play_Screen extends GameState {


    TiledMap m_TileMap;
    OrthogonalTiledMapRenderer m_TileMapRender;
    gameMap m_GameMap;

    public Play_Screen(GameStateManager gsm)
    {
        super(gsm);
        m_GameMap = new tileGameMap();
    }


    @Override
    public void update(float delta) {
        //people.get(0).update(delta);
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_GameMap.render();
        m_GameMap.update(Gdx.graphics.getDeltaTime());
    }


    @Override
    public void dispose() {
        m_TileMap.dispose();
    }

    @Override
    public void handleInput() {

    }
}
