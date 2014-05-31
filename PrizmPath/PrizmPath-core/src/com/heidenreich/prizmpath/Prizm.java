package com.heidenreich.prizmpath;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

//---------------------------------------------------------------------------------------------
//
//Prizm.java
//Last Revised: 5/31/2014
//Author: Hunter Heidenreich
//Product of: HunterMusicAndTV
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class is the Prizm Object. It holds information such as a prizm's color and type. It
//features getters and setters to get and retrieve this information.
//
//--------------------------------------------------------------------------------------------

public class Prizm {

	private int color;
	private int curFrame;
	private int right;
	private int type;
	private int wrong;
	private Sprite check;
	private Sprite ex;
	private Sprite[] sprites;
	private Vector2 loco;
	private static Vector2 size;

	// Constructor for the Prizm
	public Prizm(int color, int type, Vector2 loco) {
		// Saves parameters
		this.color = color;
		this.type = type;
		this.loco = loco;

		// Sets the size
		size = new Vector2(60, 60);

		// Sets the sprites
		setupSprites();
	}

	// Method called to change the color of a Prizm
	public void changeColor() {
		// Prevent out of bounds
		if (++color > 5)
			color = 0;

		// Code to create levels
		// if (--color < 0)
		// color = 5;

		// Re-assigns the sprites
		setupSprites();

		// Allows the check image to display
		right = 15;
	}

	// Allows the ex image to display
	public void triggerWrong() {
		wrong = 15;
	}

	// Returns the color
	public int getColor() {
		return color;
	}

	// Returns the type
	public int getType() {
		return type;
	}

	// Draws the Prizm
	public void draw(SpriteBatch batch) {
		// Draw the Prizm
		sprites[curFrame].draw(batch);

		// Checks whether to draw the check
		if (right > 0) {
			check.draw(batch);
			right--;
		}

		// Checks whether to draw the ex
		if (wrong > 0) {
			ex.draw(batch);
			wrong--;
		}
	}

	// Resets a previously instantiated Prizm
	public void setup(int color, int type) {
		// Saves parameters
		this.type = type;
		this.color = color;

		// Sets up the sprites
		setupSprites();
	}

	// Sets the frame for a Prizm to show if it is highlighted or not
	public void setFrame(int curFrame) {
		this.curFrame = curFrame;
	}

	// Sets the sprites to the appropriate image
	private void setupSprites() {
		// Retrieves the sprite array based on type and color
		Sprite[] temp = PrizmPathGame.getColorpack(0, type, color);

		// Creates a new array, based on retrieved array
		sprites = new Sprite[temp.length];

		// Assigns array with the values from temp
		for (int i = 0; i < sprites.length; i++)
			sprites[i] = new Sprite(temp[i]);

		// Sets the position of the prites
		for (int i = 0; i < sprites.length; i++)
			sprites[i].setPosition(loco.x - sprites[i].getWidth() / 2 + size.x
					/ 2, loco.y - sprites[i].getHeight() / 2 + size.y / 2);

		// Retrieves image for check and sets position
		check = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("check");
		check.setPosition(loco.x - check.getWidth() / 2 + size.x / 2, loco.y
				- check.getHeight() / 2 + size.y / 2);

		// Retrieves image for ex and sets position
		ex = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("ex");
		ex.setPosition(loco.x - ex.getWidth() / 2 + size.x / 2,
				loco.y - ex.getHeight() / 2 + size.y / 2);
	}
}
// © Hunter Heidenreich 2014