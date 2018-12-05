package com.quantdo.market.misc;

public enum CacheType {
	
	LOCAL("0"),REDIS("1");
	
	private String value;
	
    private CacheType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
