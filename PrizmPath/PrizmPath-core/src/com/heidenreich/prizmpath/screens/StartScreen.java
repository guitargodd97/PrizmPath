package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Screen;
import com.heidenreich.prizmpath.PrizmPathGame;

public class StartScreen implements Screen {

	private PrizmPathGame p;
	public StartScreen(PrizmPathGame p) {
		this.p = p;
	}

	public void render(float delta) {
	}

	public void resize(int width, int height) {
	}

	public void show() {
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}

	private void toOptions() {
		p.setScreen(new OptionScreen(p));
	}
	
	private void toCredits() {
		p.setScreen(new CreditScreen(p));
	}
	private void toLevels() {
		p.setScreen(new LevelScreen(p));
	}
	
	private void toShop() {
		p.setScreen(new ShopScreen(p));
	}
}
