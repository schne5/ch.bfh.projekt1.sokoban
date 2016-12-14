package ch.bfh.projekt1.sokoban;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 *@author:Elisa
 */
public class GameElementView extends JLabel {
	static ImageIcon imagePawn;
	static ImageIcon imagePawnOnStorage;
	static ImageIcon imageFloor;
	static ImageIcon imageBox;
	static ImageIcon imageBoxOnStorage;
	static ImageIcon imageStorage;
	static ImageIcon imageWall;

	GameElementType type;
	private Position position;
	private boolean design;

	public static GameElementView create(GameElementType type,
			MouseListener ml, boolean design) {
		return create(type, ml, 0, 0, design);
	}

	public GameElementView(boolean design) {
		this.design = design;
	}

	public static GameElementView create(GameElementType type,
			MouseListener ml, int x, int y, boolean design) {
		if (design) {
			loadImagesDesign();
		} else {
			loadImages();
		}
		GameElementView gameElementView = new GameElementView(design);
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
			image = imagePawn;
			break;
		case PAWN_ON_STORAGE:
			if (design) {
				image = imagePawnOnStorage;
			} else {
				image = imagePawn;
			}
			break;
		case BOX:
			image = imageBox;
			break;
		case BOX_ON_STORAGE:
			if (design) {
				image = imageBoxOnStorage;
			} else {
				image = imageBox;
			}
			break;
		case WALL:
			image = imageWall;
			break;
		case STORAGE:
			image = imageStorage;
			break;
		}

		image = new ImageIcon(image.getImage());
		setIcon(image);
	}

	private static void loadImages() {
		imageFloor = new ImageIcon(Floor.loadImage(false).getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		imagePawn = new ImageIcon(Pawn.loadImage().getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		imageBox = new ImageIcon(Box.loadImage().getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		imageWall = new ImageIcon(Wall.loadImage().getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		imageStorage = new ImageIcon(Storage.loadImage().getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
	}

	private static void loadImagesDesign() {
		loadImages();
		imageFloor = new ImageIcon(Floor.loadImage(true).getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		imagePawnOnStorage = new ImageIcon(Pawn.loadImageOnStorage()
				.getScaledInstance(GameElementUtile.WIDTH,
						GameElementUtile.WIDTH, java.awt.Image.SCALE_SMOOTH));
		imageBoxOnStorage = new ImageIcon(Box.loadImageOnStorage()
				.getScaledInstance(GameElementUtile.WIDTH,
						GameElementUtile.WIDTH, java.awt.Image.SCALE_SMOOTH));
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

	public boolean isDesign() {
		return design;
	}

	public void setDesign(boolean design) {
		this.design = design;
	}
}
