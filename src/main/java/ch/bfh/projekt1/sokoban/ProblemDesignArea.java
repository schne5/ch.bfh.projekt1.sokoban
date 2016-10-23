package ch.bfh.projekt1.sokoban;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ProblemDesignArea extends JPanel {
	List<GameElement> area;

	public ProblemDesignArea() {
		area = new ArrayList<GameElement>();
		drawArea();
		repaint();
	}

	public void drawArea() {
		int posX = 175;
		int posY = 50;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 20; j++) {
				area.add(new Floor(new Position(j * GameElementUtile.WIDTH
						+ posX, i * GameElementUtile.WIDTH + posY)));
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (GameElement gameElement : area) {
			g.drawImage(gameElement.getImage(), gameElement.getPosition()
					.getPosX(), gameElement.getPosition().getPosY(),
					GameElementUtile.WIDTH, GameElementUtile.WIDTH, this);
		}
	}

}
