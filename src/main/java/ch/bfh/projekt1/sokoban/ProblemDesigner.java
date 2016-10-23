package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class ProblemDesigner extends JFrame {
	public static int SPACE = 30;
	ArrayList<JLabel> elements = new ArrayList<JLabel>();
	MouseListener mouseListener;
	ProblemDesignArea gameArea;
	JPanel gameElementPanel;
	JButton ok;
	JButton cancel;
	JButton clear;
	JPanel buttonPanel;

	public ProblemDesigner() {
		newMouseListener();
		gameElementPanel = new JPanel();
		addGameElementViews();
		gameArea = new ProblemDesignArea();
		initButtons();
		add(gameElementPanel, BorderLayout.NORTH);
		add(gameArea, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		repaint();
	}

	private void addGameElementViews() {
		gameElementPanel.add(GameElementView.create(GameElementView.Type.PAWN,
				mouseListener));
		gameElementPanel.add(GameElementView.create(GameElementView.Type.BOX,
				mouseListener));
		gameElementPanel.add(GameElementView.create(GameElementView.Type.WALL,
				mouseListener));
		gameElementPanel.add(GameElementView.create(
				GameElementView.Type.STORAGE, mouseListener));
		gameElementPanel.add(GameElementView.create(GameElementView.Type.FLOOR,
				mouseListener));
	}

	private void initButtons() {
		buttonPanel = new JPanel();
		ok = new JButton("OK");
		ok.addActionListener(a -> {
			// Problem abspeichern
			this.dispose();
			MainSokoban.showInitDialog();
		});
		cancel = new JButton("Abbrechen");
		cancel.addActionListener(a -> {
			// Abbrechen und zur startseite
			this.dispose();
			MainSokoban.showInitDialog();
		});
		clear = new JButton("Clear");
		clear.addActionListener(a -> {
			// Feld clearen und auf anfang initialisieren
			gameArea.drawArea();
		});
		buttonPanel.add(ok);
		buttonPanel.add(clear);
		buttonPanel.add(cancel);
	}

	private void newMouseListener() {
		mouseListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				JComponent jc = (JComponent) e.getSource();
				TransferHandler th = jc.getTransferHandler();
				th.exportAsDrag(jc, e, TransferHandler.COPY);
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

		};
	}
}
