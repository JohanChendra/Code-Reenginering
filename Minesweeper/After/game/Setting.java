package game;

import java.util.Random;

public class Setting {
	private int width;
	private int height;
	private int mineTotal;
	
	public Setting(int width, int height, int mineTotal) {
		super();
		this.width = width;
		this.height = height;
		this.mineTotal = mineTotal;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMineTotal() {
		return mineTotal;
	}
	
	public void fillBoardWithNotOpenObject(int h, int w, Game game) {
		game.board = new GameObject[h][w];
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				game.board[i][j] = new GameObject(game, "not_open");
			}
		}
	}
	public void placeMines(Setting gameSetting, int h, int w,Game game) {
		int placed = 0;
		Random random = new Random();
		while(placed < gameSetting.getMineTotal()) {
			int y = random.nextInt(h);
			int x = random.nextInt(w);
			
			if(game.board[y][x].getType().equalsIgnoreCase("mine")) {
				continue;
			}
			
			game.board[y][x] = new GameObject(game, "mine");
			++placed;
		}
	}
}
