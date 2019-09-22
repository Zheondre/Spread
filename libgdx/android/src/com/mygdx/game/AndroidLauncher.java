package com.mygdx.game;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("testing","android launcher constructor");
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGdxGame(), config);
	}
}
