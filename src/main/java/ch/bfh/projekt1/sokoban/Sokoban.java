package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class Sokoban extends JFrame {
	public static String UNDO = "Undo";
	public static String SAVE = "Save";
	public static String LOAD = "Load";

	JPanel buttonPanel;
	JButton undo;
	JButton save;
	JButton load;
	Warehouse warehouse;
	String name;

	public Sokoban() {
		warehouse = new Warehouse();
		setFocusOnWarehouse();
		buttonPanel = new JPanel();
		undo = new JButton();
		undo.setText(UNDO);
		undo.addActionListener(t -> {
			warehouse.getController().undo();
			warehouse.repaint();
			setFocusOnWarehouse();
		});

		save = new JButton();
		save.setText(SAVE);
		save.addActionListener(t -> {
			String name = getUsername();
			if (!"".equals(name) && null != name) {
				warehouse.getController().save(name);
				setFocusOnWarehouse();
			}
		});

		load = new JButton();
		load.setText(LOAD);
		load.addActionListener(t -> {
			String name = getUsername();
			if (!"".equals(name) && null != name) {
				Model model = warehouse.getController().load(name);
				if (model != null) {
					warehouse.setModel(model);
				} else {
					JOptionPane.showMessageDialog(this,
							"Kein Spiel zu Benutzernamen: \"" + name
									+ "\" gefunden.");
				}
				warehouse.repaint();
			}
			setFocusOnWarehouse();
		});
		buttonPanel.add(undo);
		buttonPanel.add(save);
		buttonPanel.add(load);
		getContentPane().add(warehouse, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	private void setFocusOnWarehouse() {
		warehouse.setFocusable(true);
		warehouse.requestFocusInWindow();
	}

	public String getUsername() {
		return JOptionPane.showInputDialog(this, "Username eingeben",
				JOptionPane.OK_OPTION).trim();
	}
}
