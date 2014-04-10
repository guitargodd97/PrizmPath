package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

	private Label title;
	private PrizmPathGame p;
	private Skin skin;
	private SpriteBatch batch;
	private Stage stage;
	private TextButton credits;
	private TextButton options;
	private TextButton quit;
	private TextButton shop;
	private TextButton start;
	private Vector2 buttonSize;*
	private Vector2 buttonLocation;*

	// Constructs StartScreen
	public StartScreen(PrizmPathGame p) {
		this.p = p;
		Gdx.app.log(PrizmPathGame.getLog(), "Start started");
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

		// Starts drawing the stage
		stage.act(delta);
		batch.begin();
		stage.draw();
		batch.end();

	}

	// Called on screen resize
	public void resize(int width, int height) {
		// Initialize the stage
		if (stage == null)
			stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		// Button style
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.getDrawable("buttonnormal");
		tbs.down = skin.getDrawable("buttonpressed");
		tbs.font = b;

		// Start Button
		start = new TextButton("Start", tbs);
		start.setSize(buttonSize.x, buttonSize.y);
		start.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		// start.setY(y);
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
		shop = new TextButton("Shop", tbs);
		shop.setSize(buttonSize.x, buttonSize.y);
		shop.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		// shop.setY(y);
		shop.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				toShop();
			}
		});

		// Options Button
		options = new TextButton("Options", tbs);
		options.setSize(buttonSize.x, buttonSize.y);
		options.setX(Gdx.graphics.getWidth() / 2 - buttonSize.x / 2);
		// options.setY(y);
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
		// credits.setY(y);
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
		// quit.setY(y);
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

		// Title label
		LabelStyle ls = new LabelStyle(f, Color.WHITE);
		title = new Label("PrizmPath", ls);
		title.setX(0);
		// title.setY(400);
		title.setWidth(Gdx.graphics.getWidth());
		title.setAlignment(Align.center);

		// Adds to stage
		stage.addActor(start);
		stage.addActor(shop);
		stage.addActor(options);
		stage.addActor(credits);
		stage.addActor(quit);
		stage.addActor(title);
	}

	//Called when the screen is shown
	public void show() {
		batch = new SpriteBatch();
		
		buttonAtlas = PrizmPathGame.getAssets().get(BUTTON_TEXTURE, TextureAtlas.class);
		
		skin = new Skin();
		skin.addRegions(buttonAtlas);
	}

	public void hide() {
		dispose();
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
