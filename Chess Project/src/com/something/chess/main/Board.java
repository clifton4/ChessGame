package com.something.chess.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.something.chess.main.ChessFigure.FigureType;

public class Board implements Renderable{

	public static final int HEIGHT = ChessGame.HEIGHT - 50;
	public static final int WIDTH = ChessGame.WIDTH - 50;
	public static final int RANKS = 8, FILES = 8;
	private final int ranks = 8;
	private final int files = 8;
	private Side turn;
	
	private Square[][] board;
	private ChessGame chessGame;
	
	public enum Side{
		BLACK,
		WHITE;
	}
	
	public Board(ChessGame chessGame) {
		
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
		
		
		turn = Side.WHITE;
	}
	
	private void initPieces() {
		System.out.println("Initializing starting positions...");

		board[0][0].setPiece(new ChessFigure(ChessFigure.FigureType.ROOK, Side.BLACK));
		board[1][0].setPiece(new ChessFigure(ChessFigure.FigureType.KNIGHT, Side.BLACK));
		board[2][0].setPiece(new ChessFigure(ChessFigure.FigureType.BISHOP, Side.BLACK));
		board[3][0].setPiece(new ChessFigure(ChessFigure.FigureType.QUEEN, Side.BLACK));
		board[4][0].setPiece(new ChessFigure(ChessFigure.FigureType.KING, Side.BLACK));
		board[5][0].setPiece(new ChessFigure(ChessFigure.FigureType.BISHOP, Side.BLACK));
		board[6][0].setPiece(new ChessFigure(ChessFigure.FigureType.KNIGHT, Side.BLACK));
		board[7][0].setPiece(new ChessFigure(ChessFigure.FigureType.ROOK, Side.BLACK));
		
		for (int i = 0; i < files; i ++) {
			board[i][1].setPiece(new ChessFigure(ChessFigure.FigureType.PAWN, Side.BLACK));
		}
		
		board[0][7].setPiece(new ChessFigure(ChessFigure.FigureType.ROOK, Side.WHITE));
		board[1][7].setPiece(new ChessFigure(ChessFigure.FigureType.KNIGHT, Side.WHITE));
		board[2][7].setPiece(new ChessFigure(ChessFigure.FigureType.BISHOP, Side.WHITE));
		board[3][7].setPiece(new ChessFigure(ChessFigure.FigureType.QUEEN, Side.WHITE));
		board[4][7].setPiece(new ChessFigure(ChessFigure.FigureType.KING, Side.WHITE));
		board[5][7].setPiece(new ChessFigure(ChessFigure.FigureType.BISHOP, Side.WHITE));
		board[6][7].setPiece(new ChessFigure(ChessFigure.FigureType.KNIGHT, Side.WHITE));
		board[7][7].setPiece(new ChessFigure(ChessFigure.FigureType.ROOK, Side.WHITE));
		
		for (int i = 0; i < files; i ++) {
			board[i][6].setPiece(new ChessFigure(ChessFigure.FigureType.PAWN, Side.WHITE));
		}
		

	}
	
	public Square getSquare(int i, int j) {
		return board[i][j];
	}
	

	@Override
	public void render(Graphics g) {
		//System.out.println(board[0][0].getPiece());
		for (Square[] ranks : board) {
			for (Square square : ranks) {
				square.render(g);;
			}
		}
	}

