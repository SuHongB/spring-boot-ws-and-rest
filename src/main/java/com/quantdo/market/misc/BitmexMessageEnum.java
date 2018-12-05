package com.quantdo.market.misc;

public enum BitmexMessageEnum {
	
	TABLE("table"),
	ACTION("action"),
	INSTRUMENT("instrument"),
	TRADE("trade"),
	SUCCEES("{\"success\":true"),
	ERROR("error"),
	PING("ping"),
	PONG("pong");
	
	private String value;
	
    private BitmexMessageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
	
}
