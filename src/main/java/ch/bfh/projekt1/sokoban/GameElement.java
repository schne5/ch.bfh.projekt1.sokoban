package ch.bfh.projekt1.sokoban;

import java.awt.Image;

public abstract class GameElement {
	private int posX;
	private int posY;
	private Image image;
	
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}

}
