package com.something.chess.main;

public class Move {

	public final Square start;
	public final Square end;
	
	public Move(Square start, Square end) {
		this.start = start;
		this.end = end;
		
		//this could be the place to test if the move is valid
		//throw invalid move exception
	}

	public Square getEnd() {
		return end;
	}
	
	public Square getStart() {
		return start;
	}
	
	
}
