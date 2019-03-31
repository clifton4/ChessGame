package com.something.chess.main;

public class InvalidMoveException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidMoveException(String msg) {
		super(msg);
		System.out.println(msg);
	}

}
