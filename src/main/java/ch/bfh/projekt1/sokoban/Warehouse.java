package ch.bfh.projekt1.sokoban;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class Warehouse extends JPanel implements KeyListener {
	public static final int WIDTH = 30;
	public static final int YES = 0;
	public static final String MESSAGE = "Sie haben das Spiel gewonnen. "
			+ "Wollen Sie das nächste Problem spielen?";
	private List<GameElement> gameElements;
	private Observer observer;

	public Warehouse() {
		addKeyListener(this);
		this.setBackground(Color.WHITE);
		observer = new Observer();
		initGame();

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
		if (observer.checkFinish()) {
			int option = JOptionPane.showConfirmDialog(this, MESSAGE,
					"Gewonnen", JOptionPane.YES_NO_OPTION);
			if (option == YES) {
				observer.higherLevel();
			}
			initGame();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void initGame() {
		observer.initGameElements();
		gameElements = observer.initWarehouse();
		repaint();
	}
}