	/**
	 * is the move valid. 
	 * - for now just checks that the two positions exist and that the start has a piece
	 * 
	 * @return is the move valid
	 */
	public boolean isValidMove(Move move) {
		Square start = move.getStart();
		Square end = move.getEnd();
		ChessFigure mover = start.getPiece();
		int dX = start.getFile() - end.getFile();
		int dY = start.getRank() - end.getRank();
		
		//does the move have a start, an end, and a piece?
		if (start == null || end == null || start.isEmpty()) return false;
		
		//are any of the parameters out of bounds?
		if (start.getFile() >= Board.FILES || start.getFile() < 0) return false;
		if (start.getRank() >= Board.RANKS || start.getRank() < 0) return false;
		if (end.getFile() >= Board.FILES || end.getFile() < 0) return false;
		if (end.getRank() >= Board.FILES || end.getRank() < 0) return false;
		
		//is it the piece's turn?
		if (mover.getSide() != turn) {
			System.out.println("not " + mover.getSide() + "'s turn.");
			return false;
		}
		
		//is the piece trying to take an ally?
		if (!end.isEmpty()) {
			if (mover.getSide() == end.getPiece().getSide()) {
				return false;
			}
		}
		
		//is their a clear and valid path to the destination?
		switch (mover.getType()) {
			case PAWN:	//TODO - en passanting
				if (dX == 0 ) { //if the pawn is not moving side to side 
					if (mover.getSide() == Side.BLACK) {
						//is there something in the pawn's way?
						if (!board[start.getFile()][start.getRank()+1].isEmpty()) return false;
						//is the pawn moving backward?
						if (end.getRank() - start.getRank() < 0) return false;
						
						//is the pawn moving too far forward
						if (end.getRank()-start.getRank() > 1) {
							if (start.getRank() != 1 || end.getRank() != 3) {
								return false;
							}
						}	
					}else {
						//is there something in the pawn's way?
						if (!board[start.getFile()][start.getRank()-1].isEmpty()) return false;
						//is the pawn moving backward?
						if (start.getRank() - end.getRank() < 0) return false;
						
						//is the pawn moving too far forward
						if (start.getRank()-end.getRank() > 1) {
							if (start.getRank() != 6 || end.getRank() != 4) {
								return false;
							}
						}
					} 
				} else{ //the pawn is moving left or right
					if (Math.abs(dY) != 1) return false; // if the pawn is moving more than one space forward, it cannot take.
					if (Math.abs(dX) == 1) {//is the pawn moving left only one
						if (end.getPiece() != null) {//is the pawn taking
							if (end.getPiece().getSide() != mover.getSide()); //OK if the pawn is taking an enemy piece
							else return false; //otherwise fail V
						} else return false;
					} else {
						return false;
					}
				}
				break;
			case KNIGHT:
				//either dX is one and Dy is 2 or vice versa, else = fail
				if (  ( (Math.abs(dX) == 2) && (Math.abs(dY) == 1) ) || ( (Math.abs(dX) == 1) && (Math.abs(dY) == 2) ) ) {}
				else return false;
				break;
			case ROOK:
				if (start.getFile() != end.getFile()) { //if the rook is moving in the x direction
					
					//is the rook also moving in the y direction?
					if (start.getRank() != end.getRank()) {
						return false;
					}
					
					//is there anything in the rook's way
					if (end.getFile() > start.getFile()) { //moving to the right 
						for (int i = start.getFile()+1; i < end.getFile(); i ++) {
							if (!board[i][start.getRank()].isEmpty()) return false;
						}
					} else { //moving to the left
						for (int i = start.getFile()-1; i > end.getFile(); i --) {
							if (!board[i][start.getRank()].isEmpty()) return false;
						}
					}
					
				}else {//Rook is moving only in the y direction
					if (end.getRank() > start.getRank()) { //moving down 
						for (int i = start.getRank()+1; i < end.getRank(); i ++) {
							if (!board[start.getFile()][i].isEmpty()) return false;
						}
					} else { //moving up
						for (int i = start.getRank()-1; i > end.getRank(); i --) {
							if (!board[start.getFile()][i].isEmpty()) return false;
						}
					}
				}
				break;
			case BISHOP: //TODO - Come up with a more elegant solution than using varname1 here and varname in queen
				//note to self: use new boolean clearPath(start, end, xInc, yInc) method for queen, bishop, and rook. Remove nasty rank and file increment names. 
				//the same as a queen except it must move in both directions
				
				//if the bishop is not moving in a direction, or is not moving the same distance in both directions 
				if (dX == 0 || dY == 0 || (Math.abs(dX) != Math.abs(dY))) return false;  
				
				//if there is anything in the bishop's way
				//x and y incrementors mark the pattern to use checking squares
				int xInc1 = (int) Math.signum(dX);
				int yInc1 = (int) Math.signum(dY); 
				int rank1 = start.getRank(), file1 = start.getFile();
				while (rank1 != end.getRank()+yInc1 || file1 != end.getFile()+xInc1) { //iterate until you reach the square before the end
					rank1 -= yInc1;
					file1 -= xInc1;
					if (!board[file1][rank1].isEmpty()) return false; //if that square has a piece on it, move is invalid
				}
				break;
			case QUEEN:
				
				if ((dX == 0 && dY !=0)  || (dY == 0 && dX != 0 )) ; //if the queen is moving only in one direction
				else {
					if (dX == 0 && dY == 0) return false; // if the queen is not moving
					if (Math.abs(dX) != Math.abs(dY)) return false; //if the queen is not moving in a straight line
				}
				
				//if there is anything in the queen's way
				//x and y incrementors mark the pattern to use checking squares
				int xInc = (int) Math.signum(dX);
				int yInc = (int) Math.signum(dY); 
				int rank = start.getRank(), file = start.getFile();
				while (rank != end.getRank()+yInc || file != end.getFile()+xInc) { //iterate until you reach the square before the end
					rank -= yInc;
					file -= xInc;
					if (!board[file][rank].isEmpty()) return false; //if that square has a piece on it, move is invalid
				}
				break;
			case KING: //TODO castling
				//is the king moving too far
				if (Math.abs(dX) > 1) return false;
				if (Math.abs(dY) > 1) return false;
				
				if (mover.getSide() == Side.WHITE && isControl(Side.BLACK, end.getRank(), end.getFile())) return false; 
				if (mover.getSide() == Side.BLACK && isControl(Side.WHITE, end.getRank(), end.getFile())) return false;
				break;
		}
		
		//no tests failed, move is valid
		return true;
	}
	
