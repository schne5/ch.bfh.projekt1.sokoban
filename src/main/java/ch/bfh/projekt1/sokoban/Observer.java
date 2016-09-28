package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Observer {
	private String fileName;
	private String path;
	
	public Observer() {
		path = "../";
		fileName = "problem1.txt";
	}
	
	public void move(int newPosX, int newPosY){
		
	}
	
	public boolean checkRules(){
		return false;
	}
	
	public List<GameElement> initWarehouse(File file){
		try (Stream<String> stream = Files.lines(Paths.get(path+fileName))) {

			stream.forEach(t -> {
				char [] line = t.toCharArray();
				for(char c :line){
					if(c == '#'){
						new Wall();
					}
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<GameElement>();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

}
