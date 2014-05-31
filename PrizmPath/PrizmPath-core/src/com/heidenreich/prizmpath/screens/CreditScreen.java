package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.heidenreich.prizmpath.PrizmPathGame;

//---------------------------------------------------------------------------------------------
//
//CreditScreen.java
//Last Revised: 5/31/2014
//Author: Hunter Heidenreich
//Product of: HunterMusicAndTV
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the screen class that displays the credit info about me.
//
//--------------------------------------------------------------------------------------------

public class CreditScreen implements Screen {

	private BitmapFont f;
	private BitmapFont g;
	private Label info;
	private Label title;
	private PrizmPathGame p;
	private Sprite box;
	private SpriteBatch batch;
	private Stage stage;

	// Constructs the CreditScreen
	public CreditScreen(PrizmPathGame p) {
		this.p = p;
	}

	// Updates the screen
	public void render(float delta) {
		// Clears the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Starts drawing the background
		PrizmPathGame.getBackground(PrizmPathGame.curBackground).setColor(
				new Color(Color.LIGHT_GRAY));
		batch.begin();
		PrizmPathGame.getBackground(PrizmPathGame.curBackground).draw(batch);
		box.draw(batch);
		batch.end();

		// Checks for clicks
		clickCheck();

		// Draws the stage
		stage.act();
		batch.begin();
		stage.draw();
		batch.end();
	}

	// Called when the window is resized
	public void resize(int width, int height) {
		// Sets up the stage
		if (stage == null)
			stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		// Label styles
		LabelStyle ls = new LabelStyle(f, Color.WHITE);
		LabelStyle lsg = new LabelStyle(g, Color.WHITE);

		// Title label
		title = new Label("Credits", ls);
		title.setX(0);
		title.setY(420);
		title.setWidth(Gdx.graphics.getWidth());
		title.setAlignment(Align.center);

		// Info label
		info = new Label(
				"This game was created by \nHunter Heidenreich. It is his first \napp. Hope you enjoy, and \nif you have any feedback visit \nwww.huntermusicandtv.com\n\nClick to continue...",
				lsg);
		info.setX(0);
		info.setY(100);
		info.setWidth(Gdx.graphics.getWidth());
		info.setAlignment(Align.center);

		// Adding to the stage
		stage.addActor(title);
		stage.addActor(info);
	}

	// Called when the screen is shown
	public void show() {
		// Sets up the SpriteBatch
		batch = new SpriteBatch();

		// Sets up the fonts
		f = new BitmapFont(Gdx.files.internal("data/font.fnt"));
		g = new BitmapFont(Gdx.files.internal("data/g.fnt"));

		// Sets up the box Sprite
		box = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("box");
	}

	// Called when the screen is hidden
	public void hide() {
		dispose();
	}

	// Called when the screen is paused
	public void pause() {
	}

	// Called when the screen is resumed
	public void resume() {
	}

	// Disposes of resources
	public void dispose() {
		f.dispose();
		g.dispose();
		stage.dispose();
		batch.dispose();
	}

	// Sends the game to the StartScreen
	private void toStart() {
		p.setScreen(new StartScreen(p));
	}

	// Checks if the screen should be exited
	private void clickCheck() {
		if (Gdx.input.isTouched())
			toStart();
	}
}
// © Hunter Heidenreich 2014