package com.congklak.core;

import java.util.ArrayList;
import java.util.Collections;

public class ExpertComputer extends Computer {
	
	public ExpertComputer(String name) {
		super(name);
	}

	public void combination(GameState state) {
		this.solutions.clear();
		this.combination(state, new ArrayList<>(), 4);
		Collections.sort(this.solutions);
	}
}
