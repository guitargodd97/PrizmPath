package com.heidenreich.prizmpath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.heidenreich.prizmpath.PrizmPathGame;

public class SplashScreen implements Screen {
	private boolean reverse;
	private boolean hasPlayed;
	private float alpha;
	private float fade;
	private float timer;
	private int counter;
	private Music splash;
	private PrizmPathGame p;
	private SpriteBatch batch;
	private final String MUSIC_SOUND = "data/sound/music/HeidenreichSound.wav";
	private final String SFX_SOUND = "data/sound/sfx/yeahsfx.wav";

	// Constructs the SplashScreen
	public SplashScreen(PrizmPathGame p) {
		this.p = p;
		reverse = false;
		hasPlayed = false;
		alpha = 0;
		fade = 0.01f;
		timer = 0;
		counter = 0;
		Gdx.app.log(PrizmPathGame.getLog(), "Splash started");
	}

	// Updates the screen
	public void render(float delta) {
		if (PrizmPathGame.getAssets().update()) {
			// Clears the screen
			Gdx.gl.glClearColor(0, 0, 0, alpha);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			// Starts drawing the textures with new alphas
			batch.begin();
			Color c = batch.getColor();
			batch.setColor(c.r, c.b, c.g, 1);
			PrizmPathGame.splash.draw(batch);
			c = batch.getColor();
			batch.setColor(c.r, c.b, c.g, 1);
			if (timer >= 2.85 && timer <= 3.15)
				playSFX();
			batch.end();

			// Updates the alphas
			updateAlpha();
		}
	}

	// Called when the screen changes size
	public void resize(int width, int height) {

	}

	// Called on screen startup
	public void show() {
		// Initializes the batch
		batch = new SpriteBatch();

		Gdx.app.log(PrizmPathGame.getLog(), "Initialized images and sounds");
		// Sets up the sprites
		PrizmPathGame.splash
				.setPosition((Gdx.graphics.getWidth() - PrizmPathGame.splash
						.getWidth()) / 2,
						(Gdx.graphics.getHeight() - PrizmPathGame.splash
								.getHeight()) / 2);

		// Initializes the sounds
		splash = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_SOUND));
		splash.play();
		splash.setLooping(false);
	}

	// Called on hide
	public void hide() {
		dispose();
	}

	// Called on pause
	public void pause() {

	}

	// Called on resume
	public void resume() {

	}

	// Disposes of disposable assets
	public void dispose() {
		batch.dispose();
		splash.dispose();
	}

	// Plays the static noise
	public void playSFX() {
		// If the SFX hasn't been played yet
		if (!hasPlayed) {
			Gdx.audio.newSound(Gdx.files.internal(SFX_SOUND)).play();
			hasPlayed = true;
		}
	}

	// Updates the alpha of the images to create a fade-in fade-out
	private void updateAlpha() {
		PrizmPathGame.splash.setColor(1, 1, 1, alpha);
		if (counter % 3 == 0) {
			if (alpha < .99f && !reverse)
				alpha += fade;
			else if (alpha > 0.01f && reverse)
				alpha -= fade;
		}
		timer += fade;
		if (timer >= 2.99f && !reverse)
			reverse = true;
		if (reverse && alpha < 0.02f)
			toStartScreen();
	}

	private void toStartScreen() {
		Gdx.app.log(PrizmPathGame.getLog(), "Splash ended");
		p.setScreen(new StartScreen(p));
	}

}
// © Hunter Heidenreich 2014