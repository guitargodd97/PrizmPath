package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.heidenreich.prizmpath.PrizmPathGame;
import com.heidenreich.prizmpath.Tile;

public class GameScreen implements Screen {

	private BitmapFont f;
	private int clickBuffer;
	private int curClick;
	private int level;
	private int[] maxClick;
	private int maxClickIndex;
	private Label click;
	private Label title;
	private PrizmPathGame p;
	private SpriteBatch batch;
	private Stage stage;
	private Tile collection[][];

	// Constructs the GameScreen
	public GameScreen(PrizmPathGame p, int level) {
		this.p = p;
		this.level = level;
		clickBuffer = 0;
		collection = new Tile[5][13];
		curClick = 0;
		maxClickIndex = 0;
		maxClick = new int[4];
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
					if (collection[i][id].checkCollision(t)
							&& collection[i][id].isPrizmActive()) {
						if (collection[i][id].isSelected()) {
							collection[i][id].changeColor();
							changePrizms(collection[i][id].getType(), i, id, 1);
							curClick++;
							checkClicks(collection[i][id].getColor());
							for (int x = 0; x < collection.length; x++) {
								for (int y = 0; y < collection[x].length; y++) {
									collection[x][y].setSelected(false);
									collection[x][y].setFrame(0);
								}
							}
						} else {
							for (int x = 0; x < collection.length; x++) {
								for (int y = 0; y < collection[x].length; y++) {
									collection[x][y].setSelected(false);
									collection[x][y].setFrame(0);
								}
							}
							collection[i][id].setFrame(2);
							changePrizms(collection[i][id].getType(), i, id, 0);
							curClick++;
							collection[i][id].setSelected(true);
						}
					}
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

		// Title label
		LabelStyle ls = new LabelStyle(f, Color.WHITE);
		title = new Label("Level " + level, ls);
		title.setX(0);
		title.setY(420);
		title.setWidth(Gdx.graphics.getWidth());
		title.setAlignment(Align.center);

		// Click Label
		click = new Label(
				"Clicks: " + curClick + "/" + maxClick[maxClickIndex], ls);
		click.setX(400);
		click.setY(420);
		click.setWidth(Gdx.graphics.getWidth() / 2);
		click.setAlignment(Align.center);

		// Add things to the stage
		stage.addActor(title);
		stage.addActor(click);
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
		constructLevel();
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

	private void constructLevel() {
		switch (level) {
		case (1):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(3, 0);
			collection[2][6].setPrizm(0, 0);
			maxClick[0] = 5;
			maxClick[1] = (int) (maxClick[0] * 1.5);
			maxClick[2] = (int) (maxClick[0] * 1.75);
			maxClick[3] = (int) (maxClick[0] * 2);
			break;
		}
	}

	private void changePrizms(int type, int x, int y, int change) {
		//Cases
		// 0 = 0 1 0
		//     1 2 1
		//     0 1 0
		//
		// 1 = 1 0 1
		//     0 2 0
		//     1 0 1
		//
		// 2 = 1 1 1
		//     1 2 1
		//     1 1 1
		//
		// 3 = 1 2 1
		//     0 1 0
		//     0 1 0
		if (change == 1) {
			switch (type) {
			case (0):
				collection[x][y - 1].changeColor();
				collection[x][y + 1].changeColor();
				collection[x - 1][y].changeColor();
				collection[x + 1][y].changeColor();
				break;
			}
		} else {
			switch (type) {
			case (0):
				collection[x][y - 1].setFrame(1);
				collection[x][y + 1].setFrame(1);
				collection[x - 1][y].setFrame(1);
				collection[x + 1][y].setFrame(1);
				break;
			}
		}
	}

	private void checkClicks(int theColor) {
		boolean completed = true;
		for (int i = 0; i < collection.length; i++) {
			for (int id = 0; id < collection[i].length; id++) {
				if (collection[i][id].isPrizmActive()) {
					if (collection[i][id].getColor() != theColor) {
						completed = false;
						id = collection[i].length - 1;
						i = collection.length - 1;
					}
				}
			}
		}
		if (completed)
			toStart();
		if (curClick >= maxClick[maxClickIndex]
				&& maxClickIndex < maxClick.length - 1)
			maxClickIndex++;
		else if (curClick >= maxClick[3])
			toStart(); // Gameover code
		click.setText("Clicks: " + curClick + "/" + maxClick[maxClickIndex]);
	}

	private void toStart() {
		p.setScreen(new StartScreen(p));
	}
}