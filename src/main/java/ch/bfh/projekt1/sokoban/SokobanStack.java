package ch.bfh.projekt1.sokoban;

import java.util.ArrayList;
import java.util.List;

/*
 *@author:Elisa, Anna
 */
public class SokobanStack {
	private List<SokobanStackTuple> stack;

	public SokobanStack() {
		stack = new ArrayList<SokobanStackTuple>();
	}

	public void push(SokobanStackTuple tuple) {
		stack.add(tuple);
	}

	public SokobanStackTuple pop() {
		if (isValidPointer()) {
			return stack.remove(stack.size() - 1);
		}
		return null;
	}

	public boolean isValidPointer() {
		return stack != null && !stack.isEmpty();
	}
}
