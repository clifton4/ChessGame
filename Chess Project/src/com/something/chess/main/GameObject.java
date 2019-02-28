package com.something.chess.main;

import java.awt.Graphics;

/**
 * represents an object that can be drawn to the screen. 
 * 
 * this class is stolen from another game I am writing. the tick method probably isn't needed for a chess game. 
 * 
 * @author clift
 *
 */
public abstract class GameObject {

	
	protected int x, y;
	
	public GameObject(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}
