package com.quantdo.market.misc;

public enum BitstampMessageEnum {
	
	SUCCEES("subscription_succeeded"),
	ERROR("error"),
	PING("ping"),
	PONG("{\"heartbeat\":\"pong\"}");
	
	private String value;
	
    private BitstampMessageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
	
}
