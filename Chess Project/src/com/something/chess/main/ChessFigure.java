package com.something.chess.main;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import com.something.chess.main.Board.Side;

public class ChessFigure implements Renderable{
	
	public enum FigureType{
		PAWN,
		KNIGHT,
		BISHOP,
		ROOK,
		QUEEN,
		KING;
	}
	
	private FigureType type;
	private Image image;
	private Board.Side side;

	
	public ChessFigure(FigureType type, Side side) {

		this.type = type;
		this.side = side;
		
		
		//Get the image from resources.
		loadImage();
	}
	
	private void loadImage() {
		String imagePath = "resources\\" + this.type.toString() + "_" + this.side.toString() + ".png";
		image = new ImageIcon(imagePath).getImage();

	}
	
	public ChessFigure.FigureType getType(){
		return type;
	}
	
	public Board.Side getSide(){
		return side;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}	

	public Image getImage() {
		return image;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ChessFigure) {
			return ((ChessFigure)o).getType() == this.getType();
		} if (o instanceof ChessFigure.FigureType) {
			return o == getType();
		}
		return false;
	}
	
}
