package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Screen;
import com.heidenreich.prizmpath.PrizmPathGame;

public class LevelScreen implements Screen {

	private PrizmPathGame p;
	public LevelScreen(PrizmPathGame p) {
		this.p = p;
	}

	@Override
	public void render(float delta) {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	private void toStart() {
		p.setScreen(new StartScreen(p));
	}
	
	private void toGame() {
		p.setScreen(new GameScreen(p));
	}
}
