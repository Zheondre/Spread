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

	OrthographicCamera camera;

	gameMap m_GameMap;

	//we shouldnt load the game here we will have to do so in an activaty
	@Override
	public void create () {

		float wdth = Gdx.graphics.getWidth();
		float hght = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, wdth - 900, hght - 900);
		camera.update();

		m_GameMap = new tileGameMap();
		//Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {

		batch = new SpriteBatch();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isTouched()) {
			camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			camera.update();
		}

		m_GameMap.render(camera,batch);
		m_GameMap.update(Gdx.graphics.getDeltaTime());
		//m_GameMap.update(1);
		camera.update();
	}
}
