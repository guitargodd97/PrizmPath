package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.heidenreich.prizmpath.PrizmPathGame;

public class SplashScreen implements Screen {

	private int timer;
	private Music splash;
	private PrizmPathGame p;
	
	public SplashScreen(PrizmPathGame p) {
		this.p = p;
	}
	
	public void render(float delta) {

	}

	public void resize(int width, int height) {

	}

	public void show() {

	}

	public void hide() {
		dispose();
	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}
	
	private void startScreen() {
		p.setScreen(new StartScreen(p));
	}

}
