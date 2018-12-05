package com.quantdo.market.entity;

import java.math.BigDecimal;

public class TickerEntity {
	
	private String symbol;
	
	private BigDecimal open;
	
	private BigDecimal high;
	
	private BigDecimal low;
	
	private BigDecimal lastPrice;
	
	private BigDecimal lastVolume;
	
	private BigDecimal volumeToday;
	
	private BigDecimal bidPrice;
	
	private BigDecimal bidSize;
	
	private BigDecimal askPrice;
	
	private BigDecimal askSize;
	
	private Long timeStamp;


	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getLastVolume() {
		return lastVolume;
	}

	public void setLastVolume(BigDecimal lastVolume) {
		this.lastVolume = lastVolume;
	}

	public BigDecimal getVolumeToday() {
		return volumeToday;
	}

	public void setVolumeToday(BigDecimal volumeToday) {
		this.volumeToday = volumeToday;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public BigDecimal getBidSize() {
		return bidSize;
	}

	public void setBidSize(BigDecimal bidSize) {
		this.bidSize = bidSize;
	}

	public BigDecimal getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(BigDecimal askPrice) {
		this.askPrice = askPrice;
	}

	public BigDecimal getAskSize() {
		return askSize;
	}

	public void setAskSize(BigDecimal askSize) {
		this.askSize = askSize;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "TickerEntity [symbol=" + symbol + ", open=" + open + ", high=" + high + ", low=" + low + ", lastPrice="
				+ lastPrice + ", lastVolume=" + lastVolume + ", volumeToday=" + volumeToday + ", bidPrice=" + bidPrice
				+ ", bidSize=" + bidSize + ", askPrice=" + askPrice + ", askSize=" + askSize + ", timeStamp="
				+ timeStamp + "]";
	}

	
	
}
