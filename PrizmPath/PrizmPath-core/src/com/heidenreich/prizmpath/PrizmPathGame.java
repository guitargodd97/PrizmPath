package com.heidenreich.prizmpath;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.heidenreich.prizmpath.screens.SplashScreen;

public class PrizmPathGame extends Game {
	public static ApplicationType appType;
	public static AssetManager assets;
	public static boolean musicMute;
	public static boolean sfxMute;
	public static byte[] levelData;
	public static byte[] optionData;
	public static Music[][] soundpacks;
	public static Sprite[] backgrounds;
	public static Sprite[][][] colorpacks;
	public static final String log = "PrizmPath";
	public static final String version = "Alpha 1.0";

	/*
	 * TO DO LIST: - Write loadData() - Write loadResources() - Write savaData()
	 */
	// Creates the game
	public void create() {
		// Logs the game type for control modification
		appType = Gdx.app.getType();

		// Loads data
		loadData();

		// Sets up the assets
		PrizmPathGame.setAssetManager(new AssetManager());
		loadResources();

		// Starts the game with the splashscreen
		this.setScreen(new SplashScreen(this));
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
		 * NOTE - May need multiple instanes of AssetManagers for multiple file
		 * types, such as textures and audio.
		 */
		Gdx.app.log(getLog(), "Resources loaded");
	}

	// Loads the data
	public void loadData() {
		/*
		 * Brief Description: Loads the data from level.bin and options.bin into
		 * the byte[]s levelData and option data. This data is then read into
		 * the static variables.
		 */
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
	public static void setMusicMute(boolean musicMute) {
		PrizmPathGame.musicMute = musicMute;
	}

	// Gets whether the sound effects should be muted
	public static boolean isSfxMute() {
		return sfxMute;
	}

	// Sets whether the sound effects should be muted
	public static void setSfxMute(boolean sfxMute) {
		PrizmPathGame.sfxMute = sfxMute;
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
	public static Sprite[][] getColorpack(int index) {
		return colorpacks[index];
	}

	// Gets the log for debugging and naming
	public static String getLog() {
		return log;
	}

	// Gets the version
	public static String getVersion() {
		return version;
	}

}
// © Hunter Heidenreich 2014