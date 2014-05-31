package com.heidenreich.prizmpath.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.heidenreich.prizmpath.PrizmPathGame;
import com.heidenreich.prizmpath.Solutions;
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
	private int tutorial;
	private int[] maxClick;
	private int maxClickIndex;
	private Label click;
	private Label info;
	private Label title;
	private PrizmPathGame p;
	private Solutions answers;
	private Sprite box;
	private Sprite tuts[];
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

		// 0 = running, 1 = options, 2 = gameover, 3 = level won, 4 = tutorial
		gameState = 0;

		if (level == 1 || level == 6 || level == 11 || level == 16
				|| level == 21 || level == 26) {
			gameState = 4;
			if (level == 1)
				tutorial = 8;
			else
				tutorial = 0;
		}
		answers = new Solutions(level);
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
								if (answers.checkMove(new Vector2(i, id))) {
									collection[i][id].changeColor();
									changePrizms(collection[i][id].getType(),
											i, id, 1);
									if (!PrizmPathGame.isSfxMute())
										PrizmPathGame.assets.get(
												PrizmPathGame.SFX_PATH
														+ "correct.mp3",
												Sound.class).play();
								} else {
									if (!PrizmPathGame.isSfxMute())
										PrizmPathGame.assets.get(
												PrizmPathGame.SFX_PATH
														+ "wrong.mp3",
												Sound.class).play();
									collection[i][id].triggerWrong();
								}
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
			batch.begin();
			box.draw(batch);
			batch.end();
		} else if (gameState == 2) {
			batch.begin();
			box.draw(batch);
			batch.end();
		} else if (gameState == 3) {
			batch.begin();
			box.draw(batch);
			batch.end();
		} else {
			batch.begin();
			box.draw(batch);
			info.setVisible(true);
			if (tutorial == 8) {
				info.setText("Welcome to PrizmPath\nThe Color Puzzle Game!");
				tutorial--;
			} else if (tutorial == 1) {
				tuts[0].draw(batch);
			} else if (level != 1) {
				switch (level) {
				case (6):
					info.setText("Here is the pyramidal prizm. It affects the prizms\nto its diagonals.");
					tuts[1].draw(batch);
					break;
				case (11):
					info.setText("This is the cubic prizm. It affects all the prizms\nthat surround it.");
					tuts[2].draw(batch);
					break;
				case (16):
					info.setText("The tetrahedral prizm acts like a sphere prizm,\nexcept it reaches for the two adjacent prizms.");
					tuts[3].draw(batch);
					break;
				case (21):
					info.setText("The diamond prizm is a combination of the pyramidal\nand tetrahedral prizm. It affects its diagonal for \ntwo prizms.");
					tuts[4].draw(batch);
					break;
				default:
					info.setText("You now hold all the knowledge of the prizms.\nGo forth and take on these last five challenge levels!");
					break;
				}
			}
			if (tutorial > -1) {
				info.setY(220);
				if (Gdx.input.isTouched() && clickBuffer == 0) {
					switch (level) {
					case (1):
						switch (tutorial) {
						case (7):
							info.setText("The object of the game is to make all the prizms\non the screen the same color.");
							break;
						case (6):
							info.setText("To change a prizm's color, you have to click to select it\nand then click again to finalize your choice.");
							break;
						case (5):
							info.setText("Colors cycle in this order:\nRed>Orange>Yellow>Green>Blue>Purple>Red");
							break;
						case (4):
							info.setText("Seems simple enough, right?\nHowever, there is one catch!");
							break;
						case (3):
							info.setText("There are 5 types of prizms, determined by shape,\nthat affect the prizms surrounding it in a unique way.");
							break;
						case (2):
							info.setText("To start, let's introduce the sphere prizm: It affects\nthe prizms in its four, adjacent cardinal directions");
							break;
						case (1):
							info.setText("One final thing before you start, I appreciate\nyou downloading my app and I hope you enjoy!");
							break;
						}
						break;
					}
					tutorial--;
					clickBuffer++;
				} else if (!Gdx.input.isTouched() && clickBuffer == 1)
					clickBuffer++;
				else if (clickBuffer == 2)
					clickBuffer = 0;
			} else {
				gameState = 0;
				info.setY(250);
				info.setVisible(false);
			}
			batch.end();
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

		// Info Label
		info = new Label("Info", ls);
		info.setX(0);
		info.setY(250);
		info.setWidth(Gdx.graphics.getWidth());
		info.setAlignment(Align.center);
		info.setVisible(false);

		// Image Buttons
		ImageButtonStyle homeStyle = new ImageButtonStyle();
		homeStyle.imageUp = new SpriteDrawable(PrizmPathGame.homeButtons[0]);
		homeStyle.imageDown = new SpriteDrawable(PrizmPathGame.homeButtons[1]);

		// Home Button
		home = new ImageButton(homeStyle);
		home.setSize(buttonSize.x, buttonSize.y);
		home.setX((Gdx.graphics.getWidth() - buttonSize.x) / 2 - buttonSize.x
				- 20);
		home.setY((Gdx.graphics.getHeight() - buttonSize.y) / 2
				- (Gdx.graphics.getHeight() / 4));
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
		ImageButtonStyle pauseStyle = new ImageButtonStyle();
		pauseStyle.imageUp = new SpriteDrawable(PrizmPathGame.pauseButtons[0]);
		pauseStyle.imageDown = new SpriteDrawable(PrizmPathGame.pauseButtons[1]);
		pause = new ImageButton(pauseStyle);
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
		ImageButtonStyle playStyle = new ImageButtonStyle();
		playStyle.imageUp = new SpriteDrawable(PrizmPathGame.playButtons[0]);
		playStyle.imageDown = new SpriteDrawable(PrizmPathGame.playButtons[1]);
		play = new ImageButton(playStyle);
		play.setSize(buttonSize.x, buttonSize.y);
		play.setX((Gdx.graphics.getWidth() - buttonSize.x) / 2);
		play.setY((Gdx.graphics.getHeight() - buttonSize.y) / 2
				- (Gdx.graphics.getHeight() / 4));
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
		ImageButtonStyle nextStyle = new ImageButtonStyle();
		nextStyle.imageUp = new SpriteDrawable(PrizmPathGame.playButtons[0]);
		nextStyle.imageDown = new SpriteDrawable(PrizmPathGame.playButtons[1]);
		next = new ImageButton(nextStyle);
		next.setSize(buttonSize.x, buttonSize.y);
		next.setX((Gdx.graphics.getWidth() - buttonSize.x) / 2);
		next.setY((Gdx.graphics.getHeight() - buttonSize.y) / 2
				- (Gdx.graphics.getHeight() / 4));
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
		ImageButtonStyle replayStyle = new ImageButtonStyle();
		replayStyle.imageUp = new SpriteDrawable(
				PrizmPathGame.restartButtons[0]);
		replayStyle.imageDown = new SpriteDrawable(
				PrizmPathGame.restartButtons[1]);
		restart = new ImageButton(replayStyle);
		restart.setSize(buttonSize.x, buttonSize.y);
		restart.setX((Gdx.graphics.getWidth() - buttonSize.x) / 2
				+ buttonSize.x + 20);
		restart.setY((Gdx.graphics.getHeight() - buttonSize.y) / 2
				- (Gdx.graphics.getHeight() / 4));
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
		stage.addActor(info);
	}

	private void optionOpen() {
		gameState = 1;
		restart.setDisabled(false);
		restart.setVisible(true);
		play.setDisabled(false);
		play.setVisible(true);
		home.setDisabled(false);
		home.setVisible(true);
		info.setText("GAME PAUSED");
		info.setVisible(true);
	}

	private void optionClose() {
		gameState = 0;
		restart.setDisabled(true);
		restart.setVisible(false);
		play.setDisabled(true);
		play.setVisible(false);
		home.setDisabled(true);
		home.setVisible(false);
		info.setVisible(false);
	}

	// Called when the screen is shown
	public void show() {
		batch = new SpriteBatch();

		f = new BitmapFont(Gdx.files.internal("data/font.fnt"));

		box = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("box2");

		tuts = new Sprite[5];
		for (int i = 0; i < tuts.length; i++) {
			tuts[i] = PrizmPathGame.getAssets()
					.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
					.createSprite("tut" + (i + 1));
			tuts[i].setPosition(
					(Gdx.graphics.getWidth() - tuts[i].getWidth()) / 2, 75);
		}
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
		// X X X X X 4 1 4 X X X X X
		// X X X X X 1 0 1 X X X X X
		// X X X X X 4 1 4 X X X X X
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
		// X X X X X X X X X X X X X
		// X X X X X 4 3 4 X X X X X
		// X X X X X 3 0 3 X X X X X
		// X X X X X 4 3 4 X X X X X
		// X X X X X X X X X X X X X
		case (8):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 1; i < 4; i++)
				for (int id = 5; id < 8; id++)
					collection[i][id].setPrizm(4, 0);
			collection[3][6].setPrizm(3, 1);
			collection[2][5].setPrizm(3, 1);
			collection[2][6].setPrizm(0, 0);
			collection[2][7].setPrizm(3, 1);
			collection[1][6].setPrizm(3, 1);
			maxClick[0] = 8;
			break;
		// X X X X 0 2 0 2 2 X X X X
		// X X X X 1 4 0 1 2 X X X X
		// X X X X 5 1 5 1 2 X X X X
		// X X X X 2 1 1 2 2 X X X X
		// X X X X 2 2 2 2 2 X X X X
		case (9):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(2, 0);
			collection[4][4].setPrizm(0, 1);
			collection[4][6].setPrizm(0, 1);
			collection[4][8].setPrizm(2, 1);
			collection[3][4].setPrizm(1, 0);
			collection[3][5].setPrizm(4, 1);
			collection[3][6].setPrizm(0, 0);
			collection[3][7].setPrizm(1, 1);
			collection[2][4].setPrizm(5, 1);
			collection[2][5].setPrizm(1, 0);
			collection[2][6].setPrizm(5, 0);
			collection[2][7].setPrizm(1, 0);
			collection[2][8].setPrizm(2, 1);
			collection[1][5].setPrizm(1, 1);
			collection[1][6].setPrizm(1, 0);
			collection[1][7].setPrizm(2, 1);
			collection[0][4].setPrizm(2, 1);
			collection[0][6].setPrizm(2, 1);
			collection[0][8].setPrizm(2, 1);
			maxClick[0] = 5;
			break;
		// X X X X 5 3 5 3 5 X X X X
		// X X X X 3 3 0 3 3 X X X X
		// X X X X 5 0 4 0 5 X X X X
		// X X X X 3 3 0 3 3 X X X X
		// X X X X 5 3 5 3 5 X X X X
		case (10):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(3, 1);
			collection[4][4].setPrizm(5, 1);
			collection[4][6].setPrizm(5, 1);
			collection[4][8].setPrizm(5, 1);
			collection[3][5].setPrizm(3, 0);
			collection[3][6].setPrizm(0, 1);
			collection[3][7].setPrizm(3, 0);
			collection[2][4].setPrizm(5, 1);
			collection[2][5].setPrizm(0, 1);
			collection[2][6].setPrizm(4, 1);
			collection[2][7].setPrizm(0, 1);
			collection[2][8].setPrizm(5, 1);
			collection[1][5].setPrizm(3, 0);
			collection[1][6].setPrizm(0, 1);
			collection[1][7].setPrizm(3, 0);
			collection[0][4].setPrizm(5, 1);
			collection[0][6].setPrizm(5, 1);
			collection[0][8].setPrizm(5, 1);
			maxClick[0] = 9;
			break;
		// X X X X 5 5 5 5 5 X X X X
		// X X X X 5 4 4 4 5 X X X X
		// X X X X 5 4 4 4 5 X X X X
		// X X X X 5 4 4 4 5 X X X X
		// X X X X 5 5 5 5 5 X X X X
		case (11):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(5, 0);
			collection[3][5].setPrizm(4, 0);
			collection[3][6].setPrizm(4, 0);
			collection[3][7].setPrizm(4, 0);
			collection[2][5].setPrizm(4, 0);
			collection[2][6].setPrizm(4, 2);
			collection[2][7].setPrizm(4, 0);
			collection[1][5].setPrizm(4, 0);
			collection[1][6].setPrizm(4, 0);
			collection[1][7].setPrizm(4, 0);
			maxClick[0] = 1;
			break;
		// X X X X 4 1 1 1 4 X X X X
		// X X X X 4 4 4 4 4 X X X X
		// X X X X 4 3 3 3 4 X X X X
		// X X X X 4 4 4 4 4 X X X X
		// X X X X 4 1 1 1 4 X X X X
		case (12):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(4, 2);
			collection[4][5].setPrizm(1, 0);
			collection[3][5].setPrizm(4, 0);
			collection[2][5].setPrizm(3, 0);
			collection[1][5].setPrizm(4, 0);
			collection[0][5].setPrizm(1, 0);
			collection[4][6].setPrizm(1, 2);
			collection[2][6].setPrizm(3, 2);
			collection[0][6].setPrizm(1, 2);
			collection[4][7].setPrizm(1, 0);
			collection[3][7].setPrizm(4, 0);
			collection[2][7].setPrizm(3, 0);
			collection[1][7].setPrizm(4, 0);
			collection[0][7].setPrizm(1, 0);
			maxClick[0] = 9;
			break;
		// X X X X 4 3 3 4 5 X X X X
		// X X X X 3 2 1 4 5 X X X X
		// X X X X 2 5 5 2 5 X X X X
		// X X X X 3 1 0 4 5 X X X X
		// X X X X 3 1 1 3 5 X X X X
		case (13):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(3, 0);
			collection[4][4].setPrizm(4, 0);
			collection[4][7].setPrizm(4, 0);
			collection[4][8].setPrizm(5, 0);
			collection[3][5].setPrizm(2, 2);
			collection[3][6].setPrizm(1, 2);
			collection[3][7].setPrizm(4, 2);
			collection[3][8].setPrizm(5, 0);
			collection[2][4].setPrizm(2, 0);
			collection[2][5].setPrizm(5, 2);
			collection[2][6].setPrizm(5, 0);
			collection[2][7].setPrizm(2, 2);
			collection[2][8].setPrizm(5, 0);
			collection[1][5].setPrizm(1, 2);
			collection[1][6].setPrizm(0, 2);
			collection[1][7].setPrizm(4, 2);
			collection[1][8].setPrizm(5, 0);
			collection[0][5].setPrizm(1, 0);
			collection[0][6].setPrizm(1, 0);
			collection[0][7].setPrizm(3, 0);
			collection[0][8].setPrizm(5, 0);
			maxClick[0] = 8;
			break;
		// X X X X 5 3 4 2 4 X X X X
		// X X X X 5 5 0 4 4 X X X X
		// X X X X 4 4 1 4 4 X X X X
		// X X X X 4 4 0 5 5 X X X X
		// X X X X 4 2 4 3 5 X X X X
		case (14):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(4, 2);
			collection[4][4].setPrizm(5, 2);
			collection[4][5].setPrizm(3, 2);
			collection[4][7].setPrizm(2, 2);
			collection[3][4].setPrizm(5, 2);
			collection[3][5].setPrizm(5, 2);
			collection[3][6].setPrizm(0, 1);
			collection[2][6].setPrizm(1, 0);
			collection[1][6].setPrizm(0, 1);
			collection[1][7].setPrizm(5, 2);
			collection[1][8].setPrizm(5, 2);
			collection[0][5].setPrizm(2, 2);
			collection[0][7].setPrizm(3, 2);
			collection[0][8].setPrizm(5, 2);
			maxClick[0] = 8;
			break;
		// X X X X 1 2 1 3 3 X X X X
		// X X X X 5 1 3 3 1 X X X X
		// X X X X 0 5 3 5 0 X X X X
		// X X X X 1 3 3 1 5 X X X X
		// X X X X 3 3 1 2 1 X X X X
		case (15):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(3, 0);
			collection[4][4].setPrizm(1, 1);
			collection[4][5].setPrizm(2, 1);
			collection[4][6].setPrizm(1, 1);
			collection[4][7].setPrizm(3, 1);
			collection[4][8].setPrizm(3, 1);
			collection[3][4].setPrizm(5, 0);
			collection[3][5].setPrizm(1, 0);
			collection[3][8].setPrizm(1, 0);
			collection[2][4].setPrizm(0, 2);
			collection[2][5].setPrizm(5, 2);
			collection[2][6].setPrizm(3, 2);
			collection[2][7].setPrizm(5, 2);
			collection[2][8].setPrizm(0, 2);
			collection[1][8].setPrizm(5, 0);
			collection[1][7].setPrizm(1, 0);
			collection[1][4].setPrizm(1, 0);
			collection[0][8].setPrizm(1, 1);
			collection[0][7].setPrizm(2, 1);
			collection[0][6].setPrizm(1, 1);
			collection[0][5].setPrizm(3, 1);
			collection[0][4].setPrizm(3, 1);
			maxClick[0] = 16;
			break;
		// X X X X 5 5 4 5 5 X X X X
		// X X X X 5 5 4 5 5 X X X X
		// X X X X 4 4 4 4 4 X X X X
		// X X X X 5 5 4 5 5 X X X X
		// X X X X 5 5 4 5 5 X X X X
		case (16):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(1, 0);
			collection[4][6].setPrizm(0, 0);
			collection[3][6].setPrizm(0, 0);
			collection[2][4].setPrizm(0, 0);
			collection[2][5].setPrizm(0, 0);
			collection[2][6].setPrizm(0, 3);
			collection[2][7].setPrizm(0, 0);
			collection[2][8].setPrizm(0, 0);
			collection[1][6].setPrizm(0, 0);
			collection[0][6].setPrizm(0, 0);
			maxClick[0] = 1;
			break;
		// X X X X 3 2 3 2 3 X X X X
		// X X X X 3 2 1 2 3 X X X X
		// X X X X 2 5 5 5 2 X X X X
		// X X X X 3 2 1 2 3 X X X X
		// X X X X 3 2 3 2 3 X X X X
		case (17):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(3, 0);
			collection[4][5].setPrizm(2, 0);
			collection[4][7].setPrizm(2, 0);
			collection[3][5].setPrizm(2, 0);
			collection[3][6].setPrizm(1, 0);
			collection[3][7].setPrizm(2, 0);
			collection[2][4].setPrizm(2, 0);
			collection[2][5].setPrizm(5, 3);
			collection[2][6].setPrizm(5, 0);
			collection[2][7].setPrizm(5, 3);
			collection[2][8].setPrizm(2, 0);
			collection[1][5].setPrizm(2, 0);
			collection[1][6].setPrizm(1, 0);
			collection[1][7].setPrizm(2, 0);
			collection[0][5].setPrizm(2, 0);
			collection[0][7].setPrizm(2, 0);
			maxClick[0] = 4;
			break;
		// X X X X 4 4 3 4 4 X X X X
		// X X X X 3 3 0 3 3 X X X X
		// X X X X 4 1 4 1 4 X X X X
		// X X X X 3 2 4 2 3 X X X X
		// X X X X 1 0 5 0 1 X X X X
		case (18):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(3, 0);
			collection[4][4].setPrizm(4, 0);
			collection[4][5].setPrizm(4, 3);
			collection[4][6].setPrizm(3, 3);
			collection[4][7].setPrizm(4, 3);
			collection[4][8].setPrizm(4, 0);
			collection[3][6].setPrizm(0, 3);
			collection[2][4].setPrizm(4, 0);
			collection[2][5].setPrizm(1, 0);
			collection[2][6].setPrizm(4, 0);
			collection[2][7].setPrizm(1, 0);
			collection[2][8].setPrizm(4, 0);
			collection[1][5].setPrizm(2, 0);
			collection[1][6].setPrizm(4, 3);
			collection[1][7].setPrizm(2, 0);
			collection[0][4].setPrizm(1, 0);
			collection[0][5].setPrizm(0, 3);
			collection[0][6].setPrizm(5, 3);
			collection[0][7].setPrizm(0, 3);
			collection[0][8].setPrizm(1, 0);
			maxClick[0] = 8;
			break;
		// X X X X 5 4 5 4 5 X X X X
		// X X X X 4 5 2 5 4 X X X X
		// X X X X 5 2 5 2 5 X X X X
		// X X X X 4 5 2 5 4 X X X X
		// X X X X 5 4 5 4 5 X X X X
		case (19):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(5, 0);
			collection[4][5].setPrizm(4, 0);
			collection[4][7].setPrizm(4, 0);
			collection[3][4].setPrizm(4, 0);
			collection[3][6].setPrizm(2, 1);
			collection[3][8].setPrizm(4, 0);
			collection[2][5].setPrizm(2, 1);
			collection[2][6].setPrizm(5, 3);
			collection[2][7].setPrizm(2, 1);
			collection[1][4].setPrizm(4, 0);
			collection[1][6].setPrizm(2, 1);
			collection[1][8].setPrizm(4, 0);
			collection[0][5].setPrizm(4, 0);
			collection[0][7].setPrizm(4, 0);
			maxClick[0] = 10;
			break;
		// X X X X 3 5 0 5 3 X X X X
		// X X X X 1 1 4 1 1 X X X X
		// X X X X 3 5 2 5 3 X X X X
		// X X X X 2 3 4 3 2 X X X X
		// X X X X 5 5 2 5 5 X X X X
		case (20):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(5, 0);
			collection[4][4].setPrizm(3, 0);
			collection[4][6].setPrizm(0, 0);
			collection[4][8].setPrizm(3, 0);
			collection[3][4].setPrizm(1, 0);
			collection[3][5].setPrizm(1, 1);
			collection[3][6].setPrizm(4, 2);
			collection[3][7].setPrizm(1, 1);
			collection[3][8].setPrizm(1, 0);
			collection[2][4].setPrizm(3, 0);
			collection[2][5].setPrizm(5, 2);
			collection[2][6].setPrizm(2, 3);
			collection[2][7].setPrizm(5, 2);
			collection[2][8].setPrizm(3, 0);
			collection[1][4].setPrizm(2, 0);
			collection[1][5].setPrizm(3, 1);
			collection[1][6].setPrizm(4, 2);
			collection[1][7].setPrizm(3, 1);
			collection[1][8].setPrizm(2, 0);
			collection[0][6].setPrizm(2, 0);
			maxClick[0] = 13;
			break;
		// X X X X 4 5 5 5 4 X X X X
		// X X X X 5 4 5 4 5 X X X X
		// X X X X 5 5 4 5 5 X X X X
		// X X X X 5 4 5 4 5 X X X X
		// X X X X 4 5 5 5 4 X X X X
		case (21):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(2, 0);
			collection[4][4].setPrizm(1, 0);
			collection[4][8].setPrizm(1, 0);
			collection[3][5].setPrizm(1, 0);
			collection[3][7].setPrizm(1, 0);
			collection[2][6].setPrizm(1, 4);
			collection[1][5].setPrizm(1, 0);
			collection[1][7].setPrizm(1, 0);
			collection[0][4].setPrizm(1, 0);
			collection[0][8].setPrizm(1, 0);
			maxClick[0] = 1;
			break;
		// X X X X 4 3 3 3 4 X X X X
		// X X X X 3 3 1 3 3 X X X X
		// X X X X 3 1 0 1 3 X X X X
		// X X X X 3 3 1 3 3 X X X X
		// X X X X 4 3 3 3 4 X X X X
		case (22):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(3, 0);
			collection[4][4].setPrizm(4, 0);
			collection[4][6].setPrizm(3, 0);
			collection[4][8].setPrizm(4, 0);
			collection[3][5].setPrizm(3, 4);
			collection[3][6].setPrizm(1, 4);
			collection[3][7].setPrizm(3, 4);
			collection[2][5].setPrizm(1, 4);
			collection[2][6].setPrizm(0, 0);
			collection[2][7].setPrizm(1, 4);
			collection[1][5].setPrizm(3, 4);
			collection[1][6].setPrizm(1, 4);
			collection[1][7].setPrizm(3, 4);
			collection[0][4].setPrizm(4, 0);
			collection[0][8].setPrizm(4, 0);
			maxClick[0] = 9;
			break;
		// X X X X 3 3 4 3 3 X X X X
		// X X X X 4 2 3 2 4 X X X X
		// X X X X 2 1 1 1 2 X X X X
		// X X X X 2 0 2 0 2 X X X X
		// X X X X 1 2 0 2 1 X X X X
		case (23):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(3, 0);
			collection[4][6].setPrizm(4, 4);
			collection[3][4].setPrizm(4, 0);
			collection[3][5].setPrizm(2, 2);
			collection[3][6].setPrizm(3, 4);
			collection[3][7].setPrizm(2, 2);
			collection[3][8].setPrizm(4, 0);
			collection[2][4].setPrizm(2, 0);
			collection[2][5].setPrizm(1, 0);
			collection[2][6].setPrizm(1, 4);
			collection[2][7].setPrizm(1, 0);
			collection[2][8].setPrizm(2, 0);
			collection[1][4].setPrizm(2, 0);
			collection[1][5].setPrizm(0, 2);
			collection[1][6].setPrizm(2, 4);
			collection[1][7].setPrizm(0, 2);
			collection[1][8].setPrizm(2, 0);
			collection[0][4].setPrizm(1, 0);
			collection[0][5].setPrizm(2, 0);
			collection[0][6].setPrizm(0, 4);
			collection[0][7].setPrizm(2, 0);
			collection[0][8].setPrizm(1, 0);
			maxClick[0] = 11;
			break;
		// X X X X 4 0 5 0 2 X X X X
		// X X X X 3 1 3 0 3 X X X X
		// X X X X 3 3 5 4 0 X X X X
		// X X X X 3 1 3 0 3 X X X X
		// X X X X 4 0 5 0 2 X X X X
		case (24):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(0, 0);
			collection[4][4].setPrizm(4, 0);
			collection[4][6].setPrizm(5, 4);
			collection[4][8].setPrizm(2, 0);
			collection[3][4].setPrizm(3, 0);
			collection[3][5].setPrizm(1, 1);
			collection[3][6].setPrizm(3, 4);
			collection[3][7].setPrizm(0, 1);
			collection[3][8].setPrizm(3, 0);
			collection[2][4].setPrizm(3, 2);
			collection[2][5].setPrizm(3, 2);
			collection[2][6].setPrizm(5, 4);
			collection[2][7].setPrizm(4, 2);
			collection[2][8].setPrizm(0, 2);
			collection[1][4].setPrizm(3, 0);
			collection[1][5].setPrizm(1, 1);
			collection[1][6].setPrizm(3, 4);
			collection[1][7].setPrizm(0, 1);
			collection[1][8].setPrizm(3, 0);
			collection[0][4].setPrizm(4, 0);
			collection[0][6].setPrizm(5, 4);
			collection[0][8].setPrizm(2, 0);
			maxClick[0] = 11;
			break;
		// X X X X 4 2 5 4 2 X X X X
		// X X X X 3 1 2 0 1 X X X X
		// X X X X 3 1 1 0 1 X X X X
		// X X X X 5 0 4 2 3 X X X X
		// X X X X 4 4 5 5 4 X X X X
		case (25):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(1, 0);
			collection[4][4].setPrizm(4, 1);
			collection[4][5].setPrizm(2, 3);
			collection[4][6].setPrizm(5, 4);
			collection[4][7].setPrizm(4, 2);
			collection[4][8].setPrizm(2, 0);
			collection[3][4].setPrizm(3, 1);
			collection[3][5].setPrizm(1, 3);
			collection[3][6].setPrizm(2, 4);
			collection[3][7].setPrizm(0, 2);
			collection[2][4].setPrizm(3, 1);
			collection[2][5].setPrizm(1, 3);
			collection[2][6].setPrizm(1, 4);
			collection[2][7].setPrizm(0, 2);
			collection[1][4].setPrizm(5, 1);
			collection[1][5].setPrizm(0, 3);
			collection[1][6].setPrizm(4, 4);
			collection[1][7].setPrizm(2, 2);
			collection[1][8].setPrizm(3, 0);
			collection[0][4].setPrizm(4, 1);
			collection[0][5].setPrizm(4, 3);
			collection[0][6].setPrizm(5, 4);
			collection[0][7].setPrizm(5, 2);
			collection[0][8].setPrizm(4, 0);
			maxClick[0] = 13;
			break;
		// X X X 0 5 3 3 3 4 4 X X X
		// X X X 0 4 3 2 2 5 4 X X X
		// X X X 0 2 0 1 0 4 4 X X X
		// X X X 0 4 2 3 2 5 4 X X X
		// X X X 5 4 3 2 3 4 4 X X X
		case (26):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 3; id < 10; id++)
					collection[i][id].setPrizm(4, 0);
			collection[4][3].setPrizm(0, 0);
			collection[4][4].setPrizm(5, 1);
			collection[4][5].setPrizm(3, 3);
			collection[4][6].setPrizm(3, 4);
			collection[4][7].setPrizm(3, 3);
			collection[4][8].setPrizm(4, 1);
			collection[3][3].setPrizm(0, 0);
			collection[3][4].setPrizm(4, 1);
			collection[3][5].setPrizm(3, 2);
			collection[3][6].setPrizm(2, 2);
			collection[3][7].setPrizm(2, 2);
			collection[3][8].setPrizm(5, 1);
			collection[2][3].setPrizm(0, 0);
			collection[2][4].setPrizm(2, 1);
			collection[2][5].setPrizm(0, 2);
			collection[2][6].setPrizm(1, 0);
			collection[2][7].setPrizm(0, 2);
			collection[2][8].setPrizm(4, 1);
			collection[1][3].setPrizm(0, 0);
			collection[1][4].setPrizm(4, 1);
			collection[1][5].setPrizm(2, 2);
			collection[1][6].setPrizm(3, 2);
			collection[1][7].setPrizm(2, 2);
			collection[1][8].setPrizm(5, 1);
			collection[0][3].setPrizm(5, 0);
			collection[0][4].setPrizm(4, 1);
			collection[0][5].setPrizm(3, 3);
			collection[0][6].setPrizm(2, 4);
			collection[0][7].setPrizm(3, 3);
			collection[0][8].setPrizm(4, 1);
			maxClick[0] = 14;
			break;
		// X X 5 3 1 3 4 3 1 3 5 X X
		// X X 4 4 5 4 1 4 5 4 4 X X
		// X X 4 4 1 0 0 0 1 4 4 X X
		// X X 0 0 0 0 0 0 0 0 0 X X
		// X X 0 4 4 4 0 4 4 4 0 X X
		case (27):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 2; id < 11; id++)
					collection[i][id].setPrizm(4, 2);
			collection[4][2].setPrizm(5, 1);
			collection[4][3].setPrizm(3, 2);
			collection[4][4].setPrizm(1, 3);
			collection[4][5].setPrizm(3, 4);
			collection[4][6].setPrizm(4, 0);
			collection[4][7].setPrizm(3, 4);
			collection[4][8].setPrizm(1, 3);
			collection[4][9].setPrizm(3, 2);
			collection[4][10].setPrizm(5, 1);
			collection[3][2].setPrizm(4, 1);
			collection[3][4].setPrizm(5, 3);
			collection[3][5].setPrizm(4, 4);
			collection[3][6].setPrizm(2, 0);
			collection[3][7].setPrizm(4, 4);
			collection[3][8].setPrizm(5, 3);
			collection[3][10].setPrizm(4, 1);
			collection[2][2].setPrizm(4, 1);
			collection[2][4].setPrizm(1, 3);
			collection[2][5].setPrizm(0, 4);
			collection[2][6].setPrizm(0, 0);
			collection[2][7].setPrizm(0, 4);
			collection[2][8].setPrizm(1, 3);
			collection[2][10].setPrizm(4, 1);
			collection[1][2].setPrizm(0, 1);
			collection[1][3].setPrizm(0, 2);
			collection[1][4].setPrizm(0, 3);
			collection[1][5].setPrizm(0, 4);
			collection[1][6].setPrizm(0, 0);
			collection[1][7].setPrizm(0, 4);
			collection[1][8].setPrizm(0, 3);
			collection[1][9].setPrizm(0, 2);
			collection[1][10].setPrizm(0, 1);
			collection[0][2].setPrizm(0, 1);
			collection[0][4].setPrizm(4, 3);
			collection[0][5].setPrizm(4, 4);
			collection[0][6].setPrizm(0, 0);
			collection[0][7].setPrizm(4, 4);
			collection[0][8].setPrizm(4, 3);
			collection[0][10].setPrizm(0, 1);
			maxClick[0] = 16;
			break;
		// X 0 0 0 5 5 5 5 5 0 0 0 X
		// X 0 0 0 3 0 4 0 3 0 0 0 X
		// X 0 0 0 0 4 0 4 0 0 0 0 X
		// X 0 0 0 3 1 4 1 3 0 0 0 X
		// X 0 0 0 0 5 1 5 0 0 0 0 X
		case (28):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 1; id < 12; id++)
					collection[i][id].setPrizm(0, 0);
			collection[4][1].setPrizm(0, 2);
			collection[4][2].setPrizm(0, 2);
			collection[4][3].setPrizm(0, 2);
			collection[4][4].setPrizm(5, 2);
			collection[4][5].setPrizm(5, 2);
			collection[4][6].setPrizm(5, 4);
			collection[4][7].setPrizm(5, 2);
			collection[4][8].setPrizm(5, 2);
			collection[4][9].setPrizm(0, 2);
			collection[4][10].setPrizm(0, 2);
			collection[4][11].setPrizm(0, 2);
			collection[3][4].setPrizm(3, 0);
			collection[3][6].setPrizm(4, 3);
			collection[3][8].setPrizm(3, 0);
			collection[2][5].setPrizm(4, 0);
			collection[2][7].setPrizm(4, 0);
			collection[1][4].setPrizm(3, 0);
			collection[1][5].setPrizm(1, 0);
			collection[1][6].setPrizm(4, 3);
			collection[1][7].setPrizm(1, 0);
			collection[1][8].setPrizm(3, 0);
			collection[0][1].setPrizm(0, 1);
			collection[0][2].setPrizm(0, 1);
			collection[0][3].setPrizm(0, 1);
			collection[0][4].setPrizm(0, 1);
			collection[0][5].setPrizm(5, 1);
			collection[0][6].setPrizm(1, 4);
			collection[0][7].setPrizm(5, 1);
			collection[0][8].setPrizm(0, 1);
			collection[0][9].setPrizm(0, 1);
			collection[0][10].setPrizm(0, 1);
			collection[0][11].setPrizm(0, 1);
			maxClick[0] = 16;
			break;
		// 0 3 2 0 0 2 2 2 0 0 2 3 0
		// 0 5 2 4 4 2 4 2 4 4 2 5 0
		// 3 3 3 5 0 0 0 0 0 5 3 3 3
		// 0 5 2 4 4 2 4 2 4 4 2 5 0
		// 0 3 2 0 0 2 2 2 0 0 2 3 0
		case (29):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id].setPrizm(0, 0);
			collection[4][1].setPrizm(3, 0);
			collection[4][2].setPrizm(2, 0);
			collection[4][5].setPrizm(2, 0);
			collection[4][6].setPrizm(2, 0);
			collection[4][7].setPrizm(2, 0);
			collection[4][10].setPrizm(2, 0);
			collection[4][11].setPrizm(3, 0);
			collection[3][0].setPrizm(0, 1);
			collection[3][1].setPrizm(5, 1);
			collection[3][2].setPrizm(2, 1);
			collection[3][3].setPrizm(4, 1);
			collection[3][4].setPrizm(4, 1);
			collection[3][5].setPrizm(2, 1);
			collection[3][6].setPrizm(4, 1);
			collection[3][7].setPrizm(2, 1);
			collection[3][8].setPrizm(4, 1);
			collection[3][9].setPrizm(4, 1);
			collection[3][10].setPrizm(2, 1);
			collection[3][11].setPrizm(5, 1);
			collection[3][12].setPrizm(0, 1);
			collection[2][0].setPrizm(3, 4);
			collection[2][1].setPrizm(3, 3);
			collection[2][2].setPrizm(3, 3);
			collection[2][3].setPrizm(5, 4);
			collection[2][4].setPrizm(0, 4);
			collection[2][5].setPrizm(0, 3);
			collection[2][6].setPrizm(0, 0);
			collection[2][7].setPrizm(0, 3);
			collection[2][8].setPrizm(0, 4);
			collection[2][9].setPrizm(5, 4);
			collection[2][10].setPrizm(3, 3);
			collection[2][11].setPrizm(3, 3);
			collection[2][12].setPrizm(3, 4);
			collection[1][0].setPrizm(0, 2);
			collection[1][1].setPrizm(5, 2);
			collection[1][2].setPrizm(2, 2);
			collection[1][3].setPrizm(4, 2);
			collection[1][4].setPrizm(4, 2);
			collection[1][5].setPrizm(2, 2);
			collection[1][6].setPrizm(4, 2);
			collection[1][7].setPrizm(2, 2);
			collection[1][8].setPrizm(4, 2);
			collection[1][9].setPrizm(4, 2);
			collection[1][10].setPrizm(2, 2);
			collection[1][11].setPrizm(5, 2);
			collection[1][12].setPrizm(0, 2);
			collection[0][1].setPrizm(3, 0);
			collection[0][2].setPrizm(2, 0);
			collection[0][5].setPrizm(2, 0);
			collection[0][6].setPrizm(2, 0);
			collection[0][7].setPrizm(2, 0);
			collection[0][10].setPrizm(2, 0);
			collection[0][11].setPrizm(3, 0);
			maxClick[0] = 21;
			break;
		// 0 5 5 3 1 2 2 2 1 3 5 5 0
		// 0 5 5 1 3 5 4 5 3 1 5 5 0
		// 0 5 3 1 1 1 0 1 1 1 3 5 0
		// 0 5 5 1 3 5 4 5 3 1 5 5 0
		// 0 5 5 3 1 2 2 2 1 3 5 5 0
		case (30):
			for (int i = 0; i < collection.length; i++)
				for (int id = 0; id < collection[i].length; id++)
					collection[i][id] = new Tile(new Vector2((60 * id) + 10,
							(60 * i) + 80), false);
			for (int i = 0; i < collection.length; i++)
				for (int id = 4; id < 9; id++)
					collection[i][id].setPrizm(0, 0);
			collection[4][1].setPrizm(5, 1);
			collection[4][2].setPrizm(5, 2);
			collection[4][3].setPrizm(3, 3);
			collection[4][4].setPrizm(1, 4);
			collection[4][5].setPrizm(2, 0);
			collection[4][6].setPrizm(2, 4);
			collection[4][7].setPrizm(2, 0);
			collection[4][8].setPrizm(1, 4);
			collection[4][9].setPrizm(3, 3);
			collection[4][10].setPrizm(5, 2);
			collection[4][11].setPrizm(5, 1);
			collection[3][1].setPrizm(5, 1);
			collection[3][2].setPrizm(5, 2);
			collection[3][3].setPrizm(1, 3);
			collection[3][4].setPrizm(3, 4);
			collection[3][5].setPrizm(5, 0);
			collection[3][6].setPrizm(4, 4);
			collection[3][7].setPrizm(5, 0);
			collection[3][8].setPrizm(3, 4);
			collection[3][9].setPrizm(1, 3);
			collection[3][10].setPrizm(5, 2);
			collection[3][11].setPrizm(5, 1);
			collection[2][1].setPrizm(5, 1);
			collection[2][2].setPrizm(3, 2);
			collection[2][3].setPrizm(1, 3);
			collection[2][4].setPrizm(1, 4);
			collection[2][5].setPrizm(1, 0);
			collection[2][6].setPrizm(0, 4);
			collection[2][7].setPrizm(1, 0);
			collection[2][8].setPrizm(1, 4);
			collection[2][9].setPrizm(1, 3);
			collection[2][10].setPrizm(3, 2);
			collection[2][11].setPrizm(5, 1);
			collection[1][1].setPrizm(5, 1);
			collection[1][2].setPrizm(5, 2);
			collection[1][3].setPrizm(1, 3);
			collection[1][4].setPrizm(3, 4);
			collection[1][5].setPrizm(5, 0);
			collection[1][6].setPrizm(4, 4);
			collection[1][7].setPrizm(5, 0);
			collection[1][8].setPrizm(3, 4);
			collection[1][9].setPrizm(1, 3);
			collection[1][10].setPrizm(5, 2);
			collection[1][11].setPrizm(5, 1);
			collection[0][1].setPrizm(5, 1);
			collection[0][2].setPrizm(5, 2);
			collection[0][3].setPrizm(3, 3);
			collection[0][4].setPrizm(1, 4);
			collection[0][5].setPrizm(2, 0);
			collection[0][6].setPrizm(2, 4);
			collection[0][7].setPrizm(2, 0);
			collection[0][8].setPrizm(1, 4);
			collection[0][9].setPrizm(3, 3);
			collection[0][10].setPrizm(5, 2);
			collection[0][11].setPrizm(5, 1);
			maxClick[0] = 28;
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
		else {
			if ((level == 1 || level == 6 || level == 11 || level == 16 || level == 21)
					&& curClick >= 1)
				lose();
			else if (curClick >= maxClick[maxClickIndex]
					&& maxClickIndex < maxClick.length - 1)
				maxClickIndex++;
			else if (curClick >= maxClick[3])
				lose();
		}
		click.setText("Clicks: " + curClick + "/" + maxClick[maxClickIndex]);
	}

	private void win() {
		gameState = 3;
		restart.setDisabled(false);
		restart.setVisible(true);
		if (level < 30) {
			next.setDisabled(false);
			next.setVisible(true);
		}
		home.setDisabled(false);
		home.setVisible(true);
		if (level > 0) {
			if (maxClickIndex == 0)
				PrizmPathGame.setLevelData((byte) 4, level - 1);
			else if (maxClickIndex == 1 && PrizmPathGame.getLevelData(level - 1) < 4)
				PrizmPathGame.setLevelData((byte) 3, level - 1);
			else if (maxClickIndex == 2 && PrizmPathGame.getLevelData(level - 1) < 3)
				PrizmPathGame.setLevelData((byte) 2, level - 1);
		}
		if (level < 30 && PrizmPathGame.getLevelData(level) == 0)
			PrizmPathGame.setLevelData((byte) 1, level);
		String s = "LEVEL COMPLETED\n\n";
		s += "Clicks: " + curClick + "/" + maxClick[maxClickIndex] + "\n";
		if (maxClickIndex == 0)
			s += "GOLD";
		else if (maxClickIndex == 1)
			s += "SILVER";
		else if (maxClickIndex == 2)
			s += "BRONZE";
		info.setText(s);
		info.setVisible(true);
		PrizmPathGame.assets.get(PrizmPathGame.SFX_PATH + "win.mp3",
				Sound.class).play();
	}

	private void lose() {
		gameState = 2;
		restart.setDisabled(false);
		restart.setVisible(true);
		home.setDisabled(false);
		home.setVisible(true);
		info.setText("GAMEOVER\n\nMaximum Number of Clicks\nExceeded");
		info.setVisible(true);
		PrizmPathGame.assets.get(PrizmPathGame.SFX_PATH + "lose.mp3",
				Sound.class).play();
	}

	private void toStart() {
		p.setScreen(new StartScreen(p));
	}

	private void toNextLevel() {
		Random ran = new Random();
		for (int lx = ran.nextInt(15); lx > 0; lx--)
			PrizmPathGame.changeSong();
		p.setScreen(new GameScreen(p, level + 1));
	}

	private void replayLevel() {
		p.setScreen(new GameScreen(p, level));
	}
}
// © Hunter Heidenreich 2014