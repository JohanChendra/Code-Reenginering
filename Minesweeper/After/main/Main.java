package main;

import java.io.IOException;
import java.util.Scanner;
import game.*;
import player.Player;
import player.PlayerRepo;

public class Main {

	public static void main(String[] args) throws IOException {
		new Main(new PlayerRepo());
	}
	
	public Main(PlayerRepo playerRepo) {
		Player p = playerRepo.loadOrNewPrompt();
		startGame(p, playerRepo);
	}

	private Scanner scan = new Scanner(System.in);
	private void startGame(Player p, PlayerRepo playerRepo) {		
		cls();
		showPlayerProfile(p);
		showLevelAndExit();
		
		Game game = null;
		game = chooseLevel(p, game);
		
		p.howToPlay();
		scan.nextLine();
		
		game.play();
		try {
			playerRepo.save();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		startGame(p, playerRepo);
	}

	private Game chooseLevel(Player p, Game game) {
		do {
			System.out.print("level [1-2]: ");
			String input = scan.nextLine();
			if(input.equalsIgnoreCase("exit")) {
				System.exit(0);;
			}

			try {
				int level = Integer.parseInt(input);
				if(level < 1 || level > 2) continue;
				if(level == 1) {
					game = new Easy(p);
					break;
				}
				if(level == 2) {
					game = new Hard(p);
					break;
				}
			} catch (Exception e) {
				continue;
			}
		}while(true);
		return game;
	}

	private void showLevelAndExit() {
		System.out.println("Choose level: ");
		System.out.println("1. Easy (7 x 5, 5 mines)");
		System.out.println("2. Hard (12 x 9, 15 mines)");		
		System.out.println("");		
		System.out.println("Type 'exit' to exit from the game");
	}

	private void showPlayerProfile(Player p) {
		System.out.println("Welcome, " + p.getName());
		System.out.println("Your current record: ");
		System.out.println("Easy: Win: "+ p.getEasySuccess() + " | Lose: " + p.getEasyFailed());
		System.out.println("Hard: Win: "+ p.getHardSuccess() + " | Lose: " + p.getHardFailed());
		System.out.println("");
	}

	public static void cls() {
		for(int i = 0; i < 26; i++) System.out.println("");
	}
}