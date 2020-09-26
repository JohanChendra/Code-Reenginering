package com.congklak.core;

import java.util.ArrayList;
import java.util.Collections;

public class MediumComputer extends Computer {
	
	public MediumComputer(String name) {
		super(name);
	}

	public void combination(GameState state) {
		this.solutions.clear();
		this.combination(state, new ArrayList<>(), 2);
		Collections.sort(this.solutions);
	}
}
