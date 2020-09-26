package game;

public class Type {
	private String value;
	private static String[] validTypes = {
			"mine", "not_open", "number"	
		};
	
	public Type(String value){
		if(!isValidType(value)){
			throw new IllegalArgumentException("invalid game object type");
		}
		this.value = value;
	}
	
	private static boolean isValidType(String type) {
		for (String validType : validTypes) {
			if(validType.equalsIgnoreCase(validType)) {
				return true;
			}
		}
		return false;
	}
	
	public String getValue(){
		return value;
	}
}
