package com.congklak.ui;

import com.congklak.core.Computer;
import com.congklak.core.EasyComputer;
import com.congklak.core.ExpertComputer;
import com.congklak.core.HardComputer;
import com.congklak.core.Hint;
import com.congklak.core.MediumComputer;
import com.congklak.core.Player;

public class PlayerMenu {
	private Player player;
	public String label;
	private MainMenu mainMenu = null;
	
	public PlayerMenu(MainMenu mainMenu, String label) {
		this.mainMenu = mainMenu;
		this.label = label;
		this.player = null;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void createPlayer() {
		String name = "";
		do {
			System.out.print(label + " name: ");
			name = mainMenu.scan.nextLine();
		} while(name.length() == 0 || name.length() > 10);
		player = new Player(name);
	}
	
	public void createComputer(int level) {
		String name = Computer.generateName();
		switch (level) {
			case GameMenu.EASY:
				player = new EasyComputer(name);
				break;
			case GameMenu.MEDIUM:
				player = new MediumComputer(name);
				break;
			case GameMenu.HARD:
				player = new HardComputer(name);
				break;
			case GameMenu.EXPERT:
				player = new ExpertComputer(name);
				break;
		}
	}
	
	public int inputHole() {
		int hole = 0;
		do {
			System.out.println(player.getName() + " turn");
			if (player.getName().equals("BINUS")) { // hidden feature
				Hint c = new Hint("");
				System.out.println("Hint: " + c.getHint(player.getOpponent(), player));
			}
			System.out.print("Choose hole(1-7): ");
			if (mainMenu.scan.hasNextInt()) {
				hole = mainMenu.scan.nextInt();
			}
			if (mainMenu.scan.hasNextLine()) {
				mainMenu.scan.nextLine();
			}
		} while(hole < 1 || hole > 7 || player.getValueHole(hole - 1) == 0);
		return hole;
	}
	
	public void printDraw() {
		System.out.println("Draw");
		System.out.print("Press enter to continue");
		mainMenu.scan.nextLine();
	}
	
	public void printWin() {
		System.out.println(player.getName() + " win");
		System.out.print("Press enter to continue");
		mainMenu.scan.nextLine();
	}; 
}
