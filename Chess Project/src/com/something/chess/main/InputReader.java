package com.something.chess.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputReader extends MouseAdapter implements Player, Renderable{

	private Board board;
	
	private Square startSelection;
	private Square endSelection;
	private Color color; //thinking of highlighting selection squares with a certain color later
	
	public InputReader(Board board) {
		this.board = board;
	}
	
	/**
	 * what to do when the mouse is pressed
	 *  - check if the mouse is over any of the squares
	 *  - if so start keeping track of selections
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		for (int i = 0; i < board.RANKS; i ++) {
			for (int j = 0; j < board.FILES; j ++) {
				Square square = board.getSquare(i, j);
				if (square.mouseOver(mx, my)) {
					if (!square.isEmpty()) {
						if (startSelection == null) {
							startSelection = square;
						}else {
							endSelection = square;
						}
					} else {
						if (startSelection != null) {
							endSelection = square;
						}
					}
				}
			}
		}
	}
	
	@Override 
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public Move getMove() {
		return new Move(startSelection, endSelection);
	}

	public void reset() {
		startSelection = null;
		endSelection = null;
	}

	@Override
	public void render(Graphics g) {
		//going to make some sort of gui to represent selecions
	}
	
	/**
	 * does the player have a valid move ready
	 * @return
	 */
	public boolean isReady() {
		return endSelection != null && startSelection != null;
	}

}
