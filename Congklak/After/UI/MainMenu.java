package com.congklak.ui;
import java.util.Scanner;

public class MainMenu {
	public Scanner scan = new Scanner(System.in);
	public MainMenu() {
		int input = -1;
		do {
			showMenu();
			input = chooseMenu();
		} while(input != 7);
	}

	private int chooseMenu() {
		int input;
		try {
			input = scan.nextInt();
			if(scan.hasNextLine()) {
				scan.nextLine();
			}
		} catch (Exception ex) {
			input = -1;
		}
		switch (input) {
			case 1: new GameMenu(this); break;
			case 2: new GameMenu(this, Difficulty.EASY); break;
			case 3: new GameMenu(this, Difficulty.MEDIUM); break;
			case 4: new GameMenu(this, Difficulty.HARD); break;
			case 5: new GameMenu(this, Difficulty.EXPERT); break;
			case 6: howToPlay(); break;
		}
		return input;
	}

	private void showMenu() {
		printLine();
		System.out.println("Menu\n" +
				"----\n" +
				"1. Player vs Player\n" +
				"2. Player vs Computer (Easy)\n" +
				"3. Player vs Computer (Medium)\n" +
				"4. Player vs Computer (Hard)\n" +
				"5. Player vs Computer (Expert)\n" +
				"6. How to Play\n" +
				"7. Exit");
		System.out.print("Choose: ");
	}
	
	private void howToPlay() {
		printLine();
		System.out.println("How to Play\n" + 
				"-----------\n" +
				"Every player have 7 small holes and 1 big hole, every small hole have 7 units\n" + 
				"Every turn, the Player must choose the hole to take the units. After that, the player must distribute to other holes by order and skip the opponent big hole\n" + 
				"When the last unit drops to a filled small hole, then the player must take from the hole and distribute to other holes by order again\n" + 
				"When the last unit drops to a big hole, then the player can choose the hole to the units again.\n" + 
				"\n" + 
				"When the last unit drops to the empty small hole, then the player must change turn.\n" + 
				"When the last unit drops to the empty small hole on that's the player side, then the player takes the last unit and the unit from opponent small hole side to that's the player big hole.\n");
		scan.nextLine();
	}
	
	public void printLine() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}

	public static void main(String[] args) {
		new MainMenu();
	}
}
