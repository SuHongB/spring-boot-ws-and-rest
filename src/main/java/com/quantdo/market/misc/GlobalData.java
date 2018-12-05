package com.quantdo.market.misc;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GlobalData {
	
	/**
	 * Bitmex订阅信息
	 */
	public static final List<String> BITMEXTOPICLIST = new CopyOnWriteArrayList<String>();
	/**
	 * Bitmex交易所订阅信息  key=订阅类型（MARKET/DEPTH/KLINE)#品种#合约#占位   value=topic
	 */
	public static final Map<String,String> BITMEXTOPICMAP = new HashMap<String,String>();
	/**
	 * Bitmex交易所行情数据
	 */
	public static final Map<String,String> BITMEXMARKETMAP = new ConcurrentHashMap<String,String>();
	
	/**
	 * Bitstamp订阅信息
	 */
	public static final List<String> BITSTAMPTOPICLIST = new CopyOnWriteArrayList<String>();
	/**
	 * Bitstamp交易所订阅信息  key=订阅类型（MARKET/DEPTH/KLINE)#品种#合约#占位   value=topic
	 */
	public static final Map<String,String> BITSTAMPTOPICMAP = new HashMap<String,String>();
	/**
	 * Bitstamp交易所行情数据
	 */
	public static final Map<String,String> BITSTAMPMARKETMAP = new ConcurrentHashMap<String,String>();
	
	
	/**
	 * Kraken交易所行情数据
	 */
	public static final Map<String,String> KRAKENMARKETMAP = new ConcurrentHashMap<String,String>();
	
	
	/**
	 * Coinbase订阅信息
	 */
	public static final List<String> COINBASETOPICLIST = new CopyOnWriteArrayList<String>();
	/**
	 * Coinbase交易所symsol币种*/
	public static final List<String> COINBASESYMBOLlIST = new ArrayList<String>();
	/**
	 * Coinbase交易所行情数据
	 */
	public static final Map<String,String> COINBASEMARKETMAP = new ConcurrentHashMap<String,String>();
	
	

	public static List<String> getBitmextopiclist() {
		return BITMEXTOPICLIST;
	}


	public static Map<String, String> getBitmextopicmap() {
		return BITMEXTOPICMAP;
	}


	public static Map<String, String> getBitmexmarketmap() {
		return BITMEXMARKETMAP;
	}


	public static List<String> getBitstamptopiclist() {
		return BITSTAMPTOPICLIST;
	}


	public static Map<String, String> getBitstamptopicmap() {
		return BITSTAMPTOPICMAP;
	}


	public static Map<String, String> getBitstampmarketmap() {
		return BITSTAMPMARKETMAP;
	}


	public static Map<String, String> getKrakenmarketmap() {
		return KRAKENMARKETMAP;
	}


	public static List<String> getCoinbasetopiclist() {
		return COINBASETOPICLIST;
	}


	public static List<String> getCoinbasesymbollist() {
		return COINBASESYMBOLlIST;
	}


	public static Map<String, String> getCoinbasemarketmap() {
		return COINBASEMARKETMAP;
	}


}
