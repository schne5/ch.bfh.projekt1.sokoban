package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Sokoban extends JFrame{
	
	public Sokoban(){
		getContentPane().add(new Warehouse(),BorderLayout.CENTER);
	}

}
