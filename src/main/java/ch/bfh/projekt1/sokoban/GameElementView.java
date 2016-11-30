package ch.bfh.projekt1.sokoban;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 *@author:Elisa
 */
public class GameElementView extends JLabel {
	static ImageIcon imagePawn;
	static ImageIcon imageFloor;
	static ImageIcon imageBox;
	static ImageIcon imageStorage;
	static ImageIcon imageWall;

	GameElementType type;
	private Position position;

	public static GameElementView create(GameElementType type, MouseListener ml) {
		return create(type, ml, 0, 0);
	}

	public static GameElementView create(GameElementType type,
			MouseListener ml, int x, int y) {
		loadImages();
		GameElementView gameElementView = new GameElementView();
		gameElementView.setType(type);
		gameElementView.setBounds(ProblemDesigner.SPACE, 0,
				GameElementUtile.WIDTH, GameElementUtile.WIDTH);
		gameElementView.addMouseListener(ml);
		gameElementView.position = new Position(x, y);
		return gameElementView;
	}

	public void changeType(GameElementType type) {
		setType(type);
	}

	private void setIcon() {
		ImageIcon image = null;
		switch (type) {
		case FLOOR:
			image = imageFloor;
			break;
		case PAWN:
		case PAWN_ON_STORAGE:
			image = imagePawn;
			break;
		case BOX:
		case BOX_ON_STORAGE:
			image = imageBox;
			break;
		case WALL:
			image = imageWall;
			break;
		case STORAGE:
			image = imageStorage;
			break;
		}

		image = new ImageIcon(image.getImage().getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		setIcon(image);
	}

	private static void loadImages() {
		imageFloor = new ImageIcon(Floor.loadImage());
		imagePawn = new ImageIcon(Pawn.loadImage());
		imageBox = new ImageIcon(Box.loadImage());
		imageWall = new ImageIcon(Wall.loadImage());
		imageStorage = new ImageIcon(Storage.loadImage());
	}

	public GameElementType getType() {
		return type;
	}

	public void setType(GameElementType type) {
		this.type = type;
		setIcon();
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
