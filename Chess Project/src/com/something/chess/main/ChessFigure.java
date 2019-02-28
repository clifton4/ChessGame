package com.something.chess.main;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import com.something.chess.main.Board.Side;

public class ChessFigure extends GameObject{
	
	public enum FigureType{
		PAWN(),
		KNIGHT,
		BISHOP,
		ROOK,
		QUEEN,
		KING;
	}
	
	private FigureType type;
	private Image image;
	private Side side;
	
	public ChessFigure(FigureType type, Side side) {
		super(0 , 0);
		this.type = type;
		this.side = side;
		
		
		//Get the image
		
		loadImage();
		
	}
	
	private void loadImage() {
		String imagePath = "resources\\" + this.type.toString() + "_" + this.side.toString() + ".png";
		image = new ImageIcon(imagePath).getImage();

	}
	
	public ChessFigure.FigureType getType(){
		return type;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}	

	public Image getImage() {
		return image;
	}
	
}
