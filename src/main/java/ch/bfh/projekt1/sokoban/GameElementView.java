package ch.bfh.projekt1.sokoban;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

/*
 *@author:Elisa
 */
public class GameElementView extends JLabel {

	private GameElement gameElement;
	GameElementType type;

	public GameElementView(GameElement gameElement, MouseListener ml) {
		this.gameElement = gameElement;
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
		gmv.setGameElement(GameElementUtile.getGameElementByType(type));
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
		setGameElement(GameElementUtile.getGameElementByType(type));
	}

	private void setIcon() {
		ImageIcon imageIcon = new ImageIcon(gameElement.getImagePath());
		imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(
				GameElementUtile.WIDTH, GameElementUtile.WIDTH,
				java.awt.Image.SCALE_SMOOTH));
		setIcon(imageIcon);
	}

	public GameElementType getType() {
		return type;
	}

	public void setType(GameElementType type) {
		this.type = type;
	}

	public GameElement getGameElement() {
		return gameElement;
	}

	private void setGameElement(GameElement gameElement) {
		this.gameElement = gameElement;
		setIcon();
	}
}
