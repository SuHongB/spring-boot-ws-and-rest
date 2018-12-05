package com.quantdo.market.util;

import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class RestAplService {
	
	private static CloseableHttpClient HTTPCLIENT = HttpClients.createDefault();
	
	private static RequestConfig REQUESTCONFIG = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();//设置请求和传输超时时间
	

	public static CloseableHttpClient createSSL(){
	     try {
             SSLContext sslContext = SSLContext.getInstance("TLS");
             sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                 public void checkClientTrusted(X509Certificate[] xcs, String string) {}
                 public void checkServerTrusted(X509Certificate[] xcs, String string) {}
                 public X509Certificate[] getAcceptedIssuers() {return null;}
	          }}, null);
			@SuppressWarnings("deprecation")
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	         return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	        return  HTTPCLIENT;
	  }
	
	
	/**
	 * Get请求
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String doGet(String url,String charset){
		String result = null;
		CloseableHttpResponse response = null;
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(REQUESTCONFIG);
			response = HTTPCLIENT.execute(httpget);
			if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
		} catch (Exception e) {
			throw new RuntimeException("http接口调用失败："+e.getMessage(), e);
		} finally {
			close(response);
        }
		return result;
     } 
	
	/**
	 * Post请求
	 * @param url
	 * @param paramsStr
	 * @param charset
	 * @return
	 */
	public static String doPost(String url, String paramsStr,String charset){
			String result = null;
	        CloseableHttpResponse response = null;
	        try {
	        	HttpPost httpPost = new HttpPost(url);
	        	httpPost.setConfig(REQUESTCONFIG);
	        	httpPost.setEntity(new StringEntity(URLEncoder.encode(paramsStr, charset)));
	        	response = HTTPCLIENT.execute(httpPost);
	        	if(response != null){  
	                HttpEntity resEntity = response.getEntity();  
	                if(resEntity != null){  
	                    result = EntityUtils.toString(resEntity,charset);  
	                }  
	            }  
	        } catch (Exception e) {
	        	throw new RuntimeException("http接口调用失败："+e.getMessage(), e);
			} finally {
				close(response);
	        }
	        return result;

     }  
	/**
	 * Post请求
	 * @param url
	 * @param paramsMap
	 * @param charset
	 * @return
	 */
	public static String doPost(String url, Map<String,String> paramsMap,String charset){
		String result = null;  
        CloseableHttpResponse response = null;
        try {
        	HttpPost httpPost = new HttpPost(url);
        	httpPost.setConfig(REQUESTCONFIG);
        	if(!paramsMap.isEmpty()){
        		List<NameValuePair> list = new ArrayList<NameValuePair>();
        		for(Iterator<?> ite = paramsMap.keySet().iterator(); ite.hasNext();){
        			String key = (String)ite.next();
        			list.add(new BasicNameValuePair(key,paramsMap.get(key)));  
        		}
        		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity); 
        	}
        	response = HTTPCLIENT.execute(httpPost);
        	if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        } catch (Exception e) {
        	throw new RuntimeException("http接口调用失败："+e.getMessage(), e);
		} finally {
           close(response);
        }
        return result;
	}  
    
	@SuppressWarnings("all")
	private static void close(CloseableHttpResponse response){
		try {
        	if(null != response){
        		EntityUtils.consume(response.getEntity());
				response.close();	
        	}
		} catch (Exception e) {
			throw new RuntimeException("关闭流失败："+e.getMessage(), e);
		}
	}
	
}
