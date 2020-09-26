package com.congklak.ui;

import java.util.Random;

import com.congklak.core.Computer;
import com.congklak.core.EasyComputer;
import com.congklak.core.ExpertComputer;
import com.congklak.core.GameState;
import com.congklak.core.HardComputer;
import com.congklak.core.MediumComputer;
import com.congklak.core.Player;

public class GameMenu {
	
	private PlayerMenu player1 = null;
	private PlayerMenu player2 = null;
	
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	public static final int EXPERT = 4;
	
	public static final int BIG_HOLE = 8;
	
	private int turn = 1;
	
	public static Random rand = new Random();
	
	private Mode mode = null;
	private int level = 0;
	private MainMenu mainMenu = null;
	
	public GameMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
		mode = Mode.PLAYER_VS_PLAYER;
		// player 1
		player1 = new PlayerMenu(mainMenu, "Player 1");
		player1.createPlayer();
		
		// player 2
		player2 = new PlayerMenu(mainMenu, "Player 2");
		player2.createPlayer();
		
		player1.getPlayer().setOpponent(player2.getPlayer());
		player2.getPlayer().setOpponent(player1.getPlayer());
		play();
	}
	
	public GameMenu(MainMenu mainMenu, int level) {
		this.mainMenu = mainMenu;
		mode = Mode.PLAYER_VS_COMPUTER;
		this.level = level;
		// player
		player1 = new PlayerMenu(mainMenu, "Player");
		player1.createPlayer();
		
		// computer
		player2 = new PlayerMenu(mainMenu, "Computer");
		player2.createComputer(level);
		
		player1.getPlayer().setOpponent(player2.getPlayer());
		player2.getPlayer().setOpponent(player1.getPlayer());
		play();
	}

	private void play() {
		while (true) {
			int hole = -1;
			String player1label = "Player 1", player2label = "Player 2";
			if (mode == Mode.PLAYER_VS_COMPUTER) {
				player1label = "Player";
				player2label = "Computer";
			}
			PlayerMenu currentPlayerMenu = null;
			String label = ""; 
			if (turn % 2 == 1) {
				currentPlayerMenu = player1;
				label = player1label;
			} else {
				currentPlayerMenu = player2;
				label = player2label;
			}
			
			Player currentPlayer = currentPlayerMenu.getPlayer();
			
			if (currentPlayer.hasMove() == false) {
				++turn;
				printBoard();
				System.out.println(currentPlayer.getName() + " has't move");
				System.out.print("Press enter to continue");
				mainMenu.scan.nextLine();
				continue;
			}
			
			printBoard();
			if (label.contains("Player")) { // is player
				hole = currentPlayerMenu.inputHole();
			} else { // is computer
				Computer comp = (Computer) player2.getPlayer();
				if (comp.pick.isEmpty()) {
					GameState state = new GameState(player1.getPlayer().clone(), player2.getPlayer().clone());
					double ratio = 0;
					switch (level) {
						case EASY:
							((EasyComputer) comp).combination(state);
							ratio = 0.70;
							break;
						case MEDIUM:
							((MediumComputer) comp).combination(state);
							ratio = 0.50;
							break;
						case HARD:
							((HardComputer) comp).combination(state);
							ratio = 0.30;
							break;
						case EXPERT:
							((ExpertComputer) comp).combination(state);
							ratio = 0.10;
							break;
					}
					int bound = (int) Math.ceil(comp.solutions.size() * ratio);
					for (Integer pick : comp.solutions.get(rand.nextInt(bound)).picks) {
						comp.pick.add(pick);
					}
				}
				hole = comp.pick.remove();
				System.out.println("Computer choose: " + hole);
				System.out.print("Press enter to continue");
				mainMenu.scan.nextLine();
			}
			
			int take = 0;
			int currentIndex = hole;
			take = currentPlayer.getValueHole(hole - 1);
			currentPlayer.setValueHole(hole - 1, 0);
			while (take > 0) {
				++currentIndex;
				// skip opponent big hole
				if ((turn % 2 == 1 && currentPlayer == player2.getPlayer() && currentIndex == BIG_HOLE) || (turn % 2 == 0 && currentPlayer == player1.getPlayer() && currentIndex == BIG_HOLE)) {
					++currentIndex;
					continue;
				}
				// change side
				if (currentIndex > BIG_HOLE) {
					currentPlayer = currentPlayer.getOpponent();
					currentIndex = 0;
					continue;
				}
				// drop to hole
				if (currentIndex == BIG_HOLE) {
					currentPlayer.setBig(currentPlayer.getBig() + 1);
				} else if (currentIndex < BIG_HOLE) {
					currentPlayer.setValueHole(currentIndex - 1, currentPlayer.getValueHole(currentIndex - 1) + 1);
				}
				--take;
				// take from last hole
				if (take == 0 && currentIndex < BIG_HOLE && currentPlayer.getValueHole(currentIndex - 1) > 1) {
					take = currentPlayer.getValueHole(currentIndex - 1);
					currentPlayer.setValueHole(currentIndex - 1, 0);
				}
				
				printBoard();
				System.out.println("On hand: " + take);
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// steal from opponent
			if (currentIndex < BIG_HOLE && ((turn % 2 == 1 && currentPlayer == player1.getPlayer()) || (turn % 2 == 0 && currentPlayer == player2.getPlayer()))) {
				int opponentIndex = 7 - currentIndex;
				Player opponentPlayer = currentPlayer.getOpponent();
				int takeOpponent = opponentPlayer.getValueHole(opponentIndex);
				opponentPlayer.setValueHole(opponentIndex, 0);
				currentPlayer.setValueHole(currentIndex - 1, 0);
				currentPlayer.setBig(currentPlayer.getBig() + takeOpponent + 1);
				printBoard();
				System.out.println(currentPlayer.getName() + " steal: " + takeOpponent);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (player1.getPlayer().hasMove() == false && player2.getPlayer().hasMove() == false) {
				break;
			}
			
			if (currentIndex != BIG_HOLE) {
				++turn;
				printBoard();
				System.out.println("Change turns");
				System.out.print("Press enter to continue");
				mainMenu.scan.nextLine();
			}
		}
		
		printBoard();
		if(player1.getPlayer().getBig() == player2.getPlayer().getBig()) {
			player1.printDraw();
		} else if(player1.getPlayer().getBig() > player2.getPlayer().getBig()) {
			player1.printWin();
		} else {
			player2.printWin();
		}
	}
	
	private void printBoard() {
		String player1label = "Player 1", player2label = "Player 2";
		if (mode == Mode.PLAYER_VS_COMPUTER) {
			player1label = "Player";
			player2label = "Computer";
		}
		mainMenu.printLine();

		System.out.println("               7  6  5  4  3  2  1");
		System.out.println("             +--+--+--+--+--+--+--+");
		System.out.printf( " %-10s  |%2d|%2d|%2d|%2d|%2d|%2d|%2d|\n", player2.getPlayer().getName(), player2.getPlayer().getValueHole(6), player2.getPlayer().getValueHole(5), player2.getPlayer().getValueHole(4), player2.getPlayer().getValueHole(3), player2.getPlayer().getValueHole(2), player2.getPlayer().getValueHole(1), player2.getPlayer().getValueHole(0));
		System.out.println("          +--+--+--+--+--+--+--+--+--+");
		System.out.printf( " %-8s |%2d|                    |%2d| %8s\n", player2label, player2.getPlayer().getBig(), player1.getPlayer().getBig(), player1label);
		System.out.println("          +--+--+--+--+--+--+--+--+--+");
		System.out.printf( "             |%2d|%2d|%2d|%2d|%2d|%2d|%2d|  %10s\n", player1.getPlayer().getValueHole(0), player1.getPlayer().getValueHole(1), player1.getPlayer().getValueHole(2), player1.getPlayer().getValueHole(3), player1.getPlayer().getValueHole(4), player1.getPlayer().getValueHole(5), player1.getPlayer().getValueHole(6), player1.getPlayer().getName());
		System.out.println("             +--+--+--+--+--+--+--+");
		System.out.println("               1  2  3  4  5  6  7");
	}
	
	
}
