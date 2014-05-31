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

public class OptionScreen implements Screen {

	private BitmapFont f;
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
		this.p = p;
		buttonSize = new Vector2(300, 75);
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
		tbs.font = f;

		// Background button
		background = new TextButton("Background: "
				+ PrizmPathGame.curBackground, tbs);
		background.setSize(buttonSize.x, buttonSize.y);
		background.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		background.setY((4 * buttonSize.y) + 15);
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
		muteSFX.setY((3 * buttonSize.y) + 15);
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
		muteMusic.setY((2 * buttonSize.y) + 15);
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
		song.setY((1 * buttonSize.y) + 15);
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
		title = new Label("Options", ls);
		title.setX(0);
		title.setY(420);
		title.setWidth(Gdx.graphics.getWidth());
		title.setAlignment(Align.center);

		// Info label
		info = new Label("Click to continue...", ls);
		info.setX(0);
		info.setY(25);
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
		background.setText("Background: " + PrizmPathGame.curBackground);
		String s = "";
		if (PrizmPathGame.isSfxMute())
			s = "off";
		else
			s = "on";
		muteSFX.setText("SFX: " + s);
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
		batch = new SpriteBatch();

		f = new BitmapFont(Gdx.files.internal("data/font.fnt"));

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
		stage.dispose();
		batch.dispose();
	}

	// Checks if the screen should be exited
	private void clickCheck() {
		if (Gdx.input.isTouched()
				&& (Gdx.input.getX() > 580 || Gdx.input.getX() < 220 || Gdx.input
						.getY() > 400))
			toStart();
	}

	// Sends the game to the StartScreen
	private void toStart() {
		p.setScreen(new StartScreen(p));
	}
}
// © Hunter Heidenreich 2014