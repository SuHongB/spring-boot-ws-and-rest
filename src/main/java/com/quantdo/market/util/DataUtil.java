package com.quantdo.market.util;

import java.util.ArrayList;
import java.util.List;
import com.quantdo.market.entity.Product;

public class DataUtil {
	
	public static List<Product> getBitmex(){
		List<Product> bitmexList = new ArrayList<>();
		bitmexList.add(new Product("XBTUSD"));
		return bitmexList;
	}
	
	public static List<Product> getBitstamp(){
		List<Product> bitstampList = new ArrayList<>();
		bitstampList.add(new Product("btcusd"));
		bitstampList.add(new Product("btceur"));
		bitstampList.add(new Product("eurusd"));
		bitstampList.add(new Product("xrpusd"));
		bitstampList.add(new Product("xrpeur"));
		bitstampList.add(new Product("xrpbtc"));
		bitstampList.add(new Product("ltcusd"));
		bitstampList.add(new Product("ltceur"));
		bitstampList.add(new Product("ltcbtc"));
		bitstampList.add(new Product("ethusd"));
		bitstampList.add(new Product("etheur"));
		bitstampList.add(new Product("ethbtc"));
		bitstampList.add(new Product("bchusd"));
		bitstampList.add(new Product("bcheur"));
		bitstampList.add(new Product("bchbtc"));
		return bitstampList;
	}
	
	public static List<Product> getKraken(){
		List<Product> krakenTest = new ArrayList<>();
		krakenTest.add(new Product("XXBTZUSD"));
		krakenTest.add(new Product("XETHZUSD"));
		krakenTest.add(new Product("XLTCZUSD"));
		
		return krakenTest;
	}
	
	public static List<Product> getCoinbase(){
		List<Product> GdaxTest = new ArrayList<>();
		GdaxTest.add(new Product("ETH-USD"));
		GdaxTest.add(new Product("ETH-EUR"));
		return GdaxTest;
	}
}
