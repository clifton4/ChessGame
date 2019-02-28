package com.something.chess.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends GameObject{

	public static final int HEIGHT = ChessGame.HEIGHT - 50;
	public static final int WIDTH = ChessGame.WIDTH - 50;
	public static final int RANKS = 8, FILES = 8;
	private final int ranks = 8;
	private final int files = 8;
	
	private Square[][] board;
	private ChessGame chessGame;
	
	public enum Side{
		BLACK,
		WHITE;
	}
	
	public Board(ChessGame chessGame) {
		super(50, 50);
		
		this.chessGame = chessGame;
		
		board = new Square[ranks][files];
		//pictured as 
		//0,7 1,7 2,7, 3,7 4,7 5,7 ...
		//0,6 1,6 2,6 ...
		//....
		//0,0
		Color squareColor = Color.WHITE;
		for (int i = 0; i < ranks; i ++) {
			for (int j = 0; j < files; j ++) {
				if (squareColor == Color.BLUE)
					squareColor = Color.WHITE;
				else 
					squareColor = Color.BLUE;
				board[i][j] = new Square(i, j, i * Square.SIDE_LENGTH + 10, j * Square.SIDE_LENGTH + 10, squareColor, chessGame);
			}
			if (squareColor == Color.BLUE)
				squareColor = Color.WHITE;
			else 
				squareColor = Color.BLUE;
		}
		
		
		// TODO - add the pieces
		initPieces();
		System.out.println(this + ": is fully created.");
	}
	
	private void initPieces() {
		System.out.println("Initializing starting positions...");
		board[0][0].setPiece(new ChessFigure(ChessFigure.FigureType.ROOK, Side.BLACK));
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		System.out.println(board[0][0].getPiece());
		for (Square[] ranks : board) {
			for (Square square : ranks) {
				square.render(g);;
			}
		}
		
		
	}
	
	
}
