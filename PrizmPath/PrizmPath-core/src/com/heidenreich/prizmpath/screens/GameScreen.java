package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.heidenreich.prizmpath.PrizmPathGame;
import com.heidenreich.prizmpath.Tile;

public class GameScreen implements Screen {

	private BitmapFont f;
	private ImageButton home;
	private ImageButton next;
	private ImageButton pause;
	private ImageButton play;
	private ImageButton restart;
	private int clickBuffer;
	private int curClick;
	private int gameState;
	private int level;
	private int[] maxClick;
	private int maxClickIndex;
	private Label click;
	private Label title;
	private PrizmPathGame p;
	private SpriteBatch batch;
	private Stage stage;
	private Tile collection[][];
	private Vector2 buttonSize;

	// Constructs the GameScreen
	public GameScreen(PrizmPathGame p, int level) {
		this.p = p;
		this.level = level;
		clickBuffer = 0;
		collection = new Tile[5][13];
		curClick = 0;
		maxClickIndex = 0;
		maxClick = new int[4];

		buttonSize = new Vector2(60, 60);

		// 0 = running, 1 = options, 2 = gameover, 3 = level won
		gameState = 0;
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

		if (gameState == 0) {
			if (Gdx.input.isTouched() && clickBuffer == 0) {
				Vector2 t = new Vector2(Gdx.input.getX(),
						Gdx.graphics.getHeight() - Gdx.input.getY());
				for (int i = 0; i < collection.length; i++) {
					for (int id = 0; id < collection[i].length; id++) {
						if (collection[i][id].checkCollision(t)
								&& collection[i][id].isPrizmActive()) {
							if (collection[i][id].isSelected()) {
								collection[i][id].changeColor();
								changePrizms(collection[i][id].getType(), i,
										id, 1);
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
								collection[i][id].setFrame(1);
								changePrizms(collection[i][id].getType(), i,
										id, 0);
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
		} else if (gameState == 1) {

		} else if (gameState == 2) {

		} else {

		}

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

		// Image Buttons
		ImageButtonStyle imageStyle = new ImageButtonStyle();
		imageStyle.imageUp = new SpriteDrawable(PrizmPathGame.homeButtons[0]);
		imageStyle.imageDown = new SpriteDrawable(PrizmPathGame.homeButtons[1]);

		// Home Button
		home = new ImageButton(imageStyle);
		home.setSize(buttonSize.x, buttonSize.y);
		home.setX((Gdx.graphics.getWidth() - buttonSize.x) / 2 - buttonSize.x
				- 20);
		home.setY((Gdx.graphics.getHeight() - buttonSize.y) / 2);
		home.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				toStart();
			}
		});
		home.setVisible(false);
		home.setDisabled(true);

		// Pause Button
		imageStyle.imageUp = new SpriteDrawable(PrizmPathGame.pauseButtons[0]);
		imageStyle.imageDown = new SpriteDrawable(PrizmPathGame.pauseButtons[1]);
		pause = new ImageButton(imageStyle);
		pause.setSize(buttonSize.x, buttonSize.y);
		pause.setX(Gdx.graphics.getWidth() - 64);
		pause.setY(Gdx.graphics.getHeight() - 75);
		pause.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				optionOpen();
			}
		});

