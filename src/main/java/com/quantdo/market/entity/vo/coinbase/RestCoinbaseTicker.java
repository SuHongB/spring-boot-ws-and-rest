package com.quantdo.market.entity.vo.coinbase;

public class RestCoinbaseTicker {
	
	private String trade_id;    // 54956104,
	
	private String price;    // 4130.00000000,
	
	private String size;    // 0.18534446,
	
	private String bid;    // 4129.99,
	
	private String ask;    // 4130,
	
	private String volume;    // 35231.17229689,
	
	private String time;    // 2018-11-29T01;    //41;    //51.734000Z
	
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getAsk() {
		return ask;
	}
	public void setAsk(String ask) {
		this.ask = ask;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
