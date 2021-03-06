package game;

import player.Player;

public class Easy extends Game {
	private Player p;

	public Easy(Player p) {
		super(new Setting(7, 5, 5));
		this.p = p;
	}

	@Override
	public void generateResult(String Result) {
		String result = Result;
		generateWinOrLose(result);	
		showResult(result);
	}
	private void generateWinOrLose(String result){
		if(result.equals("Win")){
			p.setEasySuccess(p.getEasySuccess() + 1);
		}
		else{
			p.setEasyFailed(p.getEasyFailed() + 1);
		}
		this.hideMine = false;
	}
	private void showResult(String result){
		clear();
		print();
		if(result.equals("Win")){
			System.out.println("You win");
		}
		else{
			System.out.println("You lose");
		}
		scan.nextLine();
	}
}
