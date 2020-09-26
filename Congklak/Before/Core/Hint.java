package com.congklak.core;

import java.util.ArrayList;
import java.util.Collections;

public class Hint extends Computer {

	public Hint(String name) {
		super(name);
	}
	
	private void combination(GameState state) {
		this.solutions.clear();
		this.combination(state, new ArrayList<>(), 6);
		Collections.sort(this.solutions);
	}
	
	public int getHint(Player p1, Player p2) {
		GameState state = new GameState(p1.clone(), p2.clone());
		this.combination(state);
		return this.solutions.get(0).picks.get(0);
	}
}
