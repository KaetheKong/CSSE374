package Utilities;

public class Parser {
	private String toParse;
	
	public Parser(String s) {
		this.toParse = s;
	}
	
	public String parse() {
		String[] temp = this.toParse.replace(".", "/").split("/");
		String str = this.toParse;
		if (temp.length >= 1) {
			str = temp[temp.length - 1];
		}
		
		return str;
	}

	public void setToParse(String toParse) {
		this.toParse = toParse;
	}
}
