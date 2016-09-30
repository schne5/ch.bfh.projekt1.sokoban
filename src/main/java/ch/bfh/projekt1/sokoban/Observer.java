package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Observer {
	public static final int WIDTH =30;
	public static final String PROBLEM_PATH="sokobanProblems/./";
	public static final String DEFAULT_PROBLEM="problem1.txt";
	
	private String fileName;
	private String path;
	private List<GameElement> gameElements;
	private List<Wall> walls;
	private List<Box> boxes;
	private List<Storage> storages;
	private Pawn pawn;
	
	public Observer() {
		gameElements = new ArrayList<GameElement>();
		walls = new ArrayList<Wall>();
		boxes = new ArrayList<Box>();
		storages = new ArrayList<Storage>();
		path = PROBLEM_PATH;
		fileName = DEFAULT_PROBLEM;
	}
	
	public void move(int newPosX, int newPosY){
		
	}
	
	public boolean checkRules(){
		return false;
	}
	
	public List<GameElement> initWarehouse(File file){
		List<String> lines;
		int posX=30;
		int posY=30;
		try {
			lines = Files.readAllLines(Paths.get(path+fileName));
			
			for(String line : lines){
				char [] charsLine = line.toCharArray();
				
				for(char c :charsLine){
					if(c == '#'){
						Wall wall =new Wall(posX, posY);
						walls.add(wall);	
					}else if(c==' '){
						
					}else if(c=='.'){
						Storage storage = new Storage(posX, posY);
						storages.add(storage);
					}
					else if(c=='§'){
						Box box = new Box(posX, posY);
						boxes.add(box);
					}
					else if(c=='@'){
						pawn = new Pawn(posX, posY);
					}
					posX +=WIDTH;
				}
				posX =WIDTH;
				posY +=WIDTH;		
			}
			gameElements.addAll(walls);
			gameElements.addAll(boxes);
			gameElements.addAll(storages);
			gameElements.add(pawn);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gameElements;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

}
