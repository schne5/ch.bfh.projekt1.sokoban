package ch.bfh.projekt1.sokoban;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class Warehouse extends JPanel implements KeyListener {
	public static final int IMAGE_WIDTH = 30;
	public static final int YES = 0;
	public static final String MESSAGE = "Sie haben das Spiel gewonnen. "
			+ "Wollen Sie das naechste Problem spielen?";
	private Model model;
	private Controller controller;

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
		this.controller.setModel(model);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

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
		GraphTuple[][] gameArea = model.getGameArea();
		for (int y = 0; y < gameArea[0].length; y++) {
			for (int x = 0; x < gameArea.length; x++) {
				switch (gameArea[x][y].getGameElementType()) {
				case WALL:
					g.drawImage(Wall.loadImage(),
							x * IMAGE_WIDTH + IMAGE_WIDTH, y * IMAGE_WIDTH
									+ IMAGE_WIDTH, IMAGE_WIDTH, IMAGE_WIDTH,
							this);
					break;
				case BOX:
					g.drawImage(Box.loadImage(), x * IMAGE_WIDTH + IMAGE_WIDTH,
							y * IMAGE_WIDTH + IMAGE_WIDTH, IMAGE_WIDTH,
							IMAGE_WIDTH, this);
					break;
				case STORAGE:
					g.drawImage(Storage.loadImage(), x * IMAGE_WIDTH
							+ IMAGE_WIDTH, y * IMAGE_WIDTH + IMAGE_WIDTH,
							IMAGE_WIDTH, IMAGE_WIDTH, this);
					break;
				case PAWN:
					g.drawImage(Pawn.loadImage(),
							x * IMAGE_WIDTH + IMAGE_WIDTH, y * IMAGE_WIDTH
									+ IMAGE_WIDTH, IMAGE_WIDTH, IMAGE_WIDTH,
							this);
					break;
				case FLOOR:
					g.drawImage(Floor.loadImage(), x * IMAGE_WIDTH
							+ IMAGE_WIDTH, y * IMAGE_WIDTH + IMAGE_WIDTH,
							IMAGE_WIDTH, IMAGE_WIDTH, this);
					break;
				case PAWN_ON_STORAGE:
					g.drawImage(Pawn.loadImage(),
							x * IMAGE_WIDTH + IMAGE_WIDTH, y * IMAGE_WIDTH
									+ IMAGE_WIDTH, IMAGE_WIDTH, IMAGE_WIDTH,
							this);
					break;
				case BOX_ON_STORAGE:
					g.drawImage(Box.loadImage(), x * IMAGE_WIDTH + IMAGE_WIDTH,
							y * IMAGE_WIDTH + IMAGE_WIDTH, IMAGE_WIDTH,
							IMAGE_WIDTH, this);
					break;
				}

			}
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
		// Nur für Test movetoField
		case KeyEvent.VK_P:

			new Thread() {
				public void run() {
					ArrayList<Position> path = controller.getPath(new Position(
							8, 4));
					if (!path.isEmpty()) {

						for (int i = path.size() - 1; i >= 0; i--) {
							controller.move(path.get(i), Activity.MOVE, null);
							repaint();

							try {
								Thread.sleep(400);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}.start();

			System.out.println(Thread.currentThread());
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
		model.setGameArea(controller.loadProblem());
		repaint();
	}
}
