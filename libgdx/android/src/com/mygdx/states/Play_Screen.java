package com.mygdx.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.managers.GameStateManager;

public class Play_Screen extends GameState {


    TiledMap m_TileMap;
    OrthogonalTiledMapRenderer m_TileMapRender;

    public Play_Screen(GameStateManager gsm)
    {
        super(gsm);
        m_TileMap = new TmxMapLoader().load("house_road.tmx");
        m_TileMapRender = new OrthogonalTiledMapRenderer(m_TileMap);
    }


    @Override
    public void update(float delta) {
        //people.get(0).update(delta);
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        //people.get(0).render(batch);
        m_TileMapRender.setView(camera);
        m_TileMapRender.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        m_TileMap.dispose();
    }

    @Override
    public void handleInput() {

    }
}
