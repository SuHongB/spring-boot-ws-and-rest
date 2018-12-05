package com.quantdo.market;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.quantdo.market.mapper")
public class Application {

	private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(Application.class, args);
	}
	
	public static <T> T getService(Class<T> arg){
		return null != applicationContext ? (T) applicationContext.getBean(arg) : null;
	}
}
