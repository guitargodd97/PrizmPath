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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.heidenreich.prizmpath.PrizmPathGame;

public class LevelScreen implements Screen {

	private BitmapFont f;
	private ImageButton back;
	private Label title;
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
		levels = new TextButton[3][10];
		buttonSize = new Vector2(70, 70);
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

		TextButtonStyle tbsB = new TextButtonStyle();
		tbsB.up = skin.getDrawable("buttonnormalB");
		tbsB.down = skin.getDrawable("buttonpressedB");
		tbsB.font = f;

		TextButtonStyle tbsS = new TextButtonStyle();
		tbsS.up = skin.getDrawable("buttonnormalS");
		tbsS.down = skin.getDrawable("buttonpressedS");
		tbsS.font = f;

		TextButtonStyle tbsG = new TextButtonStyle();
		tbsG.up = skin.getDrawable("buttonnormalG");
		tbsG.down = skin.getDrawable("buttonpressedG");
		tbsG.font = f;

		// Level Buttons
		for (int i = 0; i < levels.length; i++) {
			for (int id = 0; id < levels[i].length; id++) {
				levels[i][id] = new TextButton(""
						+ (i * levels[i].length + id + 1), tbs);
				levels[i][id].setName("" + (i * levels[i].length + id + 1));
				levels[i][id].setSize(buttonSize.x, buttonSize.y);
				levels[i][id].setX((id * (buttonSize.x + 5)) + 30);
				levels[i][id].setY(Gdx.graphics.getHeight()
						- ((i * (buttonSize.y + 30)) + buttonSize.y + 120));
				/* if ((i * levels[i].length + id + 1) > 1) { */
				if (PrizmPathGame.getLevelData((i * levels[i].length + id)) < 1) {
					levels[i][id].setTouchable(Touchable.disabled);
					levels[i][id].setVisible(false);
				}
				if (PrizmPathGame.getLevelData((i * levels[i].length + id)) == 2)
					levels[i][id].setStyle(tbsB);
				else if (PrizmPathGame
						.getLevelData((i * levels[i].length + id)) == 3)
					levels[i][id].setStyle(tbsS);
				else if (PrizmPathGame
						.getLevelData((i * levels[i].length + id)) == 4)
					levels[i][id].setStyle(tbsG);
				levels[i][id].addListener(new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;
					}

					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						toGame(Integer.parseInt(event.getListenerActor()
								.getName()));
					}
				});
			}
		}

		ImageButtonStyle imageStyle = new ImageButtonStyle();
		imageStyle.imageUp = new SpriteDrawable(PrizmPathGame.homeButtons[0]);
		imageStyle.imageDown = new SpriteDrawable(PrizmPathGame.homeButtons[1]);

		// Title label
		LabelStyle ls = new LabelStyle(f, Color.WHITE);
		title = new Label("Levels", ls);
		title.setX(0);
		title.setY(420);
		title.setWidth(Gdx.graphics.getWidth());
		title.setAlignment(Align.center);

		// Adds the levels to the stage
		for (int i = 0; i < levels.length; i++) {
			for (int id = 0; id < levels[i].length; id++) {
				stage.addActor(levels[i][id]);
			}
		}
		stage.addActor(title);
		// stage.addActor(back);
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
	private void toGame(int i) {
		p.setScreen(new GameScreen(p, i));
	}
}
// © Hunter Heidenreich 2014