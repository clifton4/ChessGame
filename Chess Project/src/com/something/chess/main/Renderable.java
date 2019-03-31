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
public interface Renderable {

	
	 void render(Graphics g);
	
}
