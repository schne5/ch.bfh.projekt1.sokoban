package ch.bfh.projekt1.sokoban;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

/*
 *@author:Elisa
 */
public class ProblemDesignArea extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 20;
	public static final int HEIGHT = 16;
	private GameElementView[][] gameArea;

	GridLayout layout;
	GameElementType activeType;
	private Position pawnPosition;

	public ProblemDesignArea() {
		gameArea = new GameElementView[WIDTH][HEIGHT];
		layout = new GridLayout(HEIGHT, WIDTH);
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
		drawEmptyArea();
	}

	public void drawEmptyArea() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				GameElementView view = GameElementView.create(
						GameElementType.FLOOR, null, true);
				add(view);
				gameArea[x][y] = view;
				view.addMouseListener(this);

			}
		}
	}

	public void reset() {
		this.removeAll();
		this.gameArea = new GameElementView[WIDTH][HEIGHT];
	}

	public void refresh() {
		revalidate();
		repaint();
	}

	public void drawArea(String selected) {
		List<String> lines = GameSaver.loadCustomProblems(selected);
		GameAreaGraph gameAreaGraph = new GameAreaGraph();
		gameAreaGraph.createGraph(new GraphTuple[WIDTH][HEIGHT], lines, WIDTH);
		GraphTuple[][] area = gameAreaGraph.getArea();
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				GameElementView view = GameElementView.create(
						area[x][y].getGameElementType(), null, true);
				add(view);
				gameArea[x][y] = view;
				view.addMouseListener(this);

			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (activeType != null) {
			GameElementView gameElementView = (GameElementView) e.getSource();
			gameElementView.changeType(getActiveType());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public GameElementType getActiveType() {
		return activeType;
	}

	public void setActiveType(GameElementType activeType) {
		this.activeType = activeType;
	}

	public GraphTuple[][] prepareSave() {
		GraphTuple[][] gameElements = new GraphTuple[WIDTH][HEIGHT];

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				gameElements[x][y] = new GraphTuple(gameArea[x][y].getType());
			}
		}
		return gameElements;
	}

	public boolean valid() {
		int boxes = 0;
		int storages = 0;
		int pawn = 0;

		GameElementType type;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				type = gameArea[x][y].getType();
				switch (type) {
				case PAWN:
					pawn++;
					pawnPosition = new Position(x, y);
					break;
				case PAWN_ON_STORAGE:
					pawn++;
					storages++;
					pawnPosition = new Position(x, y);
					break;
				case STORAGE:
					storages++;
					break;
				case BOX:
					boxes++;
					break;
				case BOX_ON_STORAGE:
					boxes++;
					storages++;
					break;
				}
			}
		}
		if (boxes == storages && boxes > 0 && pawn == 1) {
			return true;
		} else {
			return false;
		}
	}

	public GameElementView[][] getGameArea() {
		return gameArea;
	}

	public void setGameArea(GameElementView[][] gameArea) {
		this.gameArea = gameArea;
	}

	public void setPawnPosition(Position pawnPosition) {
		this.pawnPosition = pawnPosition;
	}

	public Position getPawnPosition() {
		return this.pawnPosition;
	}

}
