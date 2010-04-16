package com.barefoot.smsdigger.workers;


public class KeywordExtractor {
	
	private String statement;
	
	public KeywordExtractor(final String statement) {
		this.statement = statement;
	}
	
	public String[] extractKeywords() {
    	if(null != statement && statement.trim().length() > 0) {
    		return statement.split(" ");
    	}
    	return new String[] {};
    }

}
