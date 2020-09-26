package game;

import player.Player;

public class Hard extends Game {
	private Player p;

	public Hard(Player p) {
		super(new Setting(12, 9, 15));
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
			p.setHardSuccess(p.getHardSuccess() + 1);
		}
		else{
			p.setHardFailed(p.getHardFailed() + 1);
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
