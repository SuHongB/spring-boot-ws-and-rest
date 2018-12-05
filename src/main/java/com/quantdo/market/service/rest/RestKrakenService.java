package com.quantdo.market.service.rest;

import java.util.List;

import com.quantdo.market.entity.Product;
import com.quantdo.market.entity.TickerEntity;

public interface RestKrakenService {

	boolean saveTicker(List<Product> list);

	List<TickerEntity> getTicker();

}
