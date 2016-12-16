package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class ProblemDesigner extends JFrame {
	public static int SPACE = 30;
	ArrayList<JLabel> elements = new ArrayList<JLabel>();
	MouseListener mouseListener;
	ProblemDesignArea problemDesignArea;
	JPanel gameElementPanel;
	JButton ok;
	JButton cancel;
	JButton clear;
	JPanel buttonPanel;

	public static String OK = "Ok";
	public static String CANCEL = "Exit";
	public static String CLEAR = "Clear";

	public ProblemDesigner() {
		initMouseListener();
		gameElementPanel = new JPanel();
		addGameElementViews();
		problemDesignArea = new ProblemDesignArea();
		initButtons();
		add(gameElementPanel, BorderLayout.NORTH);
		add(problemDesignArea, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void addGameElementViews() {
		gameElementPanel.add(GameElementView.create(GameElementType.PAWN,
				mouseListener, true));
		gameElementPanel.add(GameElementView.create(GameElementType.BOX,
				mouseListener, true));
		gameElementPanel.add(GameElementView.create(GameElementType.WALL,
				mouseListener, true));
		gameElementPanel.add(GameElementView.create(GameElementType.STORAGE,
				mouseListener, true));
		gameElementPanel.add(GameElementView.create(GameElementType.FLOOR,
				mouseListener, true));
		gameElementPanel.add(GameElementView.create(
				GameElementType.PAWN_ON_STORAGE, mouseListener, true));
		gameElementPanel.add(GameElementView.create(
				GameElementType.BOX_ON_STORAGE, mouseListener, true));
	}

	private void initButtons() {
		buttonPanel = new JPanel();
		ok = new JButton(OK);
		ok.addActionListener(a -> {
			// Problem abspeichern
			if (problemDesignArea.valid()) {
				GraphTuple[][] gameElements = problemDesignArea.prepareSave();
				if (validWalls(gameElements)) {
					Controller.saveCustomProblem(gameElements);
					this.dispose();
					MainSokoban.showInitDialog();
				} else {
					JOptionPane.showMessageDialog(this,
							"Kein geschlossenes Spielfeld!!");
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Anzahl Spielfiguren nicht gueltig");
			}

		});
		cancel = new JButton(CANCEL);
		cancel.addActionListener(a -> {
			// Abbrechen und zur startseite
			this.dispose();
			MainSokoban.showInitDialog();
		});
		clear = new JButton(CLEAR);
		clear.addActionListener(a -> {
			// TODO Clear
		});
		buttonPanel.add(ok);
		// buttonPanel.add(clear);
		buttonPanel.add(cancel);
	}

	private void initMouseListener() {
		mouseListener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

				GameElementView gameElementView = (GameElementView) e
						.getSource();
				problemDesignArea.setActiveType(gameElementView.getType());
				updateGameElementTemplates();
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
	}

	public void updateGameElementTemplates() {
		Component[] templates = gameElementPanel.getComponents();
		for (Component template : templates) {
			if (template instanceof GameElementView) {
				GameElementView gameElementView = (GameElementView) template;

				if (gameElementView.getType() == problemDesignArea
						.getActiveType()) {
					gameElementView.setEnabled(false);
				} else {
					gameElementView.setEnabled(true);
				}
			}
		}
	}

	private List<Position> getEndPositions() {
		List<Position> positions = new ArrayList<Position>();
		for (int x = 0; x < ProblemDesignArea.WIDTH; x++) {
			if (problemDesignArea.getGameArea()[x][0].getType() != GameElementType.WALL) {
				positions.add(new Position(x, 0));
			}
			if (problemDesignArea.getGameArea()[x][ProblemDesignArea.HEIGHT - 1]
					.getType() != GameElementType.WALL) {
				positions.add(new Position(x, ProblemDesignArea.HEIGHT - 1));
			}
		}
		for (int y = 1; y < ProblemDesignArea.HEIGHT - 1; y++) {
			if (problemDesignArea.getGameArea()[0][y].getType() != GameElementType.WALL) {
				positions.add(new Position(0, y));
			}
			if (problemDesignArea.getGameArea()[ProblemDesignArea.WIDTH - 1][y]
					.getType() != GameElementType.WALL) {
				positions.add(new Position(ProblemDesignArea.WIDTH - 1, y));
			}
		}
		return positions;
	}

	private boolean validWalls(GraphTuple[][] gameElements) {
		SokobanQueue<Position> queue;
		List<Position> positions = getEndPositions();
		for (Position position : positions) {
			queue = new SokobanQueue<Position>();
			if (findPath(position, queue, problemDesignArea.getPawnPosition(),
					gameElements)) {
				return false;
			}
		}
		return true;
	}

	public boolean findPath(Position target, SokobanQueue<Position> queue,
			Position pawnPosition, GraphTuple[][] gameElements) {
		queue.enqueue(pawnPosition);
		while (!queue.isEmpty()) {
			Position first = queue.dequeue();
			ArrayList<Position> neighbours = GameElementUtile
					.getValidNeighbours(first, gameElements, true,
							ProblemDesignArea.WIDTH, problemDesignArea.HEIGHT);
			int x;
			int y;
			for (Position neighbour : neighbours) {
				x = neighbour.getPosX();
				y = neighbour.getPosY();

				if (gameElements[x][y].getPosition() == null) {
					gameElements[x][y].setPosition(first);
					if (neighbour.equals(target)) {
						return true;
					}
					queue.enqueue(neighbour);
				}
			}
		}
		return false;
	}
}
