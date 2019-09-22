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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;


// this isnt platform specific

public class MyGdxGame extends ApplicationAdapter {
	//SpriteBatch batch;
	//Texture img;
	OrthographicCamera camera;
	gameMap m_GameMap;

	@Override
	public void create () {

		float wdth = Gdx.graphics.getWidth();
		float hght = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, wdth, hght);
		camera.update();

		m_GameMap = new tileGameMap();

		//Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
/*
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			//sprite.translate(-1f);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			//sprite.translate(1f);
		}
*/
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isTouched()) {
			camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			camera.update();
		}

		//crashing here
		m_GameMap.render(camera);

		camera.update();
	//	tiledMapRenderer.setView(camera);
	//	tiledMapRenderer.render();

	}

/*
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	*/
/*
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.LEFT)
			camera.translate(-32,0);
		if(keycode == Input.Keys.RIGHT)
			camera.translate(32,0);
		if(keycode == Input.Keys.UP)
			camera.translate(0,-32);
		if(keycode == Input.Keys.DOWN)
			camera.translate(0,32);
		if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	*/
}
