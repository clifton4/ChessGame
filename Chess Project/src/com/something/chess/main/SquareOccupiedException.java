package com.something.chess.main;

public class SquareOccupiedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SquareOccupiedException(String msg) {
		super(msg);
		System.out.println(msg);
	}

}
