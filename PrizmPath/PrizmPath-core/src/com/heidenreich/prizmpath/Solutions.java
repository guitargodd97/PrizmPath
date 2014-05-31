package com.heidenreich.prizmpath;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

//---------------------------------------------------------------------------------------------
//
//Solutions.java
//Last Revised: 5/31/2014
//Author: Hunter Heidenreich
//Product of: HunterMusicAndTV
//
//---------------------------------------------------------------------------------------------
//Summary of Class:
//
//This class holds the information on what moves can be done in specific levels
//
//--------------------------------------------------------------------------------------------

public class Solutions {

	private ArrayList<Vector2> moveList;
	private int level;

	// Creates the solutions
	public Solutions(int level) {
		this.level = level;
		setup();
	}

	// Sets up the moves based on level
	private void setup() {
		moveList = new ArrayList<Vector2>();
		switch (level) {
		case (1):
			moveList.add(new Vector2(2, 6));
			break;
		case (2):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			break;
		case (3):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 7));
			moveList.add(new Vector2(2, 7));
			break;
		case (4):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(1, 5));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(1, 7));
			moveList.add(new Vector2(2, 7));
			break;
		case (5):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 6));
			break;
		case (6):
			moveList.add(new Vector2(2, 6));
			break;
		case (7):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 5));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(1, 5));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(1, 7));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(3, 7));
			break;
		case (8):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			break;
		case (9):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 5));
			moveList.add(new Vector2(3, 4));
			moveList.add(new Vector2(2, 4));
			break;
		case (10):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 5));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(1, 5));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(1, 7));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(3, 7));
			break;
		case (11):
			moveList.add(new Vector2(2, 6));
			break;
		case (12):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(4, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(2, 6));
			break;
		case (13):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 5));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(1, 5));
			moveList.add(new Vector2(0, 5));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(1, 6));
			break;
		case (14):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 7));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(1, 5));
			moveList.add(new Vector2(1, 6));
			break;
		case (15):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 4));
			moveList.add(new Vector2(3, 4));
			moveList.add(new Vector2(4, 4));
			moveList.add(new Vector2(4, 5));
			moveList.add(new Vector2(4, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(1, 8));
			moveList.add(new Vector2(0, 8));
			moveList.add(new Vector2(0, 7));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(1, 6));
			break;
		case (16):
			moveList.add(new Vector2(2, 6));
			break;
		case (17):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			break;
		case (18):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(0, 5));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(0, 7));
			break;
		case (19):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 6));
			break;
		case (20):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(3, 7));
			moveList.add(new Vector2(3, 8));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(3, 5));
			moveList.add(new Vector2(3, 4));
			moveList.add(new Vector2(2, 4));
			moveList.add(new Vector2(2, 5));
			break;
		case (21):
			moveList.add(new Vector2(2, 6));
			break;
		case (22):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 7));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(1, 7));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(1, 5));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(3, 5));
			break;
		case (23):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(4, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(1, 7));
			moveList.add(new Vector2(0, 7));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(0, 5));
			moveList.add(new Vector2(1, 5));
			break;
		case (24):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 4));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(3, 7));
			moveList.add(new Vector2(3, 8));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(1, 8));
			moveList.add(new Vector2(1, 7));
			break;
		case (25):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 4));
			moveList.add(new Vector2(3, 4));
			moveList.add(new Vector2(3, 5));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 7));
			moveList.add(new Vector2(3, 8));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(0, 6));
			break;
		case (26):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(3, 5));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(1, 5));
			moveList.add(new Vector2(0, 5));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(0, 7));
			moveList.add(new Vector2(0, 8));
			moveList.add(new Vector2(1, 8));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(3, 8));
			moveList.add(new Vector2(4, 8));
			moveList.add(new Vector2(4, 7));
			break;
		case (27):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 4));
			moveList.add(new Vector2(3, 4));
			moveList.add(new Vector2(4, 4));
			moveList.add(new Vector2(3, 4));
			moveList.add(new Vector2(2, 4));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(3, 8));
			moveList.add(new Vector2(4, 8));
			moveList.add(new Vector2(3, 8));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(2, 7));
			break;
		case (28):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(4, 6));
			moveList.add(new Vector2(4, 7));
			moveList.add(new Vector2(4, 6));
			moveList.add(new Vector2(4, 5));
			moveList.add(new Vector2(4, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(0, 5));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(0, 7));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(1, 6));
			break;
		case (29):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 4));
			moveList.add(new Vector2(2, 3));
			moveList.add(new Vector2(2, 2));
			moveList.add(new Vector2(2, 1));
			moveList.add(new Vector2(2, 2));
			moveList.add(new Vector2(2, 3));
			moveList.add(new Vector2(2, 4));
			moveList.add(new Vector2(2, 5));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(2, 7));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(2, 9));
			moveList.add(new Vector2(2, 10));
			moveList.add(new Vector2(2, 11));
			moveList.add(new Vector2(2, 10));
			moveList.add(new Vector2(2, 9));
			moveList.add(new Vector2(2, 8));
			moveList.add(new Vector2(2, 7));
			break;
		case (30):
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(0, 5));
			moveList.add(new Vector2(0, 4));
			moveList.add(new Vector2(0, 3));
			moveList.add(new Vector2(1, 3));
			moveList.add(new Vector2(2, 3));
			moveList.add(new Vector2(3, 3));
			moveList.add(new Vector2(4, 3));
			moveList.add(new Vector2(4, 4));
			moveList.add(new Vector2(4, 5));
			moveList.add(new Vector2(4, 6));
			moveList.add(new Vector2(3, 6));
			moveList.add(new Vector2(2, 6));
			moveList.add(new Vector2(1, 6));
			moveList.add(new Vector2(0, 6));
			moveList.add(new Vector2(0, 7));
			moveList.add(new Vector2(0, 8));
			moveList.add(new Vector2(0, 9));
			moveList.add(new Vector2(1, 9));
			moveList.add(new Vector2(2, 9));
			moveList.add(new Vector2(3, 9));
			moveList.add(new Vector2(4, 9));
			moveList.add(new Vector2(4, 8));
			moveList.add(new Vector2(4, 7));
			moveList.add(new Vector2(4, 6));
			moveList.add(new Vector2(3, 6));
			break;
		}
	}

	// Checks if a move can be made
	public boolean checkMove(Vector2 move) {
		// Searches through appropriate moves
		for (int i = 0; i < moveList.size(); i++) {
			// If it matches
			if (move.toString().equals(moveList.get(i).toString())) {
				// Remove the move from the list and return true
				moveList.remove(i);
				return true;
			}
		}
		return false;
	}
}
// © Hunter Heidenreich 2014