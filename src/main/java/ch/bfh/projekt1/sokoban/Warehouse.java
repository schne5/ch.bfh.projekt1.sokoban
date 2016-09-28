package ch.bfh.projekt1.sokoban;

import java.awt.Graphics;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Warehouse extends JPanel {
	private List<GameElement> gameElements;
	private Observer  observer;
	
	public Warehouse() {
		observer = new Observer();
		gameElements = observer.initWarehouse(null);
		repaint();
	}
	
	 @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        for (GameElement gameElement : gameElements){
	        	g.drawImage(gameElement.getImage(), gameElement.getPosX(), gameElement.getPosY(), this);
	        }   
	    }
	 
	 

}
