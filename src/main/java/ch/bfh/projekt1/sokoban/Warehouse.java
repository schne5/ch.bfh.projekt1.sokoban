package ch.bfh.projekt1.sokoban;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class Warehouse extends JPanel implements KeyListener, MouseListener {
	public static final int IMAGE_WIDTH = 30;
	public static final int YES = 0;
	public static final String MESSAGE = "Sie haben das Spiel gewonnen. "
			+ "Wollen Sie das naechste Problem spielen?";
	GameElementView[][] gameAreaView;
	private Model model;
	private Controller controller;
	GridLayout layout;

	public Warehouse(Controller controller) {
		addKeyListener(this);
		this.setBackground(Color.WHITE);
		this.controller = controller;
		this.model = controller.getModel();
		initGame();
		layout = new GridLayout(model.getHeight(), model.getWidth());
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
		// paintInitGameArea();
		setPreferredSize(new Dimension(model.getWidth() * IMAGE_WIDTH,
				model.getHeight() * IMAGE_WIDTH));
	}

	// public Warehouse() {
	// addKeyListener(this);
	// this.setBackground(Color.WHITE);
	// model = new Model();
	// controller = new Controller(model);
	// initGame();
	// layout = new GridLayout(model.getHeight(), model.getWidth());
	// layout.setHgap(0);
	// layout.setVgap(0);
	// setLayout(layout);
	// paintInitGameArea();
	// setPreferredSize(new Dimension(model.getWidth() * IMAGE_WIDTH,
	// model.getHeight() * IMAGE_WIDTH));
	//
	// }

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

		redraw();

		if (model.checkFinish()) {
			int option = JOptionPane.showConfirmDialog(this, MESSAGE,
					"Gewonnen", JOptionPane.YES_NO_OPTION);
			if (option == YES) {
				model.higherLevel();
				removeAll();
				initGame();
				paintInitGameArea();
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void redraw() {
		int width = model.getWidth();
		int height = model.getHeight();

		GraphTuple[][] gameArea = model.getGameArea();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				gameAreaView[x][y].changeType(GameElementUtile.getType(
						gameArea, x, y));
			}
		}

	}

	public void initGame() {
		model.initGameElements();
		model.setGameArea(controller.loadProblem());
	}

	public void paintInitGameArea() {
		int width = model.getWidth();
		int height = model.getHeight();
		gameAreaView = new GameElementView[width][height];

		GraphTuple[][] gameArea = model.getGameArea();
		GameElementView gameElementView = null;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				gameElementView = GameElementView.create(
						GameElementUtile.getType(gameArea, x, y), this, x, y);
				add(gameElementView);
				gameAreaView[x][y] = gameElementView;
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		GameElementView gameElementView = (GameElementView) e.getSource();

		new Thread() {
			public void run() {
				ArrayList<Position> path = controller.getPath(gameElementView
						.getPosition());
				if (!path.isEmpty()) {

					for (int i = path.size() - 1; i >= 0; i--) {
						controller.move(path.get(i), Activity.MOVE, null);
						redraw();

						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

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
}
