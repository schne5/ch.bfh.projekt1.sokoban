package ch.bfh.projekt1.sokoban;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

public class GameElementView extends JLabel {

	public enum Type {
		PAWN, BOX, STORAGE, WALL, FLOOR
	}

	private GameElement gameElement;

	public GameElementView(GameElement gameElement, MouseListener ml) {
		this.gameElement = gameElement;
		setIcon();
		setBounds(ProblemDesigner.SPACE, 0, GameElementUtile.WIDTH,
				GameElementUtile.WIDTH);
		addMouseListener(ml);
		setTransferHandler(new TransferHandler("icon"));
	}

	public GameElementView() {
		// TODO Auto-generated constructor stub
	}

	public GameElement getGameElement() {
		return gameElement;
	}

	public void setGameElement(GameElement gameElement) {
		this.gameElement = gameElement;
	}

	public void setIcon() {
		ImageIcon imageIcon = new ImageIcon(gameElement.getImagePath());
		imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		setIcon(imageIcon);
	}

	public static GameElementView create(Type type, MouseListener ml) {
		GameElementView gmv = new GameElementView();
		switch (type) {
		case PAWN:
			gmv.setGameElement(new Pawn());
			break;
		case BOX:
			gmv.setGameElement(new Box());
			break;
		case WALL:
			gmv.setGameElement(new Wall());
			break;
		case STORAGE:
			gmv.setGameElement(new Storage());
			break;
		case FLOOR:
			gmv.setGameElement(new Floor());
			break;
		}
		gmv.setIcon();
		gmv.setBounds(ProblemDesigner.SPACE, 0, GameElementUtile.WIDTH,
				GameElementUtile.WIDTH);
		gmv.addMouseListener(ml);
		gmv.setTransferHandler(new TransferHandler("icon"));
		return gmv;

	}

}
