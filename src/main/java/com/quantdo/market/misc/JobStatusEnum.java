package com.quantdo.market.misc;

public enum JobStatusEnum {
	ACTIVE("0"),KILL("1");
	
	private String value;
	
    private JobStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
	
}
