package com.congklak.ui;

import com.congklak.core.Computer;
import com.congklak.core.Hint;
import com.congklak.core.Player;

public class PlayerMenu {
	private Player player;
	private String label;
	private MainMenu mainMenu = null;
	
	public PlayerMenu(MainMenu mainMenu, String label) {
		this.mainMenu = mainMenu;
		this.label = label;
		this.player = null;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setLabel(String label) {
		this.label = label;
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
	
	public void createComputer() {
		String name = Computer.generateName();
			player = new Computer(name);		
	}
	
	public int inputHole() {
		int hole = 0;
		do {
			System.out.println(player.getName() + " turn");
			if (isHiddenFeature()) {
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

	private boolean isHiddenFeature() {
		return player.getName().equals("BINUS");
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
	} 
}
