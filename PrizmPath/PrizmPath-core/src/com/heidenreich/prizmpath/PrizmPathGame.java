package com.heidenreich.prizmpath;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.heidenreich.prizmpath.screens.SplashScreen;
import com.heidenreich.prizmpath.screens.StartScreen;

//---------------------------------------------------------------------------------------------
//
//PrizmPathGame.java
//Last Revised: 5/31/2014
//Author: Hunter Heidenreich
//Product of: HunterMusicAndTV
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the main game class that runs all of PrizmPath
//
//--------------------------------------------------------------------------------------------

public class PrizmPathGame extends Game {

	public static ApplicationType appType;
	public static AssetManager assets;
	private boolean ads;
	public static boolean musicMute;
	public static boolean sfxMute;
	public static byte[] levelData;
	public static byte[] optionData;
	private IActivityRequestHandler myRequestHandler;
	private int loaded;
	public static int curBackground;
	public static int curColorpack;
	public static int curSong;
	public static int curSoundpack;
	public static Music[][] soundpacks;
	public static Sprite splash;
	public static Sprite[] backgrounds;
	public static Sprite[] homeButtons;
	public static Sprite[] pauseButtons;
	public static Sprite[] playButtons;
	public static Sprite[] restartButtons;
	public static Sprite[][][][] colorpacks;
	public static final String log = "PrizmPath";
	public static final String SFX_PATH = "data/sound/sfx/";
	public static final String TEXTURE_PATH = "data/textures/texture.atlas";
	public static final String version = "1.0";

	public PrizmPathGame(IActivityRequestHandler handler) {
		myRequestHandler = handler;
		ads = false;
	}

	// Creates the game
	public void create() {
		// Logs the game type for control modification
		appType = Gdx.app.getType();

		// Loads data
		loadData();

		// Sets up the assets
		PrizmPathGame.setAssetManager(new AssetManager());
		loadResources();
	}

	// Disposes of the game
	public void dispose() {
		// Always saves before disposing
		saveData();

		// Disposes
		super.dispose();
		PrizmPathGame.getAssets().dispose();
	}

