package com.congklak.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.congklak.ui.GameMenu;

public class Computer extends Player {
	
	public Queue<Integer> pick = null;
	public ArrayList<Solution> solutions = null;
	
	private String computerNames[] = new String[]{
		"Christian",
		"Frans",
		"Thomas",
		"Michael",
		"Valent",
		"Yudy"
	};

	public Computer(String name) {
		super(name);
		pick = new LinkedList<>();
		solutions = new ArrayList<Solution>();
	}
	
	public static String generateName() {
		Computer comp = new Computer("");
		return comp.computerNames[ GameMenu.rand.nextInt(comp.computerNames.length) ];
	}
	
	protected void combination(GameState state, ArrayList<Integer> picks, int maxLevel) {
		if(maxLevel <= 0) {
			addSolution(state, picks);
			return;
		}
		for (int i = 1; i <= 7; i++) {
			if(state.computer.getValueHole(i - 1) > 0) {
				picks.add(i);
				simulateTurn(state.clone(), picks, maxLevel);
				picks.remove(picks.size() - 1);
			}
		}
	}
	
	protected void simulateTurn(GameState state, ArrayList<Integer> picks, int maxLevel) {
		int hole = picks.get(picks.size() - 1);
		Player currentPlayer = state.computer;
		
		int take = 0;
		int currentIndex = hole;
		take = currentPlayer.getValueHole(hole - 1);
		currentPlayer.setValueHole(hole - 1, 0);
		
		while (take > 0) {
			++currentIndex;
			// skip opponent big hole
			if (currentPlayer == state.player && currentIndex == GameMenu.BIG_HOLE) {
				++currentIndex;
				continue;
			}
			// change side
			if (currentIndex > GameMenu.BIG_HOLE) {
				currentPlayer = currentPlayer.getOpponent();
				currentIndex = 0;
				continue;
			}
			// drop to hole
			if (currentIndex == GameMenu.BIG_HOLE) {
				currentPlayer.setBig(currentPlayer.getBig() + 1);
			} else if (currentIndex < GameMenu.BIG_HOLE) {
				currentPlayer.setValueHole(currentIndex - 1, currentPlayer.getValueHole(currentIndex - 1) + 1);
			}
			--take;
			// take from last hole
			if (take == 0 && currentIndex < GameMenu.BIG_HOLE && currentPlayer.getValueHole(currentIndex - 1) > 1) {
				take = currentPlayer.getValueHole(currentIndex - 1);
				currentPlayer.setValueHole(currentIndex - 1, 0);
			}
		}
		
		// take from opponent
		if (currentIndex < GameMenu.BIG_HOLE && currentPlayer == state.computer) {
			int indexOpponent = 7 - currentIndex;
			int takeOpponent = state.player.getValueHole(indexOpponent);
			state.player.setValueHole(indexOpponent, 0);
			state.computer.setValueHole(currentIndex - 1, 0);
			currentPlayer.setBig(currentPlayer.getBig() + takeOpponent + 1);
		}
		
		if (currentIndex == GameMenu.BIG_HOLE) {
			combination(state, picks, maxLevel - 1);
		} else {
			addSolution(state, picks);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addSolution(GameState state, ArrayList<Integer> picks) {
		Solution data = new Solution();
		data.picks = (ArrayList<Integer>) picks.clone();
		data.result = state.computer.getBig();
		solutions.add(data);
	}
}
