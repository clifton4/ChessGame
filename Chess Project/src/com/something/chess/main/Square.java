package com.something.chess.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Square implements Renderable{

	private ChessFigure piece;
	private int rank, file;
	private Color color;
	private ChessGame chessGame;
	private int x,y;
	
	
	public static final int SIDE_LENGTH = 50;
	
	/**
	 * creates a square object
	 * @param rank - the piece's rank (from left to right)
	 * @param file - the piece's file (top to bottom - reversed as of now because of the backwards coordinate system)
	 * @param x - x value - left hand side of square
	 * @param y - y position - top side of square
	 * @param piece - what piece is on the square - null if none
	 * @param color - black or white - not checked
	 */
	public Square(int file, int rank, int x, int y, ChessFigure piece, Color color, ChessGame chessGame) {
		this.x = x;
		this.y = y;
		this.rank = rank;
		this.file = file;
		this.piece = piece;
		this.color = color;
		this.chessGame = chessGame;
	}
	/**
	 * Constructor without x and y -sets them to defaults (rank and file times the side length)
	 */
	public Square(int rank, int file, ChessFigure piece, Color color, ChessGame chessGame) {
		this(rank, file, Square.SIDE_LENGTH * rank, Square.SIDE_LENGTH * file, piece, color, chessGame );
	}
	public Square(int rank, int file, int x, int y, Color color, ChessGame chessGame) {
		this(rank, file, x, y, null, color, chessGame );
	}
	public Square(int rank, int file, Color color, ChessGame chessGame) {
		this(rank, file, null, color, chessGame);
	}
	
	/**
	 * adds a new peace 
	 * 
	 * @throws InvalidMoveException
	 * 
	 * @param piece the new chess piece to add
	 * 
	 */
	public void setPiece(ChessFigure piece) {
		this.piece = piece;
	}
	
	public ChessFigure getPiece() {
		return piece;
	}
	
	/**
	 * does this square have a chess figure on it.
	 * @return
	 */
	public boolean isEmpty() {
		return piece == null;
	}
	
	
	@Override
	public void render(Graphics g) {
		
		//draw the square itself
		g.setColor(color);
		g.fillRect(x, y, Square.SIDE_LENGTH, Square.SIDE_LENGTH);
		
		
		//draw the piece
		if (!isEmpty()) {
			g.drawImage(getPiece().getImage(), x, y, chessGame);
		}
		
		//g.setColor(Color.RED);
		//g.drawString("(" + rank + ", " + file + ")", x, y+15);
		
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getFile() {
		return file;
	}
	
	@Override
	public String toString() {
		return "Square[" + rank + "][" + file + "] at " + "( " + x + ", " + y + " )" + "     isEmpty = " + isEmpty();
	}
	
	/**
	 * This function returns true if the mouse is currently over the square, false if it is not
	 * @param mx - x coordinate of the mouse
	 * @param my - y coordinate of the mouse
	 * @return
	 */
	public boolean mouseOver(int mx, int my) {
		if ((mx >= this.x) && (mx <= (this.x + Square.SIDE_LENGTH))) {
			if ((my >= this.y) && (my <= (this.y + Square.SIDE_LENGTH))) {
				return true;
			}
		}else {
			return false;
		}
		return false;
	}
	
	/**
	 * two squares are equal if they have the same rank and file
	 */
	@Override 
	public boolean equals(Object o) {
		if (o instanceof Square) {
			Square s = (Square)o;
			return this.rank == s.rank && this.file == s.file;
		}
		return false;
	}
	
}
