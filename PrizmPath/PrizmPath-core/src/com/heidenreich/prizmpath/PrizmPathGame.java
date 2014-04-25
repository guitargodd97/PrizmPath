package com.heidenreich.prizmpath;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.heidenreich.prizmpath.screens.SplashScreen;
import com.heidenreich.prizmpath.screens.StartScreen;

public class PrizmPathGame extends Game {
	public static ApplicationType appType;
	public static AssetManager assets;
	public static boolean musicMute;
	public static boolean sfxMute;
	public static byte[] levelData;
	public static byte[] optionData;
	public static int curBackground;
	public static int curColorpack;
	public static int curSong;
	public static int curSoundpack;
	private int loaded;
	public static Music[][] soundpacks;
	public static Sprite splash;
	public static Sprite[] backgrounds;
	public static Sprite[] homeButtons;
	public static Sprite[] pauseButtons;
	public static Sprite[] playButtons;
	public static Sprite[] restartButtons;
	public static Sprite[][][][] colorpacks;
	public static final String log = "PrizmPath";
	public static final String TEXTURE_PATH = "data/textures/texture.atlas";
	public static final String version = "Alpha 1.0";

	/*
	 * TO DO LIST: Write loadData() - Write loadResources() - Write savaData()
	 */
	// Creates the game
	public void create() {
		// Logs the game type for control modification
		appType = Gdx.app.getType();

		loaded = 0;
		// Loads data
		loadData();

		// Sets up the assets
		PrizmPathGame.setAssetManager(new AssetManager());
		loadResources();

	}

	// Disposes of the game
	public void dispose() {
		saveData();
		super.dispose();
		PrizmPathGame.getAssets().dispose();
	}

	// Updates the game
	public void render() {
		super.render();
		if (loaded == 2) {
			loaded++;
			// Starts the game with the splashscreen
			this.setScreen(new SplashScreen(this));
		} else if (loaded == 1) {
			setupResources();
			loaded++;
		} else if (PrizmPathGame.getAssets().update())
			loaded++;
	}

	// Accounts for resizing of the window
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	// Pauses rendering
	public void pause() {
		super.pause();
	}

	// Resumes rendering
	public void resume() {
		super.resume();
	}

	// Loads the resources
	public void loadResources() {
		/*
		 * Brief Description: Loads all the resources into the AssetManager
		 * 
		 * NOTE - May need multiple instances of AssetManagers for multiple file
		 * types, such as textures and audio.
		 */
		// Set the type of manager
		PrizmPathGame.getAssets().setLoader(TiledMap.class,
				new TmxMapLoader(new InternalFileHandleResolver()));
		PrizmPathGame.getAssets().load(PrizmPathGame.TEXTURE_PATH,
				TextureAtlas.class);
		PrizmPathGame.getAssets().load(StartScreen.BUTTON_TEXTURE,
				TextureAtlas.class);
		PrizmPathGame.getAssets().finishLoading();
		Gdx.app.log(getLog(), "Resources loaded");
	}

	// Sets up the resources()
	public void setupResources() {
		// Sets up the textures
		PrizmPathGame.splash = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("splash");
		backgrounds = new Sprite[3];
		backgrounds[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("background0");
		backgrounds[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("background1");
		backgrounds[2] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("background2");

		homeButtons = new Sprite[2];
		homeButtons[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("homebuttonup");
		homeButtons[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("homebuttondown");

		pauseButtons = new Sprite[2];
		pauseButtons[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("pausebuttonup");
		pauseButtons[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("pausebuttondown");

		restartButtons = new Sprite[2];
		restartButtons[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("replaybuttonup");
		restartButtons[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("replaybuttondown");

		playButtons = new Sprite[2];
		playButtons[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("nextbuttonup");
		playButtons[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("nextbuttondown");

		colorpacks = new Sprite[1][1][6][3];
		for (int i = 0; i < colorpacks[0][0].length; i++)
			for (int id = 0; id < colorpacks[0][0][i].length; id++)
				colorpacks[0][0][i][id] = PrizmPathGame.getAssets()
						.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
						.createSprite("prizm0" + i, id);

		soundpacks = new Music[1][1];
		soundpacks[0][0] = Gdx.audio.newMusic(Gdx.files
				.internal("data/sound/music/Pack1-0.mp3"));

		// Sets up the song
		Random ran = new Random();
		PrizmPathGame.curSong = ran.nextInt(PrizmPathGame.soundpacks[0].length);
		PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
				.setLooping(true);
	}

	// Loads the data
	public void loadData() {
		/*
		 * Brief Description: Loads the data from level.bin and options.bin into
		 * the byte[]s levelData and option data. This data is then read into
		 * the static variables.
		 */
		curBackground = 0;
		curColorpack = 0;
		curSoundpack = 0;
		Gdx.app.log(getLog(), "Data loaded");
	}

	// Saves all data
	public void saveData() {
		/*
		 * Brief Description: Before the game closes, the data is read from the
		 * variables back into the byte[]s and then saved in the level.bin and
		 * options.bin files.
		 */
		Gdx.app.log(getLog(), "Data saved");
	}

	// Gets the AssetManager
	public static AssetManager getAssets() {
		return assets;
	}

	// Sets the AssetManager
	public static void setAssetManager(AssetManager assets) {
		PrizmPathGame.assets = assets;
	}

	// Determines whether the music should be muted
	public static boolean isMusicMute() {
		return musicMute;
	}

	// Sets whether the music should be muted
	public static void setMusicMute() {
		PrizmPathGame.musicMute = !PrizmPathGame.musicMute;
	}

	// Gets whether the sound effects should be muted
	public static boolean isSfxMute() {
		return sfxMute;
	}

	// Sets whether the sound effects should be muted
	public static void setSfxMute() {
		PrizmPathGame.sfxMute = !PrizmPathGame.sfxMute;
	}

	// Gets level data for determining various things involving the levels
	public static byte getLevelData(int index) {
		// Level data saved in level.bin
		// Contains 120 level indices
		//
		// 0 = Not Unlocked
		// 1 = Not Completed
		// 2 = No Medal
		// 3 = Bronze
		// 4 = Silver
		// 5 = Gold
		return levelData[index];
	}

	// Saves a portion of level data
	public static void setLevelData(byte data, int index) {
		PrizmPathGame.levelData[index] = data;
	}

	// Gets a specific option data
	public static byte getOptionData(int index) {
		// Saved in options.bin
		// Music 0 | 1
		// SFX 0 | 1
		// Color Pack 0 | 1 | 2 | 3 | 4
		// Sound Pack 0 | 1 | 2 | 3 | 4
		// Background 0 | 1 | 2 | 3 | 4
		return optionData[index];
	}

	// Saves an option data
	public static void setOptionData(byte data, int index) {
		PrizmPathGame.optionData[index] = data;
	}

	// Gets a particular set of sounds
	public static Music[] getSoundpack(int index) {
		return soundpacks[index];
	}

	// Gets the background
	public static Sprite getBackground(int index) {
		return backgrounds[index];
	}

	// Gets a particular set of sprites
	public static Sprite[] getColorpack(int pack, int type, int color) {
		return colorpacks[pack][type][color];
	}

	// Gets the log for debugging and naming
	public static String getLog() {
		return log;
	}

	// Gets the version
	public static String getVersion() {
		return version;
	}

	public static void nextBackground() {
		if (PrizmPathGame.curBackground < PrizmPathGame.backgrounds.length - 1)
			PrizmPathGame.curBackground++;
		else
			PrizmPathGame.curBackground = 0;
	}

}
// © Hunter Heidenreich 2014