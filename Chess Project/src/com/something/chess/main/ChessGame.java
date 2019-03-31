package com.something.chess.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;

import com.something.chess.main.Board.Side;

/**
 * Represents the whole chess game.
 * TODO - Use a little MVC design pattern logic to clean this up
 * @author clift
 *
 */
public class ChessGame extends Canvas implements Runnable{

	private static final long serialVersionUID = 1069637915155982497L;
	
	public static final int WIDTH = 800, HEIGHT = 800;
	
	private boolean running = false;
	private Thread thread;
	private InputReader playerOne;
	private Board board;
	
	/**
	 * creates a new game and a new Window object to display it.
	 */
	public ChessGame() {
		new Window (WIDTH, HEIGHT, "Chess by Clifton", this);
		
		this.board = new Board(this);
		playerOne = new InputReader(board);
		this.addMouseListener(playerOne);
				
	}

	/**
	 * basic game loop from online. 
	 * TODO: no need for a full game loop and high framerate with a turn 
	 *  based game
	 */
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				try {
					tick();
				} catch (NullPointerException e) {}
				delta --;
			}
			if(running) 
				render();
			frames ++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	
	/**
	 * start the ChessGame
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	
	/**
	 * Stop the ChessGame
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ticks all Objects in this game. Is this really necessary for a turn
	 * based game? 
	 */
	private void tick() throws NullPointerException{

		if (playerOne.isReady()) {
			if (board.isValidMove(playerOne.getMove())) {
				board.makeMove(playerOne.getMove());
			}
			playerOne.reset();
		} 
		
		if (board.isCheckMateWhite()) running = false;
		if (board.isCheckMateBlack()) running = false;
	}
	
	/**
	 * renders to the screen
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs .getDrawGraphics();
		
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		try {
			board.render(g);
			playerOne.render(g);
		} catch(NullPointerException npe) {
			//waiting for startup
		}
		
		g.dispose();
		bs.show();
	}
	

	
}
