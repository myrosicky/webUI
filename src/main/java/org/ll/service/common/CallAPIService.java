package org.ll.service.common;

import java.util.Map;
import java.util.Map.Entry;

import org.ll.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class CallAPIService {

	private final static Logger log = LoggerFactory.getLogger(CallAPIService.class);
	
	@Value("${api.protocol}")
	private String protocol;
	
	@Value("${api.host}")
	private String host;
	
	@Value("${api.port}")
	private String port;
	
	@Value("${api.context}")
	private String context;
	
	
	
	@Autowired
//	private OAuth2RestOperations restTemplate;
	private RestOperations restTemplate;
	

	public void delete(String url, Object reqBody) {
		restTemplate.delete(url, reqBody);
	}
	
	public void put(String url, Object reqBody) {
		restTemplate.put(url, reqBody);
	}
	
	public <T> T post(String url, Class<T> rtnClass, Object reqBody) {
		return restTemplate.postForObject(buildFullAPIURL(url), reqBody, rtnClass);
	}
	
	private String buildFullAPIURL(String apiMethodUrl){
		return buildFullAPIURL(apiMethodUrl, null);
	}
	private String buildFullAPIURL(String apiMethodUrl, Map<String, String> reqParams){
		StringBuilder sb = 
				new StringBuilder(50)
		.append(protocol).append("://").append(host).append(":").append(port).append(context).append("/").append(apiMethodUrl)
		;
		
		if(reqParams != null && !reqParams.isEmpty()){
			sb.append("?");
			for(Entry<String, String> entry : reqParams.entrySet()){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			sb.deleteCharAt(sb.length() - 1 );
		}
		return sb.toString();
	}


	public <T>  T get(String url, Class<T> rtnClass, Object reqParams) {
		
		Map<String, String> parsedReqParams = null;
		if(reqParams != null){
			parsedReqParams = (Map<String, String>) Util.convert(reqParams, Map.class);
		}
		return restTemplate.getForObject(buildFullAPIURL(url, parsedReqParams), rtnClass);
	}
	
}
