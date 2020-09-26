package com.congklak.core;

public class GameState implements Cloneable{
	public Player player;
	public Player computer;
	
	public GameState(Player player, Player computer) {
		this.player = player;
		this.computer = computer;
		this.player.setOpponent(computer);
		this.computer.setOpponent(player);
	}
	
	public GameState clone() {
		return new GameState(player.clone(), computer.clone());
	}
}
