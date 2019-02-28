package com.something.chess.main;

import java.awt.Color;
import java.awt.Graphics;

public class Square extends GameObject{

	private ChessFigure piece;
	private int rank, file;
	private Color color;
	private ChessGame chessGame;
	
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
	public Square(int rank, int file, int x, int y, ChessFigure piece, Color color, ChessGame chessGame) {
		super(x, y );
		this.rank = rank;
		this.file = file;
		this.piece = piece;
		this.color = color;
		this.chessGame = chessGame;
	}
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
	 * @throws SquareOccupiedException
	 * 
	 * @param piece the new chess piece to add
	 * 
	 */
	public void setPiece(ChessFigure piece) /*throws SquareOccupiedException*/ {
		//if (!isEmpty()) 
			//throw new SquareOccupiedException("Tried to set piece of an occupied square.");
		//else {
			this.piece = piece;
		//} 
			// Exception to be implemented later. 
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
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void render(Graphics g) {
		
		//draw the square itself
		g.setColor(color);
		g.fillRect(x, y, Square.SIDE_LENGTH, Square.SIDE_LENGTH);
		
		
		//draw the piece
		if (!isEmpty()) {
			System.out.println("drawing piece:" + getPiece());
			g.drawImage(getPiece().getImage(), x, y, chessGame);
		}
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
	
}
