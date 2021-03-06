package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.heidenreich.prizmpath.PrizmPathGame;

//---------------------------------------------------------------------------------------------
//
//OptionScreen.java
//Last Revised: 5/31/2014
//Author: Hunter Heidenreich
//Product of: HunterMusicAndTV
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the screen class that handles the changing of options.
//
//-------------------------------------------------------------------------------------------

public class OptionScreen implements Screen {

	private BitmapFont f;
	private BitmapFont g;
	private Label info;
	private Label title;
	private PrizmPathGame p;
	private Sprite box;
	private SpriteBatch batch;
	private Stage stage;
	private TextButton background;
	private TextButton muteMusic;
	private TextButton muteSFX;
	private TextButton song;
	private Vector2 buttonSize;

	// Constructs the OptionScreen
	public OptionScreen(PrizmPathGame p) {
		// Saves parameters
		this.p = p;

		// Sets size of buttons
		buttonSize = new Vector2(
				300 * (Gdx.graphics.getWidth() / (float) PrizmPathGame.WIDTH),
				60 * (Gdx.graphics.getHeight() / (float) PrizmPathGame.HEIGHT));
	}

	// Updates the screen
	public void render(float delta) {
		// Clears the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(p.getCam().combined);

		// Starts drawing the background
		PrizmPathGame.getBackground(PrizmPathGame.curBackground).setColor(
				new Color(Color.LIGHT_GRAY));
		batch.begin();
		PrizmPathGame.getBackground(PrizmPathGame.curBackground).draw(batch);
		box.draw(batch);
		batch.end();
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

		// Button style
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = g;

		buttonSize = new Vector2(
				300 * (Gdx.graphics.getWidth() / (float) PrizmPathGame.WIDTH),
				60 * (Gdx.graphics.getHeight() / (float) PrizmPathGame.HEIGHT));

		// Background button
		background = new TextButton("Background: "
				+ PrizmPathGame.curBackground, tbs);
		background.setSize(buttonSize.x, buttonSize.y);
		background.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		background.setY((4 * buttonSize.y) + 100);
		background.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				PrizmPathGame.nextBackground();
				updateButtons();
			}
		});

		// SFX mute button
		String s = "";
		if (PrizmPathGame.isSfxMute())
			s = "off";
		else
			s = "on";
		muteSFX = new TextButton("SFX: " + s, tbs);
		muteSFX.setSize(buttonSize.x, buttonSize.y);
		muteSFX.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		muteSFX.setY((3 * buttonSize.y) + 100);
		muteSFX.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				PrizmPathGame.setSfxMute();
				updateButtons();
			}
		});

		// Music mute button
		if (PrizmPathGame.isMusicMute())
			s = "off";
		else
			s = "on";
		muteMusic = new TextButton("Music: " + s, tbs);
		muteMusic.setSize(buttonSize.x, buttonSize.y);
		muteMusic.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		muteMusic.setY((2 * buttonSize.y) + 100);
		muteMusic.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				PrizmPathGame.setMusicMute();
				updateButtons();
			}
		});

		// Song Button
		song = new TextButton("Song: " + PrizmPathGame.curSong, tbs);
		song.setSize(buttonSize.x, buttonSize.y);
		song.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		song.setY((1 * buttonSize.y) + 100);
		song.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				PrizmPathGame.changeSong();
				updateButtons();
			}
		});

		// Title label
		LabelStyle ls = new LabelStyle(f, Color.WHITE);
		LabelStyle lsg = new LabelStyle(g, Color.WHITE);
		title = new Label("Options", ls);
		title.setX(0);
		title.setY(420 * (Gdx.graphics.getHeight() / (float) PrizmPathGame.HEIGHT));
		title.setWidth(Gdx.graphics.getWidth());
		title.setAlignment(Align.center);

		// Info label
		info = new Label("Click to continue...", lsg);
		info.setX(0);
		info.setY(110 * (Gdx.graphics.getHeight() / (float) PrizmPathGame.HEIGHT));
		info.setWidth(Gdx.graphics.getWidth());
		info.setAlignment(Align.center);

		// Adding things to stage
		stage.addActor(background);
		stage.addActor(muteSFX);
		stage.addActor(muteMusic);
		stage.addActor(title);
		stage.addActor(info);
		stage.addActor(song);
	}

	// When a button is clicked, update the rest
	protected void updateButtons() {
		// Updates the background
		background.setText("Background: " + PrizmPathGame.curBackground);

		// Updates the sfx
		String s = "";
		if (PrizmPathGame.isSfxMute())
			s = "off";
		else
			s = "on";
		muteSFX.setText("SFX: " + s);

		// Updates the music
		if (PrizmPathGame.isMusicMute())
			s = "off";
		else
			s = "on";
		muteMusic.setText("Music: " + s);
		if (PrizmPathGame.isMusicMute())
			PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
					.pause();
		else
			PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
					.play();
		song.setText("Song: " + PrizmPathGame.curSong);
	}

	// Called when the screen is shown
	public void show() {
		// Sets up the SpriteBatch
		batch = new SpriteBatch();

		// Retrieves the fonts
		f = new BitmapFont(Gdx.files.internal("data/font.fnt"));
		g = new BitmapFont(Gdx.files.internal("data/g.fnt"));

		// Retrieves box
		box = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("box2");
		box.setY(50);
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

	// Checks if the screen should be exited
	private void clickCheck() {
		if (Gdx.input.isTouched()
				&& (Gdx.input.getX() > 580 * (Gdx.graphics.getWidth() / (float) PrizmPathGame.WIDTH)
						|| Gdx.input.getX() < 220 * (Gdx.graphics.getWidth() / (float) PrizmPathGame.WIDTH) || Gdx.input
						.getY() > 400 * (Gdx.graphics.getHeight() / (float) PrizmPathGame.HEIGHT)))
			toStart();
	}

	// Sends the game to the StartScreen
	private void toStart() {
		p.setScreen(new StartScreen(p));
	}
}
// � Hunter Heidenreich 2014