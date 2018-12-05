package com.quantdo.market.entity.vo.coinbase;

public class WsCoinbaseTicker {

	    private String type;
	    
	    private long sequence;
	    
	    private String product_id;
	    
	    private String price;
	    
	    private String open_24h;
	    
	    private String volume_24h;
	    
	    private String low_24h;
	    
	    private String high_24h;
	    
	    private String volume_30d;
	    
	    private String best_bid;
	    
	    private String best_ask;
	    
	    public void setType(String type) {
	         this.type = type;
	     }
	     public String getType() {
	         return type;
	     }

	    public void setSequence(long sequence) {
	         this.sequence = sequence;
	     }
	     public long getSequence() {
	         return sequence;
	     }

	    public void setProduct_id(String product_id) {
	         this.product_id = product_id;
	     }
	     public String getProduct_id() {
	         return product_id;
	     }

	    public void setPrice(String price) {
	         this.price = price;
	     }
	     public String getPrice() {
	         return price;
	     }

	    public void setOpen_24h(String open_24h) {
	         this.open_24h = open_24h;
	     }
	     public String getOpen_24h() {
	         return open_24h;
	     }

	    public void setVolume_24h(String volume_24h) {
	         this.volume_24h = volume_24h;
	     }
	     public String getVolume_24h() {
	         return volume_24h;
	     }

	    public void setLow_24h(String low_24h) {
	         this.low_24h = low_24h;
	     }
	     public String getLow_24h() {
	         return low_24h;
	     }

	    public void setHigh_24h(String high_24h) {
	         this.high_24h = high_24h;
	     }
	     public String getHigh_24h() {
	         return high_24h;
	     }

	    public void setVolume_30d(String volume_30d) {
	         this.volume_30d = volume_30d;
	     }
	     public String getVolume_30d() {
	         return volume_30d;
	     }

	    public void setBest_bid(String best_bid) {
	         this.best_bid = best_bid;
	     }
	     public String getBest_bid() {
	         return best_bid;
	     }

	    public void setBest_ask(String best_ask) {
	         this.best_ask = best_ask;
	     }
	     public String getBest_ask() {
	         return best_ask;
	     }
		@Override
		public String toString() {
			return "GdaxWsEntity [type=" + type + ", sequence=" + sequence + ", product_id=" + product_id + ", price="
					+ price + ", open_24h=" + open_24h + ", volume_24h=" + volume_24h + ", low_24h=" + low_24h
					+ ", high_24h=" + high_24h + ", volume_30d=" + volume_30d + ", best_bid=" + best_bid + ", best_ask="
					+ best_ask + "]";
		}
	     
}