	// Updates the game
	public void render() {
		super.render();

		// If everything is loaded
		if (loaded == 2) {
			loaded++;

			// Starts the game with the splashscreen
			this.setScreen(new SplashScreen(this));
		} else if (loaded == 1) {
			// Sets up the resources
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
		// Set the type of manager
		PrizmPathGame.getAssets().setLoader(TiledMap.class,
				new TmxMapLoader(new InternalFileHandleResolver()));

		// Loads the textures
		PrizmPathGame.getAssets().load(PrizmPathGame.TEXTURE_PATH,
				TextureAtlas.class);

		// Loads the buttons
		PrizmPathGame.getAssets().load(StartScreen.BUTTON_TEXTURE,
				TextureAtlas.class);

		// Loads the sfx
		PrizmPathGame.getAssets().load(SFX_PATH + "correct.mp3", Sound.class);
		PrizmPathGame.getAssets().load(SFX_PATH + "wrong.mp3", Sound.class);
		PrizmPathGame.getAssets().load(SFX_PATH + "win.mp3", Sound.class);
		PrizmPathGame.getAssets().load(SFX_PATH + "lose.mp3", Sound.class);

		// Forces it to finish loading
		PrizmPathGame.getAssets().finishLoading();
	}

	// Sets up the resources()
	public void setupResources() {
		// Sets the splash texture
		PrizmPathGame.splash = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("splash");

		// Sets up the backgrounds
		backgrounds = new Sprite[10];
		for (int i = 0; i < backgrounds.length; i++)
			backgrounds[i] = PrizmPathGame.getAssets()
					.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
					.createSprite("background" + i);

		// Sets up the home button
		homeButtons = new Sprite[2];
		homeButtons[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("homebuttonup");
		homeButtons[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("homebuttondown");

		// Sets up the pause button
		pauseButtons = new Sprite[2];
		pauseButtons[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("pausebuttonup");
		pauseButtons[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("pausebuttondown");

		// Sets up the restart button
		restartButtons = new Sprite[2];
		restartButtons[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("replaybuttonup");
		restartButtons[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("replaybuttondown");

		// Sets up the play button
		playButtons = new Sprite[2];
		playButtons[0] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("nextbuttonup");
		playButtons[1] = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("nextbuttondown");

		// Loads all the Prizm sprites
		colorpacks = new Sprite[1][5][6][2];
		for (int x = 0; x < colorpacks[0].length; x++)
			for (int i = 0; i < colorpacks[0][x].length; i++)
				for (int id = 0; id < colorpacks[0][x][i].length; id++)
					colorpacks[0][x][i][id] = PrizmPathGame
							.getAssets()
							.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
							.createSprite("prizm" + x + "" + i, id);

		// Loads all the music
		soundpacks = new Music[1][5];
		for (int i = 0; i < soundpacks.length; i++) {
			for (int id = 0; id < soundpacks[i].length; id++)
				soundpacks[i][id] = Gdx.audio.newMusic(Gdx.files
						.internal("data/sound/music/Pack" + (i + 1) + "-" + id
								+ ".mp3"));
		}

		// Sets up the song
		Random ran = new Random();
		PrizmPathGame.curSong = ran.nextInt(PrizmPathGame.soundpacks[0].length);
		PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
				.setLooping(true);
	}

	// Loads the data
	public void loadData() {
		// Grabs the location of options.bin
		FileHandle fileLocation = Gdx.files.local("data/options.bin");

		// If file doesn't exist, write a new one
		if (!fileLocation.exists())
			fileLocation.writeBytes(new byte[] { 0, 0, 0, 0, 0 }, false);

		// Reads the data
		optionData = fileLocation.readBytes();

		// Sets all the data to appropriate values
		if (optionData[0] == 0)
			musicMute = false;
		else
			musicMute = true;
		if (optionData[1] == 0)
			sfxMute = false;
		else
			sfxMute = true;
		curColorpack = optionData[2];
		curSoundpack = optionData[3];
		curBackground = optionData[4];

		// Grabs location of levels.bin
		fileLocation = Gdx.files.local("data/levels.bin");

		// If file doesn't exist, write a new one
		if (!fileLocation.exists()) {
			levelData = new byte[30];
			levelData[0] = (byte) 1;
			fileLocation.writeBytes(levelData, false);
		}

		// Reads the data
		levelData = fileLocation.readBytes();
	}

	// Saves all data
	public static void saveData() {
		// Grabs location of options.bin and saves
		FileHandle fileLocation = Gdx.files.local("data/options.bin");
		fileLocation.writeBytes(optionData, false);

		// Grabs location of levels.bin and saves
		fileLocation = Gdx.files.local("data/levels.bin");
		fileLocation.writeBytes(levelData, false);
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
		if (PrizmPathGame.isMusicMute())
			PrizmPathGame.setOptionData((byte) 1, 0);
		else
			PrizmPathGame.setOptionData((byte) 0, 0);
	}

	// Gets whether the sound effects should be muted
	public static boolean isSfxMute() {
		return sfxMute;
	}

	// Sets whether the sound effects should be muted
	public static void setSfxMute() {
		PrizmPathGame.sfxMute = !PrizmPathGame.sfxMute;
		if (PrizmPathGame.isSfxMute())
			PrizmPathGame.setOptionData((byte) 1, 1);
		else
			PrizmPathGame.setOptionData((byte) 0, 1);
	}

	// Gets level data for determining various things involving the levels
	public static byte getLevelData(int index) {
		// Level data saved in level.bin
		// Contains 30 level indices
		//
		// 0 = Not Unlocked
		// 1 = No Medal
		// 2 = Bronze
		// 3 = Silver
		// 4 = Gold
		return levelData[index];
	}

	// Saves a portion of level data
	public static void setLevelData(byte data, int index) {
		PrizmPathGame.levelData[index] = data;
		PrizmPathGame.saveData();
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

	// Filters to the next background
	public static void nextBackground() {
		// Prevent out of bounds
		if (PrizmPathGame.curBackground < PrizmPathGame.backgrounds.length - 1)
			PrizmPathGame.curBackground++;
		else
			PrizmPathGame.curBackground = 0;

		// Saves the current background
		PrizmPathGame.setOptionData((byte) curBackground, 4);
	}

	// Changes the song
	public static void changeSong() {
		// Stop current song
		PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
				.stop();

		// Prevent out of bounds
		if (PrizmPathGame.curSong < PrizmPathGame.soundpacks[0].length - 1)
			PrizmPathGame.curSong++;
		else
			PrizmPathGame.curSong = 0;

		// Set new song to looping
		PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
				.setLooping(true);

		// If game is not muted, play the new song
		if (!PrizmPathGame.isMusicMute())
			PrizmPathGame.soundpacks[PrizmPathGame.curSoundpack][PrizmPathGame.curSong]
					.play();
	}

	// Deletes the saved data
	public static void deleteData() {
		// Grabs the option data and writes base info
		FileHandle fileLocation = Gdx.files.local("data/options.bin");
		fileLocation.writeBytes(new byte[] { 0, 0, 0, 0, 0 }, false);

		// Reads data and assigns appropriate values
		optionData = fileLocation.readBytes();
		if (optionData[0] == 0)
			musicMute = false;
		else
			musicMute = true;
		if (optionData[1] == 0)
			sfxMute = false;
		else
			sfxMute = true;
		curColorpack = optionData[2];
		curSoundpack = optionData[3];
		curBackground = optionData[4];

		// Grabs the level data and writes base info
		fileLocation = Gdx.files.local("data/levels.bin");
		levelData = new byte[30];
		levelData[0] = (byte) 1;
		fileLocation.writeBytes(levelData, false);

		// Reads the data
		levelData = fileLocation.readBytes();
	}

	// Displays the ads
	public void activateAds() {
		if (!ads) {
			myRequestHandler.showAds(true);
			ads = true;
		}
	}
}
// © Hunter Heidenreich 2014