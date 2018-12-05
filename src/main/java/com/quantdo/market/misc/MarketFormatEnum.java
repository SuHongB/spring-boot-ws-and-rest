package com.quantdo.market.misc;

/**
 * 配置信息替换
 * @author suhongbin
 */
public enum MarketFormatEnum {
	
	CURRENCY("{C}"),//币种
	SYMBOL("{S}"),//有计价单位的币种
	CHANNEL("{X}");//频道
	
	private String value;
	
    private MarketFormatEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
	
}
