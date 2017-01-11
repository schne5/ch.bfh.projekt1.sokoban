package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class ProblemDesigner extends JFrame {
	private static final long serialVersionUID = 1L;
	public static int SPACE = 30;
	public static final String OK = "Ok";
	public static final String LOAD = "Load";
	public static final String CANCEL = "Exit";
	public static final String CLEAR = "Clear";
	public static final String VALIDATION_AREA = "Kein geschlossenes Spielfeld!!";
	public static final String VALIDATION_GAMEELEMENTS = "Anzahl Spielfiguren nicht gueltig";
	public static final String ERROR_LOAD = "Spiel konnte ncht geladen werden:";
	public static final String CHOOSE_GAME = "Spiel waehlen:";
	public static final String INPUT = "Spielnamenzusatz eingeben:";

	ArrayList<JLabel> elements = new ArrayList<JLabel>();
	MouseListener mouseListener;
	ProblemDesignArea problemDesignArea;
	JPanel gameElementPanel;
	JButton ok;
	JButton loadOwnProblem;
	JButton cancel;
	JButton clear;
	JPanel buttonPanel;
	String activeGame;

	public ProblemDesigner() {
		initMouseListener();
		gameElementPanel = new JPanel();
		addGameElementViews();
		problemDesignArea = new ProblemDesignArea();
		initButtons();
		add(gameElementPanel, BorderLayout.NORTH);
		add(problemDesignArea, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		activeGame = "";
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
			if (problemDesignArea.valid()) {
				GraphTuple[][] gameElements = problemDesignArea.prepareSave();
				if (validWalls(gameElements)) {
					Controller
							.saveCustomProblem(gameElements, null, activeGame);
					this.dispose();
					MainSokoban.showInitDialog();
				} else {
					JOptionPane.showMessageDialog(this, VALIDATION_AREA);
				}
			} else {
				JOptionPane.showMessageDialog(this, VALIDATION_GAMEELEMENTS);
			}

		});

		loadOwnProblem = new JButton();
		loadOwnProblem.setText(LOAD);
		loadOwnProblem.addActionListener(t -> {
			openFileSelectionFrame(GameSaver.PATH_CUSTOM_PROBLEMS, true);
		});

		cancel = new JButton(CANCEL);
		cancel.addActionListener(a -> {
			this.dispose();
			MainSokoban.showInitDialog();
		});
		buttonPanel.add(ok);
		buttonPanel.add(loadOwnProblem);
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

			}

			@Override
			public void mouseClicked(MouseEvent e) {

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
							ProblemDesignArea.WIDTH, ProblemDesignArea.HEIGHT);
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

	private void openFileSelectionFrame(String path, boolean ownGame) {
		JFrame dialog = new JFrame();
		JComboBox<String> files = new JComboBox<String>(
				GameSaver.loadAllFiles(path));
		files.setSize(300, 100);
		JLabel label = new JLabel(CHOOSE_GAME);
		JButton ok = new JButton(OK);
		ok.setMaximumSize(new Dimension(40, 40));
		dialog.add(files, BorderLayout.CENTER);
		dialog.add(label, BorderLayout.NORTH);
		dialog.add(ok, BorderLayout.SOUTH);
		files.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		dialog.pack();
		dialog.setVisible(true);
		ok.addActionListener(a -> {
			String selected = files.getSelectedItem().toString();
			if (!"".equals(selected) && null != selected) {
				try {
					problemDesignArea.reset();
					problemDesignArea.drawArea(selected);
					activeGame = selected;
					problemDesignArea.refresh();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, ERROR_LOAD + selected);
				}
			}
			dialog.dispose();
			this.setVisible(true);
		});
	}

	public String getUserInput() {
		return JOptionPane.showInputDialog(this, INPUT, JOptionPane.OK_OPTION)
				.trim();
	}
}
