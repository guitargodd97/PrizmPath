package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.heidenreich.prizmpath.PrizmPathGame;
import com.heidenreich.prizmpath.Tile;

public class GameScreen implements Screen {

	private BitmapFont f;
	private int clickBuffer;
	private int level;
	private PrizmPathGame p;
	private SpriteBatch batch;
	private Stage stage;
	private Tile collection[][];

	// Constructs the GameScreen
	public GameScreen(PrizmPathGame p, int level) {
		this.p = p;
		this.level = level;
		clickBuffer = 0;
		collection = new Tile[10][26];
		for (int i = 0; i < collection.length; i++)
			for (int id = 0; id < collection[i].length; id++)
				collection[i][id] = new Tile(new Vector2((30 * id) + 10,
						(30 * i) + 80), true);
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
		for (int i = 0; i < collection.length; i++)
			for (int id = 0; id < collection[i].length; id++)
				collection[i][id].draw(batch);
		for (int i = 0; i < collection.length; i++)
			for (int id = 0; id < collection[i].length; id++)
				collection[i][id].drawPrizms(batch);
		batch.end();

		if (Gdx.input.isTouched() && clickBuffer == 0) {
			Vector2 t = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight()
					- Gdx.input.getY());
			for (int i = 0; i < collection.length; i++) {
				for (int id = 0; id < collection[i].length; id++) {
					if (collection[i][id].checkCollision(t))
						collection[i][id].changeColor();
				}
			}
			clickBuffer++;
		} else if (!Gdx.input.isTouched() && clickBuffer == 1)
			clickBuffer++;
		else if (clickBuffer == 2)
			clickBuffer = 0;

		stage.act();
		batch.begin();
		stage.draw();
		batch.end();
	}

	// Called when the window is resized
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);
	}

	// Called when the screen is shown
	public void show() {
		batch = new SpriteBatch();

		f = new BitmapFont(Gdx.files.internal("data/font.fnt"));

		// Determines whether the song should be played
		if (PrizmPathGame.isMusicMute())
			PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
					.pause();
		else
			PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
					.play();
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	// Disposes of resources
	public void dispose() {
		f.dispose();
		stage.dispose();
		batch.dispose();
	}

	private void toStart() {
		p.setScreen(new StartScreen(p));
	}
}