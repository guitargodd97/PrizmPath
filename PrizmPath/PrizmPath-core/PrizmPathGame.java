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
	public static boolean musicMute = false;
	public static boolean sfxMute = false;
	public static byte[] levelData;
	public static byte[] optionData;
	public static Music[][] soundpacks;
	public static Sprite[] backgrounds;
	public static Sprite[][] colorpacks;
	public static final String log = "PrizmPath";
	public static final String version = "Alpha 1.0";

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

	}

	// Loads the data
	public void loadData() {

	}

	// Saves all data
	public void saveData() {

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

	// Gets level data for determining various things
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

	public static void setLevelData(byte data, int index) {
		PrizmPathGame.levelData[index] = data;
	}

	public static byte getOptionData(int index) {
		return optionData[index];
	}

	public static void setOptionData(byte data, int index) {
		PrizmPathGame.optionData[index] = data;
	}

	public static Music[] getSoundpack(int index) {
		return soundpacks[index];
	}

	public static Sprite getBackground(int index) {
		return backgrounds[index];
	}

	public static Sprite[] getColorpack(int index) {
		return colorpacks[index];
	}

	public static String getLog() {
		return log;
	}

	public static String getVersion() {
		return version;
	}

}
