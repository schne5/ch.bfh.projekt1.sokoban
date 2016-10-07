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
	private Model model;

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	private Controller controller;

	public Warehouse() {
		addKeyListener(this);
		this.setBackground(Color.WHITE);
		model = new Model();
		controller = new Controller(model);
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
			controller.move(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			controller.move(Direction.DOWN);
			break;
		case KeyEvent.VK_RIGHT:
			controller.move(Direction.RIGHT);
			break;
		case KeyEvent.VK_LEFT:
			controller.move(Direction.LEFT);
			break;
		}
		repaint();
		if (model.checkFinish()) {
			int option = JOptionPane.showConfirmDialog(this, MESSAGE,
					"Gewonnen", JOptionPane.YES_NO_OPTION);
			if (option == YES) {
				model.higherLevel();
			}
			initGame();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void initGame() {
		model.initGameElements();
		gameElements = controller.initWarehouse();
		repaint();
	}
}
