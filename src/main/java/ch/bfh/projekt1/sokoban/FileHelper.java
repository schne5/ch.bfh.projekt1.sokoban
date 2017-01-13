package ch.bfh.projekt1.sokoban;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHelper {
	public static File openFileChooser(JFrame parent, String path) {
		JFileChooser chooser = new JFileChooser(path);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt",
				"txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String selected = chooser.getSelectedFile().getAbsolutePath();
			if (selected.substring(selected.lastIndexOf('.')).equals(".txt")) {
				return chooser.getSelectedFile();
			} else {
				JOptionPane.showMessageDialog(chooser,
						"Datei muss auf .txt enden!");
			}
		}
		return null;
	}

	public static File openFileSaver(JFrame parent, String path) {
		JFileChooser chooser = new JFileChooser(path);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt",
				"txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = new File(chooser.getSelectedFile().getAbsolutePath()
					+ ".txt");
			return file;
		}
		return null;
	}

}
