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

	SpriteBatch batch;// i think this should be in the game map object

	gameMap m_GameMap;

	//we shouldnt load the game here we will have to do so in an activity
	@Override
	public void create () {
		m_GameMap = new tileGameMap();
		batch = new SpriteBatch();
		m_GameMap.setBatch(batch);
	}

	@Override
	public void render () {

		//batch = new SpriteBatch();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		m_GameMap.render(batch);
		m_GameMap.update(Gdx.graphics.getDeltaTime());
	}
}
