package ch.bfh.projekt1.sokoban;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JPanel;

public class Warehouse extends JPanel implements KeyListener {
	public static final int WIDTH = 30;
	private List<GameElement> gameElements;
	private Observer observer;

	public Warehouse() {
		addKeyListener(this);
		this.setBackground(Color.WHITE);
		observer = new Observer();
		gameElements = observer.initWarehouse(null);
		repaint();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (GameElement gameElement : gameElements) {
			g.drawImage(gameElement.getImage(), gameElement.getPosition()
					.getPosX(), gameElement.getPosition().getPosY(), WIDTH,
					WIDTH, this);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			observer.move(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			observer.move(Direction.DOWN);
			break;
		case KeyEvent.VK_RIGHT:
			observer.move(Direction.RIGHT);
			break;
		case KeyEvent.VK_LEFT:
			observer.move(Direction.LEFT);
			break;
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			observer.move(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			observer.move(Direction.DOWN);
			break;
		case KeyEvent.VK_RIGHT:
			observer.move(Direction.RIGHT);
			break;
		case KeyEvent.VK_LEFT:
			observer.move(Direction.LEFT);
			break;
		}
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
