package ch.bfh.projekt1.sokoban;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/*
 *@author:Elisa
 */
public class ProblemDesignArea extends JPanel implements MouseListener {
	public static final int WIDTH = 20;
	public static final int HEIGHT = 16;

	List<GameElementView> area;
	GridLayout layout;
	GameElementType activeType;

	public ProblemDesignArea() {
		area = new ArrayList<GameElementView>();
		layout = new GridLayout(HEIGHT, WIDTH);
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
		drawArea();
	}

	public void drawArea() {

		for (int i = 0; i < HEIGHT * WIDTH; i++) {
			GameElementView v = GameElementView.create(GameElementType.FLOOR,
					null);
			add(v);
			area.add(v);
			v.addMouseListener(this);
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

	public GameElement[][] prepareSave() {
		GameElement[][] gameElements = new GameElement[HEIGHT][WIDTH];
		int i = 0;
		int j = 0;
		for (GameElementView gameElementView : area) {
			gameElements[i][j] = gameElementView.getGameElement();
			j++;
			if (j >= WIDTH) {
				i++;
				j = 0;
			}
		}
		return gameElements;
	}
}
