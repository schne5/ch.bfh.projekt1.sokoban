package ch.bfh.projekt1.sokoban;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

/*
 *@author:Elisa
 */
public class GameElementView extends JLabel {

	GameElementType type;

	public GameElementView(GameElementType type, MouseListener ml) {
		setType(type);
		setBounds(ProblemDesigner.SPACE, 0, GameElementUtile.WIDTH,
				GameElementUtile.WIDTH);
		addMouseListener(ml);
		setTransferHandler(new TransferHandler("icon"));
	}

	public GameElementView() {
		// TODO Auto-generated constructor stub
	}

	public static GameElementView create(GameElementType type, MouseListener ml) {
		GameElementView gmv = new GameElementView();
		gmv.setType(type);
		gmv.setIcon();
		gmv.setBounds(ProblemDesigner.SPACE, 0, GameElementUtile.WIDTH,
				GameElementUtile.WIDTH);
		gmv.addMouseListener(ml);
		gmv.setTransferHandler(new TransferHandler("icon"));
		return gmv;
	}

	public void changeType(GameElementType type) {
		setType(type);
	}

	private void setIcon() {
		ImageIcon image = null;
		switch (type) {
		case FLOOR:
			image = new ImageIcon(Floor.loadImage());
			break;
		case PAWN:
			image = new ImageIcon(Pawn.loadImage());
			break;
		case BOX:
			image = new ImageIcon(Box.loadImage());
			break;
		case WALL:
			image = new ImageIcon(Wall.loadImage());
			break;
		case STORAGE:
			image = new ImageIcon(Storage.loadImage());
			break;
		}

		image = new ImageIcon(image.getImage().getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		setIcon(image);
	}

	public GameElementType getType() {
		return type;
	}

	public void setType(GameElementType type) {
		this.type = type;
		setIcon();
	}
}
