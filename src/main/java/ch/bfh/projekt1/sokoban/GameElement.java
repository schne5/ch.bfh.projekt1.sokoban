package ch.bfh.projekt1.sokoban;

import java.awt.Image;

public abstract class GameElement {
	public static final String IMAGE_PATH ="images/./";
	private int posX;
	private int posY;
	private Image image;
	
	public GameElement(int posX, int posY) {
		setPosX(posX);
		setPosY(posY);
	}
	
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
