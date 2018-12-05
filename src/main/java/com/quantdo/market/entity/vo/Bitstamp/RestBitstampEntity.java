package com.quantdo.market.entity.vo.Bitstamp;

public class RestBitstampEntity {
	
	  	private String high;

	    private String last;

	    private String timestamp;

	    private String bid;

	    private String vwap;

	    private String volume;

	    private String low;

	    private String ask;

	    private String open;

	    public void setHigh(String high){
	        this.high = high;
	    }
	    public String getHigh(){
	        return this.high;
	    }
	    public void setLast(String last){
	        this.last = last;
	    }
	    public String getLast(){
	        return this.last;
	    }
	    public void setTimestamp(String timestamp){
	        this.timestamp = timestamp;
	    }
	    public String getTimestamp(){
	        return this.timestamp;
	    }
	    public void setBid(String bid){
	        this.bid = bid;
	    }
	    public String getBid(){
	        return this.bid;
	    }
	    public void setVwap(String vwap){
	        this.vwap = vwap;
	    }
	    public String getVwap(){
	        return this.vwap;
	    }
	    public void setVolume(String volume){
	        this.volume = volume;
	    }
	    public String getVolume(){
	        return this.volume;
	    }
	    public void setLow(String low){
	        this.low = low;
	    }
	    public String getLow(){
	        return this.low;
	    }
	    public void setAsk(String ask){
	        this.ask = ask;
	    }
	    public String getAsk(){
	        return this.ask;
	    }
	    public void setOpen(String open){
	        this.open = open;
	    }
	    public String getOpen(){
	        return this.open;
	    }
		@Override
		public String toString() {
			return "BitstampEntity [high=" + high + ", last=" + last + ", timestamp=" + timestamp + ", bid=" + bid
					+ ", vwap=" + vwap + ", volume=" + volume + ", low=" + low + ", ask=" + ask + ", open=" + open
					+ "]";
		}
	    
}
