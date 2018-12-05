package com.quantdo.market.service.rest;

import java.util.List;

import com.quantdo.market.entity.Product;
import com.quantdo.market.entity.TickerEntity;

/**
 * Bitstamp交易所Rest
 * @author suhongbin
 */
public interface RestBitstampService {
	
	/**
	 * 保存Ticker数据
	 * @param list
	 * @return
	 */
	boolean saveTicker(List<Product> list);

	/**
	 * 获取Ticker数据
	 * @return
	 */
	List<TickerEntity> getTicker();

}
