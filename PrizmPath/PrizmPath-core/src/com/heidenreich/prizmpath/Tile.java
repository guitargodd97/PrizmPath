package com.heidenreich.prizmpath;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	private boolean prizmActive;
	private boolean selected;
	private Prizm prizm;
	private Rectangle rect;
	private Sprite sprite;
	private Vector2 location;
	private static Vector2 size;

	public Tile(Vector2 location, boolean prizmActive) {
		this.prizmActive = prizmActive;
		this.location = location;
		selected = false;
		size = new Vector2(60, 60);
		sprite = PrizmPathGame.getAssets()
				.get(PrizmPathGame.TEXTURE_PATH, TextureAtlas.class)
				.createSprite("tile0");
		sprite.setPosition(this.location.x, this.location.y);
		prizm = new Prizm(0, 0, location);
		rect = new Rectangle(location.x, location.y, size.x, size.y);
	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	public void drawPrizms(SpriteBatch batch) {
		if (prizmActive)
			prizm.draw(batch);
	}

	public boolean checkCollision(Vector2 click) {
		if (rect.contains(click))
			return true;
		return false;
	}

	public void changeColor() {
		prizm.changeColor();
	}

	public void setPrizm(int color, int type) {
		prizmActive = true;
		prizm.setup(color, type);
	}

	public int getType() {
		return prizm.getType();
	}

	public int getColor() {
		return prizm.getColor();
	}

	public boolean isPrizmActive() {
		return prizmActive;
	}
	
	public void setFrame(int curFrame) {
		prizm.setFrame(curFrame);
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
