package com.quantdo.market.entity;

public class Product {

	private Long id;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 币种代码
	 */
	private String symbol;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public Product() {
		super();
	}
	public Product(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	
	
}
