package com.barefoot.smsdigger.parser;

public class TextParser {
	
	private String[] keywords;
	
	public TextParser(String[] keywords){
		int index = 0;
		this.keywords = new String[keywords.length];
		for (String keyword : keywords) {
			this.keywords[index++] = keyword.toUpperCase();
		}
	}
	
	public int matchCountFor(String text) {
		int score = 0;
		text = text.toUpperCase();
		for (String keyword : keywords) {
			score = score + (text.contains((keyword)) ? 1 : 0);
		}
		return score;
	}
}