		// Play Button
		imageStyle.imageUp = new SpriteDrawable(PrizmPathGame.playButtons[0]);
		imageStyle.imageDown = new SpriteDrawable(PrizmPathGame.playButtons[1]);
		play = new ImageButton(imageStyle);
		play.setSize(buttonSize.x, buttonSize.y);
		play.setX((Gdx.graphics.getWidth() - buttonSize.x) / 2);
		play.setY((Gdx.graphics.getHeight() - buttonSize.y) / 2);
		play.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				optionClose();
			}
		});
		play.setDisabled(true);
		play.setVisible(false);

		// Next Button
		imageStyle.imageUp = new SpriteDrawable(PrizmPathGame.playButtons[0]);
		imageStyle.imageDown = new SpriteDrawable(PrizmPathGame.playButtons[1]);
		next = new ImageButton(imageStyle);
		next.setSize(buttonSize.x, buttonSize.y);
		next.setX((Gdx.graphics.getWidth() - buttonSize.x) / 2);
		next.setY((Gdx.graphics.getHeight() - buttonSize.y) / 2);
		next.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				toNextLevel();
			}
		});
		next.setDisabled(true);
		next.setVisible(false);

		// Replay Button
		imageStyle.imageUp = new SpriteDrawable(PrizmPathGame.restartButtons[0]);
		imageStyle.imageDown = new SpriteDrawable(
				PrizmPathGame.restartButtons[1]);
		restart = new ImageButton(imageStyle);
		restart.setSize(buttonSize.x, buttonSize.y);
		restart.setX((Gdx.graphics.getWidth() - buttonSize.x) / 2
				+ buttonSize.x + 20);
		restart.setY((Gdx.graphics.getHeight() - buttonSize.y) / 2);
		restart.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				replayLevel();
			}
		});
		restart.setDisabled(true);
		restart.setVisible(false);

		// Add things to the stage
		stage.addActor(title);
		stage.addActor(click);
		stage.addActor(home);
		stage.addActor(next);
		stage.addActor(pause);
		stage.addActor(play);
		stage.addActor(restart);
	}

	private void optionOpen() {
		gameState = 1;
		restart.setDisabled(false);
		restart.setVisible(true);
		play.setDisabled(false);
		play.setVisible(true);
		home.setDisabled(false);
		home.setVisible(true);
	}

	private void optionClose() {
		gameState = 0;
		restart.setDisabled(true);
		restart.setVisible(false);
		play.setDisabled(true);
		play.setVisible(false);
		home.setDisabled(true);
		home.setVisible(false);
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
		// X X X X X X X X X X X X X
		// X X X X X 0 5 0 X X X X X
		// X X X X X 5 5 5 X X X X X
		// X X X X X 0 5 0 X X X X X
		// X X X X X X X X X X X X X
		case (1):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(0, 0);
			collection[2][5].setPrizm(5, 0);
			collection[2][6].setPrizm(5, 0);
			collection[2][7].setPrizm(5, 0);
			collection[1][6].setPrizm(5, 0);
			collection[3][6].setPrizm(5, 0);
			maxClick[0] = 1;
			break;
		// X X X X X X X X X X X X X
		// X X X X X 5 3 5 X X X X X
		// X X X X X 4 3 4 X X X X X
		// X X X X X 0 4 0 X X X X X
		// X X X X X X X X X X X X X
		case (2):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(4, 0);
			collection[3][5].setPrizm(5, 0);
			collection[3][6].setPrizm(3, 0);
			collection[3][7].setPrizm(5, 0);
			collection[2][6].setPrizm(3, 0);
			collection[1][5].setPrizm(0, 0);
			collection[1][7].setPrizm(0, 0);
			maxClick[0] = 3;
			break;
		// X X X X X X X X X X X X X
		// X X X X X 2 0 0 X X X X X
		// X X X X X 2 0 0 X X X X X
		// X X X X X 3 2 2 X X X X X
		// X X X X X X X X X X X X X
		case (3):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(0, 0);
			collection[3][5].setPrizm(2, 0);
			collection[2][5].setPrizm(2, 0);
			collection[1][5].setPrizm(3, 0);
			collection[1][6].setPrizm(2, 0);
			collection[1][7].setPrizm(2, 0);
			maxClick[0] = 4;
			break;
		// X X X X X X X X X X X X X
		// X X X X X 3 3 3 X X X X X
		// X X X X X 1 0 1 X X X X X
		// X X X X X 1 0 1 X X X X X
		// X X X X X X X X X X X X X
		case (4):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(1, 0);
			collection[3][5].setPrizm(3, 0);
			collection[3][6].setPrizm(3, 0);
			collection[3][7].setPrizm(3, 0);
			collection[2][6].setPrizm(0, 0);
			collection[1][6].setPrizm(0, 0);
			maxClick[0] = 6;
			break;
		// X X X X X X X X X X X X X
		// X X X X X 0 4 1 X X X X X
		// X X X X X 2 1 3 X X X X X
		// X X X X X 0 4 1 X X X X X
		// X X X X X X X X X X X X X
		case (5):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(1, 0);
			collection[3][5].setPrizm(0, 0);
			collection[3][6].setPrizm(4, 0);
			collection[2][5].setPrizm(2, 0);
			collection[2][7].setPrizm(3, 0);
			collection[1][5].setPrizm(0, 0);
			collection[1][6].setPrizm(4, 0);
			maxClick[0] = 7;
			break;
		// X X X X X X X X X X X X X
		// X X X X X 5 0 5 X X X X X
		// X X X X X 0 5 0 X X X X X
		// X X X X X 5 0 5 X X X X X
		// X X X X X X X X X X X X X
		case (6):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(5, 1);
			collection[3][6].setPrizm(0, 0);
			collection[2][5].setPrizm(0, 0);
			collection[2][7].setPrizm(0, 0);
			collection[1][6].setPrizm(0, 0);
			maxClick[0] = 1;
			break;
		// X X X X X X X X X X X X X
		// X X X X X 5 0 5 X X X X X
		// X X X X X 0 5 0 X X X X X
		// X X X X X 5 0 5 X X X X X
		// X X X X X X X X X X X X X
		case (7):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(4, 1);
			collection[3][6].setPrizm(1, 1);
			collection[2][5].setPrizm(1, 1);
			collection[2][6].setPrizm(0, 0);
			collection[2][7].setPrizm(1, 1);
			collection[1][6].setPrizm(1, 1);
			maxClick[0] = 9;
			break;
		}
		maxClick[1] = (int) (maxClick[0] * 1.5);
		maxClick[2] = (int) (maxClick[0] * 1.75);
		maxClick[3] = (int) (maxClick[0] * 2);
	}

	private void changePrizms(int type, int x, int y, int change) {
		// Cases
		//
		// 0
		// 0 0 0 0 0
		// 0 0 1 0 0
		// 0 1 2 1 0
		// 0 0 1 0 0
		// 0 0 0 0 0
		//
		// 1
		// 0 0 0 0 0
		// 0 1 0 1 0
		// 0 0 2 0 0
		// 0 1 0 1 0
		// 0 0 0 0 0
		//
		// 2
		// 0 0 0 0 0
		// 0 1 1 1 0
		// 0 1 2 1 0
		// 0 1 1 1 0
		// 0 0 0 0 0
		//
		// 3
		// 0 0 1 0 0
		// 0 0 1 0 0
		// 1 1 2 1 1
		// 0 0 1 0 0
		// 0 0 1 0 0
		//
		// 4
		// 1 0 0 0 1
		// 0 1 0 1 0
		// 0 0 2 0 0
		// 0 1 0 1 0
		// 1 0 0 0 1
		//
		if (change == 1) {
			switch (type) {
			case (0):
				if (y > 0)
					collection[x][y - 1].changeColor();
				if (y < collection[x].length - 1)
					collection[x][y + 1].changeColor();
				if (x > 0)
					collection[x - 1][y].changeColor();
				if (x < collection.length - 1)
					collection[x + 1][y].changeColor();
				break;
			case (1):
				if (x > 0 && y > 0)
					collection[x - 1][y - 1].changeColor();
				if (x > 0 && y < collection[x].length - 1)
					collection[x - 1][y + 1].changeColor();
				if (x < collection.length - 1 && y > 0)
					collection[x + 1][y - 1].changeColor();
				if (x < collection.length - 1 && y < collection[x].length - 1)
					collection[x + 1][y + 1].changeColor();
				break;
			case (2):
				if (y > 0)
					collection[x][y - 1].changeColor();
				if (y < collection[x].length - 1)
					collection[x][y + 1].changeColor();
				if (x > 0)
					collection[x - 1][y].changeColor();
				if (x < collection.length - 1)
					collection[x + 1][y].changeColor();
				if (x > 0 && y > 0)
					collection[x - 1][y - 1].changeColor();
				if (x > 0 && y < collection[x].length - 1)
					collection[x - 1][y + 1].changeColor();
				if (x < collection.length - 1 && y > 0)
					collection[x + 1][y - 1].changeColor();
				if (x < collection.length - 1 && y < collection[x].length - 1)
					collection[x + 1][y + 1].changeColor();
				break;
			case (3):
				if (y > 0)
					collection[x][y - 1].changeColor();
				if (y < collection[x].length - 1)
					collection[x][y + 1].changeColor();
				if (x > 0)
					collection[x - 1][y].changeColor();
				if (x < collection.length - 1)
					collection[x + 1][y].changeColor();
				if (y > 1)
					collection[x][y - 2].changeColor();
				if (y < collection[x].length - 2)
					collection[x][y + 2].changeColor();
				if (x > 1)
					collection[x - 2][y].changeColor();
				if (x < collection.length - 2)
					collection[x + 2][y].changeColor();
				break;
			case (4):
				if (x > 0 && y > 0)
					collection[x - 1][y - 1].changeColor();
				if (x > 0 && y < collection[x].length - 1)
					collection[x - 1][y + 1].changeColor();
				if (x < collection.length - 1 && y > 0)
					collection[x + 1][y - 1].changeColor();
				if (x < collection.length - 1 && y < collection[x].length - 1)
					collection[x + 1][y + 1].changeColor();
				if (x > 1 && y > 1)
					collection[x - 2][y - 2].changeColor();
				if (x > 1 && y < collection[x].length - 2)
					collection[x - 2][y + 2].changeColor();
				if (x < collection.length - 2 && y > 1)
					collection[x + 2][y - 2].changeColor();
				if (x < collection.length - 2 && y < collection[x].length - 2)
					collection[x + 2][y + 2].changeColor();
				break;
			}
		} else {
			switch (type) {
			case (0):
				if (y > 0)
					collection[x][y - 1].setFrame(1);
				if (y < collection[x].length - 1)
					collection[x][y + 1].setFrame(1);
				if (x > 0)
					collection[x - 1][y].setFrame(1);
				if (x < collection.length - 1)
					collection[x + 1][y].setFrame(1);
				break;
			case (1):
				if (x > 0 && y > 0)
					collection[x - 1][y - 1].setFrame(1);
				if (x > 0 && y < collection[x].length - 1)
					collection[x - 1][y + 1].setFrame(1);
				if (x < collection.length - 1 && y > 0)
					collection[x + 1][y - 1].setFrame(1);
				if (x < collection.length - 1 && y < collection[x].length - 1)
					collection[x + 1][y + 1].setFrame(1);
				break;
			case (2):
				if (y > 0)
					collection[x][y - 1].setFrame(1);
				if (y < collection[x].length - 1)
					collection[x][y + 1].setFrame(1);
				if (x > 0)
					collection[x - 1][y].setFrame(1);
				if (x < collection.length - 1)
					collection[x + 1][y].setFrame(1);
				if (x > 0 && y > 0)
					collection[x - 1][y - 1].setFrame(1);
				if (x > 0 && y < collection[x].length - 1)
					collection[x - 1][y + 1].setFrame(1);
				if (x < collection.length - 1 && y > 0)
					collection[x + 1][y - 1].setFrame(1);
				if (x < collection.length - 1 && y < collection[x].length - 1)
					collection[x + 1][y + 1].setFrame(1);
				break;
			case (3):
				if (y > 0)
					collection[x][y - 1].setFrame(1);
				if (y < collection[x].length - 1)
					collection[x][y + 1].setFrame(1);
				if (x > 0)
					collection[x - 1][y].setFrame(1);
				if (x < collection.length - 1)
					collection[x + 1][y].setFrame(1);
				if (y > 1)
					collection[x][y - 2].setFrame(1);
				if (y < collection[x].length - 2)
					collection[x][y + 2].setFrame(1);
				if (x > 1)
					collection[x - 2][y].setFrame(1);
				if (x < collection.length - 2)
					collection[x + 2][y].setFrame(1);
				break;
			case (4):
				if (x > 0 && y > 0)
					collection[x - 1][y - 1].setFrame(1);
				if (x > 0 && y < collection[x].length - 1)
					collection[x - 1][y + 1].setFrame(1);
				if (x < collection.length - 1 && y > 0)
					collection[x + 1][y - 1].setFrame(1);
				if (x < collection.length - 1 && y < collection[x].length - 1)
					collection[x + 1][y + 1].setFrame(1);
				if (x > 1 && y > 1)
					collection[x - 2][y - 2].setFrame(1);
				if (x > 1 && y < collection[x].length - 2)
					collection[x - 2][y + 2].setFrame(1);
				if (x < collection.length - 2 && y > 1)
					collection[x + 2][y - 2].setFrame(1);
				if (x < collection.length - 2 && y < collection[x].length - 2)
					collection[x + 2][y + 2].setFrame(1);
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
			win();
		if (curClick >= maxClick[maxClickIndex]
				&& maxClickIndex < maxClick.length - 1)
			maxClickIndex++;
		else if (curClick >= maxClick[3])
			lose();
		click.setText("Clicks: " + curClick + "/" + maxClick[maxClickIndex]);
	}

	private void win() {
		gameState = 3;
		restart.setDisabled(false);
		restart.setVisible(true);
		next.setDisabled(false);
		next.setVisible(true);
		home.setDisabled(false);
		home.setVisible(true);
		if (level > 0) {
			if (maxClickIndex == 0)
				PrizmPathGame.setLevelData((byte) 4, level - 1);
			else if (maxClickIndex == 1)
				PrizmPathGame.setLevelData((byte) 3, level - 1);
			else if (maxClickIndex == 2)
				PrizmPathGame.setLevelData((byte) 2, level - 1);
		}
		if (level < 60)
			PrizmPathGame.setLevelData((byte) 1, level);
	}

	private void lose() {
		gameState = 2;
		restart.setDisabled(false);
		restart.setVisible(true);
		home.setDisabled(false);
		home.setVisible(true);
	}

	private void toStart() {
		p.setScreen(new StartScreen(p));
	}

	private void toNextLevel() {
		p.setScreen(new GameScreen(p, level + 1));
	}

	private void replayLevel() {
		p.setScreen(new GameScreen(p, level));
	}
}
// © Hunter Heidenreich 2014