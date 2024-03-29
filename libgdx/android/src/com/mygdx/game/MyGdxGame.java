/*
Tutorials

https://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx


 */

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

// this isnt platform specific
//entry point to game
public class MyGdxGame extends ApplicationAdapter {

	gameMap m_GameMap;

	@Override
	public void create () {
		m_GameMap = new tileGameMap();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		m_GameMap.render();
		m_GameMap.update(Gdx.graphics.getDeltaTime());
	}


}
