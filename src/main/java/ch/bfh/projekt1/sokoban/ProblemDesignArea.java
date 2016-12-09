package ch.bfh.projekt1.sokoban;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/*
 *@author:Elisa
 */
public class ProblemDesignArea extends JPanel implements MouseListener {
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
		drawArea();
	}

	public void drawArea() {
		for (int i = 0; i < gameArea[0].length; i++) {
			for (int j = 0; j < gameArea.length; j++) {
				GameElementView v = GameElementView.create(
						GameElementType.FLOOR, null, true);
				add(v);
				gameArea[j][i] = v;
				v.addMouseListener(this);

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
		// TODO Auto-generated method stub

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
				GameElementType type = gameArea[x][y].getType();
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
