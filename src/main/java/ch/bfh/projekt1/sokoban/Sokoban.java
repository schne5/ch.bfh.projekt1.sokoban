package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Sokoban extends JFrame{
	
	public Sokoban(){
		getContentPane().add(new Warehouse(),BorderLayout.CENTER);
		JButton cancel = new JButton("Cancel");
		cancel.setSize(40, 20);
		getContentPane().add(cancel,BorderLayout.SOUTH);
	}

}
