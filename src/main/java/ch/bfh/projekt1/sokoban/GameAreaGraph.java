package ch.bfh.projekt1.sokoban;

import java.util.List;

public class GameAreaGraph {
	private GraphTuple[][] area;
	private Position pawnPosition;

	public static char PAWN = '@';
	public static char WALL = '#';
	public static char BOX = '$';
	public static char STORAGE = '.';
	public static char FLOOR = ' ';
	public static char BOX_ON_STORAGE = '*';
	public static char PAWN_ON_STORAGE = '+';

	public void createGraph(GraphTuple[][] area, List<String> lines, int width) {
		int x = 0;
		int y = 0;

		for (String line : lines) {
			char[] charsLine = line.toCharArray();

			for (char c : charsLine) {
				if (c == WALL) {
					area[x][y] = new GraphTuple(GameElementType.WALL);
				} else if (c == FLOOR) {
					area[x][y] = new GraphTuple(GameElementType.FLOOR);
				} else if (c == STORAGE) {
					area[x][y] = new GraphTuple(GameElementType.STORAGE);
				} else if (c == BOX) {
					area[x][y] = new GraphTuple(GameElementType.BOX);
				} else if (c == PAWN) {
					area[x][y] = new GraphTuple(GameElementType.PAWN);
					setPawnPosition(new Position(x, y));
				} else if (c == PAWN_ON_STORAGE) {
					area[x][y] = new GraphTuple(GameElementType.PAWN_ON_STORAGE);
					setPawnPosition(new Position(x, y));
				} else if (c == BOX_ON_STORAGE) {
					area[x][y] = new GraphTuple(GameElementType.BOX_ON_STORAGE);
				}
				x++;
				if (x >= width) {
					y++;
					x = 0;
				}
			}
		}
		setArea(area);
	}

	public GraphTuple[][] getArea() {
		return area;
	}

	public void setArea(GraphTuple[][] area) {
		this.area = area;
	}

	public Position getPawnPosition() {
		return pawnPosition;
	}

	public void setPawnPosition(Position pawnPosition) {
		this.pawnPosition = pawnPosition;
	}

}
