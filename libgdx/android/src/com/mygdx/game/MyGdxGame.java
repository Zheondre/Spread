/*
Tutorials

https://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx


 */

package com.mygdx.game;

import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

// this isnt platform specific

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;

	gameMap m_GameMap;

	//we shouldnt load the game here we will have to do so in an activity
	@Override
	public void create () {
		m_GameMap = new tileGameMap();
	}

	@Override
	public void render () {

		batch = new SpriteBatch();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		m_GameMap.render(batch);
		m_GameMap.update(Gdx.graphics.getDeltaTime());
	}
}
