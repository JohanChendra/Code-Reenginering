package game;

import java.util.Scanner;

public abstract class Game {
	protected Setting gameSetting;
	boolean hideMine;
	public GameObject[][] board; 
	int h,w;

	public abstract void generateResult(String Result);
	
	public Game(Setting gameSetting) {
		this.gameSetting = gameSetting;
		this.hideMine = true;
		 
		generateHeightAndWeight(gameSetting);
		gameSetting.fillBoardWithNotOpenObject(h, w, this);
		gameSetting.placeMines(gameSetting, h, w, this);
	}

	private void generateHeightAndWeight(Setting gameSetting) {
		h = gameSetting.getHeight();
		w = gameSetting.getWidth();
	}


	
	private boolean placingFlag = false;
	protected Scanner scan = new Scanner(System.in);
	public void play() {
		clear();
		print();
		
		if(placingFlag) {
			System.out.println("currently your command is for placing/unplacing flag. Type 'switch' to start opening squares");
		} else {
			System.out.println("currently your command is for opening square. Type 'switch' to placing flag");
		}
		
		do {
			System.out.print("Input coordinate to command: ");
			String input = scan.nextLine().trim();
			if(input.equalsIgnoreCase("switch")) {
				placingFlag = !placingFlag;
				break;
			}
			if(input.length() != 2) {
				System.out.println("invalid coordinate");
				continue;
			}
			
			char c1 = input.charAt(0);
			char c2 = input.charAt(1);
			int x = c1 - 'A';
			int y = c2 - '1';
			
			generateHeightAndWeight(gameSetting);
			
			if((x < 0 || x >= w) && (y < 0 || y >= y)) {
				System.out.println("invalid coordinate");
				continue;
			}
			
			if(!placingFlag) {
				if(board[y][x].flagged){
					System.out.println("cannot open flagged square");
					continue;
				}
				if(board[y][x].getType().equalsIgnoreCase("mine")) {
					this.generateResult("Lose");
					return;
				}
				
				open(x, y);
				
				if(isWin()) {
					this.generateResult("Win");
					return;
				}
			} else {
				if(board[y][x].getType().equalsIgnoreCase("number")) {
					System.out.println("opened squared cannot be flagged");
					continue;
				}
				board[y][x].flagged = !board[y][x].flagged;
			}
			break;
		}while(true);
		
		play();
	}
	
	private boolean isWin() {
		generateHeightAndWeight(gameSetting);
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				if(this.board[i][j].getType().equalsIgnoreCase("not_open")) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void open(int x, int y) {
		generateHeightAndWeight(gameSetting);
		if(x < 0 || x >= w || y < 0 || y >= h) return; 
		if(!board[y][x].getType().equalsIgnoreCase("not_open")) return;
		
		GameObject newObj = new GameObject(this, "number");
		
		int mineSurrounding = 0;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int x2 = x + j;
				int y2 = y + i;
				if(x2 < 0 || x2 >= w || y2 < 0 || y2 >= h) continue; 
				if(board[y2][x2].getType().equalsIgnoreCase("mine")) {
					++mineSurrounding;
				}
			}
		}

		newObj.setNumber(mineSurrounding);
		board[y][x] = newObj;
		
		if(mineSurrounding == 0) {
			open(x-1, y);
			open(x+1, y);
			open(x, y-1);
			open(x, y+1);
			open(x-1, y-1);
			open(x-1, y+1);
			open(x+1, y-1);
			open(x+1, y+1);
		}
	}
	
	protected void clear() {
		for(int i = 0; i < 26; i++) {
			System.out.println("");
		}
	}
	
	protected void print() {
		generateHeightAndWeight(gameSetting);
		
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				System.out.print(board[i][j].toChar());
			}
			System.out.println(" " + (i + 1));
		}
		for(int i = 0; i < w; i++) {
			System.out.print((char) ('A' + i));
		}
		
		System.out.println("");
		System.out.println("");
	}
}
