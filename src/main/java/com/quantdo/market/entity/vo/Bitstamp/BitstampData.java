package com.quantdo.market.entity.vo.Bitstamp;

import java.math.BigDecimal;

public class BitstampData {
	
		private Long id;
		
	    private BigDecimal amount;

	    private Long buy_order_id;

	    private Long sell_order_id;

	    private String amount_str;

	    private String price_str;

	    private String timestamp;

	    private BigDecimal price;

	    private Integer type;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public Long getBuy_order_id() {
			return buy_order_id;
		}

		public void setBuy_order_id(Long buy_order_id) {
			this.buy_order_id = buy_order_id;
		}

		public Long getSell_order_id() {
			return sell_order_id;
		}

		public void setSell_order_id(Long sell_order_id) {
			this.sell_order_id = sell_order_id;
		}

		public String getAmount_str() {
			return amount_str;
		}

		public void setAmount_str(String amount_str) {
			this.amount_str = amount_str;
		}

		public String getPrice_str() {
			return price_str;
		}

		public void setPrice_str(String price_str) {
			this.price_str = price_str;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return "BitstampData [id=" + id + ", amount=" + amount + ", buy_order_id=" + buy_order_id + ", sell_order_id="
					+ sell_order_id + ", amount_str=" + amount_str + ", price_str=" + price_str + ", timestamp="
					+ timestamp + ", price=" + price + ", type=" + type + "]";
		}
		

}
