package com.quantdo.market.entity.vo.Bitstamp;


public class WsBitstampEntity {
	
	  	private String event;

	    private String data;

	    private String channel;

	    public void setEvent(String event){
	        this.event = event;
	    }
	    public String getEvent(){
	        return this.event;
	    }
	    public void setData(String data){
	        this.data = data;
	    }
	    public String getData(){
	        return this.data;
	    }
	    public void setChannel(String channel){
	        this.channel = channel;
	    }
	    public String getChannel(){
	        return this.channel;
	    }
		@Override
		public String toString() {
			return "BitstampWsEntity [event=" + event + ", data=" + data + ", channel=" + channel + "]";
		}
	    
}
