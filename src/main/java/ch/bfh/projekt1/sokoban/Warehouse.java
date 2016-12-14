package ch.bfh.projekt1.sokoban;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
	public static final String MESSAGE_OWN = "Sie haben das Spiel gewonnen.";
	GameElementView[][] gameAreaView;
	private Model model;
	private Controller controller;
	GridLayout layout;

	private JFrame parent;
	private JLabel moves;
	private JLabel pushes;

	public Warehouse(Controller controller) {
		addKeyListener(this);
		this.setBackground(Color.WHITE);
		this.controller = controller;
		this.model = controller.getModel();
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
		redraw();

		if (model.checkFinish()) {
			if (model.isOwnProblem()) {
				JOptionPane.showConfirmDialog(this, MESSAGE_OWN, "Gewonnen",
						JOptionPane.OK_OPTION);
				reset();
				refresh();

			} else {
				int option = JOptionPane.showConfirmDialog(this, MESSAGE,
						"Gewonnen", JOptionPane.YES_NO_OPTION);
				if (option == YES) {
					model.higherLevel();
					reset();
					initGame();
					paintInitGameArea();
					refresh();
				}

			}

		}
		moves.setText(model.getMoves() + "");
		pushes.setText(model.getPushes() + "");
		parent.repaint();
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
		moves.setText(model.getMoves() + "");
		pushes.setText(model.getPushes() + "");
		parent.repaint();
	}

	public void initGame() {
		model.initGameElements();
		model.setGameArea(controller.loadProblem());
		init();
	}

	public void initGame(boolean own, String selected) {
		model.initGameElements();
		if (selected != null && !selected.equals("")) {
			if (own) {
				model.setGameArea(controller.loadCustomProblem(selected));
			} else {
				model.setGameArea(controller.loadGame(selected));
			}
			init();
		}

	}

	public void init() {
		setPreferredSize(new Dimension(controller.getModel().getWidth() * 30,
				controller.getModel().getHeight() * 30));
		layout = new GridLayout(model.getHeight(), model.getWidth());
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
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
						GameElementUtile.getType(gameArea, x, y), this, x, y,
						false);
				add(gameElementView);
				gameAreaView[x][y] = gameElementView;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mousePressed(MouseEvent e) {

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

	public void reset() {
		this.removeAll();
		this.gameAreaView = null;
		moves.setText(model.getMoves() + "");
		pushes.setText(model.getPushes() + "");
		parent.repaint();
	}

	public void refresh() {
		revalidate();
		repaint();
		parent.pack();
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

	public JFrame getParent() {
		return parent;
	}

	public void setParent(JFrame parent, JLabel moves, JLabel pushes) {
		this.parent = parent;
		this.moves = moves;
		this.pushes = pushes;
	}
}
