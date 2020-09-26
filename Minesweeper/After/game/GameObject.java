package game;

public class GameObject {
	private Type type;
	private Game game;
	private int number;
	boolean flagged;
	

	public GameObject(Game game, String type) {
		super();
		this.game = game;
		this.type = new Type(type);
		this.flagged = false;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getType() {
		return type.getValue();
	}
	
	public char toChar() {
		if(this.flagged) {
			return 'F';
		}
		
		if(type.getValue().equalsIgnoreCase("mine")) {
			return game.hideMine ? 'X' : 'M';
		}
		
		if(type.getValue().equalsIgnoreCase("not_open")) {
			return 'X';
		}
		
		if(type.getValue().equalsIgnoreCase("number")) {
			if(number == 0) return '-';
			return (char) ('0' + number);
		}
		
		throw new IllegalArgumentException();
	}
}
