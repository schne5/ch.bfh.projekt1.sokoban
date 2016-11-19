package ch.bfh.projekt1.sokoban;

import java.util.ArrayList;
import java.util.Arrays;

public class TransitionTable {

	public static ArrayList<GameElementType> types = new ArrayList<GameElementType>(
			Arrays.asList(GameElementType.FLOOR, GameElementType.STORAGE,
					GameElementType.BOX, GameElementType.BOX_ON_STORAGE,
					GameElementType.PAWN, GameElementType.PAWN_ON_STORAGE));
	private static int move = 0;
	private static int push = 1;

	static GameElementType[][] transition = {
			{ GameElementType.PAWN, GameElementType.PAWN_ON_STORAGE, null,
					null, GameElementType.FLOOR, GameElementType.STORAGE },
			{ GameElementType.BOX, GameElementType.BOX_ON_STORAGE,
					GameElementType.PAWN, GameElementType.PAWN_ON_STORAGE,
					GameElementType.FLOOR, GameElementType.STORAGE } };

	public static GameElementType getTransitionByGameElementType(
			GameElementType oldType, Activity activity) {
		if (activity == Activity.MOVE) {
			return transition[move][types.indexOf(oldType)];

		} else if (activity == Activity.PUSH) {
			return transition[push][types.indexOf(oldType)];
		}
		return null;
	}
}
