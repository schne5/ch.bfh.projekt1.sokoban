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
	GameElementView[][] gameArea;
	GridLayout layout;
	GameElementType activeType;

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
		GameElementView gameElementView = (GameElementView) e.getSource();
		gameElementView.changeType(getActiveType());
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

		for (int i = 0; i < gameArea[0].length; i++) {
			for (int j = 0; j < gameArea.length; j++) {
				GameElementType type = gameArea[j][i].getType();
				gameElements[j][i] = new GraphTuple(gameArea[j][i].getType());
			}
		}
		return gameElements;
	}
}
