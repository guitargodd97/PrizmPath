package com.heidenreich.prizmpath;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Prizm {

	private int color;
	private int curFrame;
	private int type;
	private Sprite[] sprites;
	private Vector2 loco;
	private static Vector2 size;

	public Prizm(int color, int type, Vector2 loco) {
		this.color = color;
		this.type = type;
		this.loco = loco;
		curFrame = 0;
		size = new Vector2(60, 60);
		Sprite[] temp = PrizmPathGame.getColorpack(0, this.type, this.color);
		sprites = new Sprite[temp.length];
		for (int i = 0; i < sprites.length; i++)
			sprites[i] = new Sprite(temp[i]);
		for (int i = 0; i < sprites.length; i++)
			sprites[i].setPosition(loco.x - sprites[i].getWidth() / 2 + size.x
					/ 2, loco.y - sprites[i].getHeight() / 2 + size.y / 2);
	}

	public void changeColor() {
		if (++color > 5)
			color = 0;
		Sprite[] temp = PrizmPathGame.getColorpack(0, type, color);
		sprites = new Sprite[temp.length];
		for (int i = 0; i < sprites.length; i++)
			sprites[i] = new Sprite(temp[i]);
		for (int i = 0; i < sprites.length; i++)
			sprites[i].setPosition(loco.x - sprites[i].getWidth() / 2 + size.x
					/ 2, loco.y - sprites[i].getHeight() / 2 + size.y / 2);
	}

	public int getColor() {
		return color;
	}

	public int getType() {
		return type;
	}

	public void draw(SpriteBatch batch) {
		sprites[curFrame].draw(batch);
	}

	public void setup(int color, int type) {
		this.type = type;
		this.color = color;
		Sprite[] temp = PrizmPathGame.getColorpack(0, type, color);
		sprites = new Sprite[temp.length];
		for (int i = 0; i < sprites.length; i++)
			sprites[i] = new Sprite(temp[i]);
		for (int i = 0; i < sprites.length; i++)
			sprites[i].setPosition(loco.x - sprites[i].getWidth() / 2 + size.x
					/ 2, loco.y - sprites[i].getHeight() / 2 + size.y / 2);
	}

	public void setFrame(int curFrame) {
		this.curFrame = curFrame;
	}
}
