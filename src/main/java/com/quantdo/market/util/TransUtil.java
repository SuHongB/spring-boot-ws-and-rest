package com.quantdo.market.util;


import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 数据类型转换
 * @author suhongbin
 */
public class TransUtil {

	private static  Logger logger = LogManager.getLogger(TransUtil.class);
	
	public static BigDecimal getDecimal(Object obj){
		BigDecimal result = null;
		try {
			if(null != obj){
				if(obj instanceof String){
					result = new BigDecimal((String)obj);
				}else if(obj instanceof Integer){
					result = new BigDecimal((Integer)obj);
				}else if(obj instanceof Long){
					result = new BigDecimal((Long)obj);
				}else if(obj instanceof Double){
					result = new BigDecimal((Double)obj);
				}
			}
		} catch (Exception e) {
			logger.error(obj.toString()+"转换BigDecimal异常",e);
		}
		return result;
	}
	
	public static Long getLong(Object obj){
		Long result = null;
		try {
			if(null != obj){
				if(obj instanceof String){
					result = new Long((String)obj);
				}else if(obj instanceof Integer){
					result = (Long)obj;
				}else if(obj instanceof Long){
					result = (Long)obj;
				}
			}
		} catch (Exception e) {
			logger.error(obj.toString()+"转换Long异常",e);
		}
		return result;
	}
}
