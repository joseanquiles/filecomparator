package com.joseanquiles.comparator;

public class Line {

	private int linenumber;
	private String line;

	public Line(int linenumber, String line) {
		this.linenumber = linenumber;
		this.line = line;
	}

	public int getLinenumber() {
		return linenumber;
	}

	public String getLine() {
		return line;
	}
	
	public void setLine(String line) {
		this.line = line;
	}
		
}
