package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProblemDesigner extends JFrame {
	public static int SPACE = 30;
	ArrayList<JLabel> elements = new ArrayList<JLabel>();
	MouseListener mouseListener;
	ProblemDesignArea problemDesignArea;
	JPanel gameElementPanel;
	JButton ok;
	JButton cancel;
	JButton clear;
	JPanel buttonPanel;

	public ProblemDesigner() {
		initMouseListener();
		gameElementPanel = new JPanel();
		addGameElementViews();
		problemDesignArea = new ProblemDesignArea();
		initButtons();
		add(gameElementPanel, BorderLayout.NORTH);
		add(problemDesignArea, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void addGameElementViews() {
		gameElementPanel.add(GameElementView.create(GameElementType.PAWN,
				mouseListener));
		gameElementPanel.add(GameElementView.create(GameElementType.BOX,
				mouseListener));
		gameElementPanel.add(GameElementView.create(GameElementType.WALL,
				mouseListener));
		gameElementPanel.add(GameElementView.create(GameElementType.STORAGE,
				mouseListener));
		gameElementPanel.add(GameElementView.create(GameElementType.FLOOR,
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
			// TODO Clear
		});
		buttonPanel.add(ok);
		buttonPanel.add(clear);
		buttonPanel.add(cancel);
	}

	private void initMouseListener() {
		mouseListener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

				GameElementView gameElementView = (GameElementView) e
						.getSource();
				problemDesignArea.setActiveType(gameElementView.getType());
				System.out.println("Mouselistener ProblemDesigner :"
						+ problemDesignArea.getActiveType());
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
	}
}
