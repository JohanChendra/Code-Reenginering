package com.congklak.ui;

public class HowToPlayMenu implements Printable {
	
	private MainMenu mainMenu = null;
	
	public HowToPlayMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
		print();
	}
	
	public void print() {
		mainMenu.printLine();
		System.out.println("How to Play\n" + 
				"-----------\n" +
				"Every player have 7 small holes and 1 big hole, every small hole have 7 units\n" + 
				"Every turn, the Player must choose the hole to take the units. After that, the player must distribute to other holes by order and skip the opponent big hole\n" + 
				"When the last unit drops to a filled small hole, then the player must take from the hole and distribute to other holes by order again\n" + 
				"When the last unit drops to a big hole, then the player can choose the hole to the units again.\n" + 
				"\n" + 
				"When the last unit drops to the empty small hole, then the player must change turn.\n" + 
				"When the last unit drops to the empty small hole on that's the player side, then the player takes the last unit and the unit from opponent small hole side to that's the player big hole.\n");
		mainMenu.scan.nextLine();
	}
}
