package com.heidenreich.prizmpath;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//---------------------------------------------------------------------------------------------
//
//Tile.java
//Last Revised: 5/31/2014
//Author: Hunter Heidenreich
//Product of: HunterMusicAndTV
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the Tile object that holds a Prizm and is the gateway between the GameScreen
//and the Przim classes.
//
//--------------------------------------------------------------------------------------------

public class Tile {

	private boolean prizmActive;
	private boolean selected;
	private Prizm prizm;
	private Rectangle rect;
	private Sprite sprite;
	private Vector2 location;
	private static Vector2 size;

	// Creates a new Tile
	public Tile(Vector2 location, boolean prizmActive) {
		//Saves parameters
		this.prizmActive = prizmActive;
		this.location = location;
		
		//Sets up all the variables
		setup();
	}

	//Draws the tiles
	public void draw(SpriteBatch batch) {
		if (prizmActive)
			sprite.draw(batch);
	}

	//Draws the Prizms
	public void drawPrizms(SpriteBatch batch) {
		if (prizmActive)
			prizm.draw(batch);
	}

	//Checks if a Tile is clicked
	public boolean checkCollision(Vector2 click) {
		if (rect.contains(click))
			return true;
		return false;
	}

	//Changes the color of its Prizm
	public void changeColor() {
		prizm.changeColor();
	}

	//Sets a Prizm to be active aswell as gives it a new color and type
	public void setPrizm(int color, int type) {
		prizmActive = true;
		prizm.setup(color, type);
	}

	//Returns the type of its Prizm
	public int getType() {
		return prizm.getType();
	}

	//Returns the color of its Prizm
	public int getColor() {
		return prizm.getColor();
	}

	//Returns whether its Prizm is active
	public boolean isPrizmActive() {
		return prizmActive;
	}

	//Triggers and ex in the Prizm class
	public void triggerWrong() {
		prizm.triggerWrong();
	}

	//Sets whether or not its Prizm is selected
	public void setFrame(int curFrame) {
		prizm.setFrame(curFrame);
	}

	//Returns whether a Tile is selected
	public boolean isSelected() {
		return selected;
	}

	//Sets whether a Tile is selected
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	//Sets up a Tile
	private void setup() {
		//Makes the Tile not selected
		selected = false;
		
		//Sets the size and rectangle
		size = new Vector2(60, 60);
		rect = new Rectangle(location.x, location.y, size.x, size.y);
		
		//Sets up the Tile graphic
		sprite = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("tile0");
		sprite.setPosition(location.x, location.y);
		
		//Gives the Tile a Prizm
		prizm = new Prizm(0, 0, location);
	}
}
//© Hunter Heidenreich 2014