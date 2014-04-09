package com.heidenreich.prizmpath;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Prizm {
	private int color;
	private int type;
	private Rectangle rect;
	private Vector2 loco;
	private static Vector2 size;

	public Prizm(int color, int type) {
		this.color = color;
		this.type = type;
	}

	public int changeColor() {
		if (++color > 5)
			color = 0;
		return type;
	}
	
	public int getColor() {
		return color;
	}
}
