package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.heidenreich.prizmpath.PrizmPathGame;

public class LevelScreen implements Screen {

	private BitmapFont f;
	private ImageButton back;
	private PrizmPathGame p;
	private Skin skin;
	private SpriteBatch batch;
	private Stage stage;
	public static final String BUTTON_TEXTURE = "data/textures/button.pack";
	private TextButton[][] levels;
	private TextureAtlas buttonAtlas;
	private Vector2 buttonSize;

	// Constructs the LevelScreen
	public LevelScreen(PrizmPathGame p) {
		this.p = p;
		levels = new TextButton[1][1];
		buttonSize = new Vector2(30, 30);
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

	// Called when the window is resized
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage();
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		// Button style
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.getDrawable("buttonnormal");
		tbs.down = skin.getDrawable("buttonpressed");
		tbs.font = f;

		// Level Buttons
		for (int i = 0; i < levels.length; i++) {
			for (int id = 0; id < levels[i].length; id++) {
				levels[i][id] = new TextButton(
						"" + (i * levels[i].length) + id, tbs);
				levels[i][id].setSize(buttonSize.x, buttonSize.y);
				levels[i][id].setX((id * (buttonSize.x + 5)) + 10);
				levels[i][id].setY((i * (buttonSize.y + 5)) + 10);
				levels[i][id].addListener(new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						toGame();
					}
				});
			}
		}

		ImageButtonStyle imageStyle = new ImageButtonStyle();
		//imageStyle.imageUp = new Drawable();
		//imageStyle.imageDown = (Drawable) new Sprite();
		
		// Back button
		back = new ImageButton(imageStyle);
		back.setSize(buttonSize.x, buttonSize.y);
		back.setX(0);
		back.setY(0);
		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				toStart();
			}
		});

		// Adds the levels to the stage
		for (int i = 0; i < levels.length; i++) {
			for (int id = 0; id < levels[i].length; id++) {
				stage.addActor(levels[i][id]);
			}
		}
	}

	// Called when the screen is shown
	public void show() {
		batch = new SpriteBatch();

		buttonAtlas = PrizmPathGame.getAssets().get(BUTTON_TEXTURE,
				TextureAtlas.class);

		f = new BitmapFont(Gdx.files.internal("data/font.fnt"));

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
		skin.dispose();
	}

	// Sets the screen to the StartScreen
	private void toStart() {
		p.setScreen(new StartScreen(p));
	}

	// Sets the screen to the GameScreen
	private void toGame() {
		p.setScreen(new GameScreen(p));
	}
}
// © Hunter Heidenreich 2014