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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.heidenreich.prizmpath.PrizmPathGame;

public class StartScreen implements Screen {

	private BitmapFont f;
	private boolean delete;
	private Label choice;
	private Label title;
	private PrizmPathGame p;
	private Skin skin;
	private Sprite box;
	private SpriteBatch batch;
	private Stage dStage;
	private Stage stage;
	public static final String BUTTON_TEXTURE = "data/textures/button.pack";
	private TextButton credits;
	private TextButton options;
	private TextButton quit;
	private TextButton shop;
	private TextButton start;
	private TextButton yes;
	private TextButton no;
	private TextureAtlas buttonAtlas;
	private Vector2 buttonSize;

	// Constructs StartScreen
	public StartScreen(PrizmPathGame p) {
		this.p = p;
		buttonSize = new Vector2(300, 75);
		delete = false;
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
		batch.end();
		if (delete) {
			batch.begin();
			box.draw(batch);
			batch.end();

			dStage.act();
			batch.begin();
			dStage.draw();
			batch.end();
		} else {
			// Starts drawing the stage
			stage.act(delta);
			batch.begin();
			stage.draw();
			batch.end();
		}

	}

	// Called on screen resize
	public void resize(int width, int height) {
		// Initialize the stage
		if (stage == null)
			stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		if (dStage == null)
			dStage = new Stage();
		dStage.clear();
		// Button style
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.getDrawable("buttonnormal");
		tbs.down = skin.getDrawable("buttonpressed");
		tbs.font = f;

		// Start Button
		start = new TextButton("Start", tbs);
		start.setSize(buttonSize.x, buttonSize.y);
		start.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		start.setY((4 * buttonSize.y) + 15);
		start.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				toLevels();
			}
		});

		// Shop Button
		shop = new TextButton("Delete Save", tbs);
		shop.setSize(buttonSize.x, buttonSize.y);
		shop.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		shop.setY((3 * buttonSize.y) + 15);
		shop.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				toWarning();
			}
		});

		// Options Button
		options = new TextButton("Options", tbs);
		options.setSize(buttonSize.x, buttonSize.y);
		options.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		options.setY((2 * buttonSize.y) + 15);
		options.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				toOptions();
			}
		});

		// Credits Button
		credits = new TextButton("Credits", tbs);
		credits.setSize(buttonSize.x, buttonSize.y);
		credits.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		credits.setY((1 * buttonSize.y) + 15);
		credits.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				toCredits();
			}
		});

		// Quit Button
		quit = new TextButton("Quit", tbs);
		quit.setSize(buttonSize.x, buttonSize.y);
		quit.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		quit.setY((0 * buttonSize.y) + 15);
		quit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
			}
		});

		yes = new TextButton("Yes", tbs);
		yes.setSize(150, buttonSize.y);
		yes.setX((Gdx.graphics.getWidth()) / 2 - 180);
		yes.setY(170);
		yes.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				PrizmPathGame.deleteData();
				offWarning();
			}
		});
		yes.setDisabled(true);
		yes.setVisible(false);

		no = new TextButton("No", tbs);
		no.setSize(150, buttonSize.y);
		no.setX((Gdx.graphics.getWidth()) / 2 + 30);
		no.setY(170);
		no.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				offWarning();
			}
		});
		no.setDisabled(true);
		no.setVisible(false);

		// Title label
		LabelStyle ls = new LabelStyle(f, Color.WHITE);
		title = new Label("PrizmPath", ls);
		title.setX(0);
		title.setY(420);
		title.setWidth(Gdx.graphics.getWidth());
		title.setAlignment(Align.center);

		choice = new Label("Delete all your data?", ls);
		choice.setX(0);
		choice.setY(250);
		choice.setWidth(Gdx.graphics.getWidth());
		choice.setAlignment(Align.center);

		// Adds to stage
		stage.addActor(start);
		stage.addActor(shop);
		stage.addActor(options);
		stage.addActor(credits);
		stage.addActor(quit);
		stage.addActor(title);
		dStage.addActor(no);
		dStage.addActor(yes);
		dStage.addActor(choice);
	}

	protected void toWarning() {
		no.setVisible(true);
		no.setDisabled(false);
		yes.setVisible(true);
		yes.setDisabled(false);
		delete = true;
		Gdx.input.setInputProcessor(dStage);
	}

	protected void offWarning() {
		no.setVisible(false);
		no.setDisabled(true);
		yes.setVisible(false);
		yes.setDisabled(true);
		delete = false;
		Gdx.input.setInputProcessor(stage);
	}

	// Called when the screen is shown
	public void show() {
		batch = new SpriteBatch();

		buttonAtlas = PrizmPathGame.getAssets().get(BUTTON_TEXTURE,
				TextureAtlas.class);

		f = new BitmapFont(Gdx.files.internal("data/font.fnt"));

		box = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("box2");

		skin = new Skin();
		skin.addRegions(buttonAtlas);

		// Determines whether the song should be played
		if (PrizmPathGame.isMusicMute())
			PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
					.pause();
		else
			PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
					.play();
	}

	// Called when the screen isn't showing
	public void hide() {
		dispose();
	}

	// Called when the screen is paused
	public void pause() {
	}

	// Called when the screen is resumed
	public void resume() {
	}

	// Disposes of elements
	public void dispose() {
		f.dispose();
		skin.dispose();
		batch.dispose();
		stage.dispose();
		dStage.dispose();
	}

	// Sends to the OptionScreen
	private void toOptions() {
		p.setScreen(new OptionScreen(p));
	}

	// Sends to the CreditScreen
	private void toCredits() {
		p.setScreen(new CreditScreen(p));
	}

	// Sends to the LevelScreen
	private void toLevels() {
		p.setScreen(new LevelScreen(p));
	}
}
// © Hunter Heidenreich 2014