package com.congklak.core;

import java.util.ArrayList;
import java.util.Collections;

public class HardComputer extends Computer {
	
	public HardComputer(String name) {
		super(name);
	}

	public void combination(GameState state) {
		this.solutions.clear();
		this.combination(state, new ArrayList<>(), 3);
		Collections.sort(this.solutions);
	}
}
