package com.quantdo.market.misc;

public enum CoinbaseMessageEnum {
	
	TICKER("ticker"),
	SUCCEES("subscriptions"),
	ERROR("error"),
	PING("heartbeat"),
	PONG("{\"heartbeat\":\"pong\"}");
	
	private String value;
	
    private CoinbaseMessageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
	
}