	/**
	 * a square is under the control of a side if that side has a piece that can legally move onto the square
	 * @param side - the side to check control for
	 * @param rank - the rank of the square to check 
	 * @param file - the file of the square to check 
	 * @return true if any piece of Side side can legally move onto the square at rank, file, or if a pawn of Side side could take on that square.
	 */
	public boolean isControl(Side side, int rank, int file){
		
		Side oldSide = turn;
		turn = side;
		
		if (side == Side.WHITE) {
			board[file][rank].setPiece(new ChessFigure(ChessFigure.FigureType.PAWN, Side.BLACK));
		} else {
			board[file][rank].setPiece(new ChessFigure(ChessFigure.FigureType.PAWN, Side.WHITE));
		}
		
		System.out.println("Checking control of (" + rank + ", " + file + ") for side: " + side);
		//for every piece on the board
		for (int i = 0; i < Board.RANKS; i ++) {
			for (int j = 0; j < Board.FILES; j ++) {
				//if that piece can move onto this square
				if (!board[j][i].isEmpty() && board[j][i].getPiece().getSide() == side) {
					System.out.println("Moving piece on (" + i + ", " + j + ") to (" + rank + ", " + file + ")" ) ;
					if (isValidMove(new Move(board[j][i], board[file][rank]))){
						System.out.println("Move worked");
						turn = oldSide;
						board[file][rank].setPiece(null);
						return true;
					}
				}
			}
		}
		board[file][rank].setPiece(null);
		turn = oldSide;
		return false;
	}
	
	/**
	 * applies a move to the board
	 * @param move - a move to be made
	 */
	public void makeMove(Move move) {
		move.getEnd().setPiece(move.getStart().getPiece());
		move.getStart().setPiece(null);

		
		if (turn == Side.WHITE) turn = Side.BLACK;
		else turn = Side.WHITE;
	}
	
	/**
	 * who's turn is it? 
	 * @return
	 */
	public Side getTurn() {
		return turn;
	}
	
	/**
	 * if the king in check mate
	 * @param side - the side of the king to check for check mate (Side.BLACK or Side.WHITE)
	 * @return - true if the king is in check mate
	 */
	@SuppressWarnings("unlikely-arg-type")
	public boolean isCheckMate(Side side) {
		Square kingSquare = null;
		for (int i = 0; i < Board.RANKS; i ++) {
			for (int j = 0; j < Board.FILES; j ++) {
				if (board[j][i].getPiece().equals(ChessFigure.FigureType.KING) && board[j][i].getPiece().getSide() == side) {
					kingSquare = board[i][j];
					break;
				}
			}
		}
		
		if (kingSquare == null) {
			System.err.println("There was no king on the board");
		}
		
		// TODO check if the check can be blocked by any piece.
		
		//if the king can move to any squares near it, it is not in checkMate
		if (isValidMove(new Move(kingSquare, board[kingSquare.getFile()+1][kingSquare.getRank()+1]))) return false;
		if (isValidMove(new Move(kingSquare, board[kingSquare.getFile()+1][kingSquare.getRank()]))) return false;
		if (isValidMove(new Move(kingSquare, board[kingSquare.getFile()+1][kingSquare.getRank()-1]))) return false;
		
		if (isValidMove(new Move(kingSquare, board[kingSquare.getFile()][kingSquare.getRank()+1]))) return false;
		if (isValidMove(new Move(kingSquare, board[kingSquare.getFile()][kingSquare.getRank()-1]))) return false;
		
		if (isValidMove(new Move(kingSquare, board[kingSquare.getFile()-1][kingSquare.getRank()+1]))) return false;
		if (isValidMove(new Move(kingSquare, board[kingSquare.getFile()-1][kingSquare.getRank()]))) return false;
		if (isValidMove(new Move(kingSquare, board[kingSquare.getFile()-1][kingSquare.getRank()-1]))) return false;

		//the king cannot move to any square
		return true;
	}
	
	/**
	 * @return is the white king in check mate?
	 */
	public boolean isCheckMateWhite() {
		return isCheckMate(Side.WHITE);
	}
	
	/**
	 * @return is the black king in check mate?
	 */
	public boolean isCheckMateBlack() {
		return isCheckMate(Side.BLACK);
	}
	
}
