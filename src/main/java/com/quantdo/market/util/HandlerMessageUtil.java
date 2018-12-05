package com.quantdo.market.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;

public class HandlerMessageUtil {
	
	public static <T> List<T> getMessgae(Map<String,String> map,String type,Class<T> clazz){
		List<T> result = new ArrayList<T>();
		try {
			if(!StringUtils.isEmpty(type) || !CollectionUtils.isEmpty(map)){
				for(Iterator<?> ite = map.keySet().iterator();ite.hasNext();){
					String key = (String)ite.next();
					if(key.startsWith(type)){
						T t = (T) JSON.parseObject(map.get(key),clazz);
						result.add(t);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
