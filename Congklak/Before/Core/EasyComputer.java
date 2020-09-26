package com.congklak.core;

import java.util.ArrayList;
import java.util.Collections;

public class EasyComputer extends Computer {
	
	public EasyComputer(String name) {
		super(name);
	}
	
	public void combination(GameState state) {
		this.solutions.clear();
		this.combination(state, new ArrayList<>(), 1);
		Collections.sort(this.solutions);
	}
}
