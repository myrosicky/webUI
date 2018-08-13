package org.ll.config;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={
		"eureka.client.register-with-eureka=false",
    	"eureka.client.fetch-registry=false",
    	"api.port=9091",
    	"api.context="
		})
public class TestConfig {
	
	private static final Logger log = LoggerFactory.getLogger(TestConfig.class);
	
	@Bean
	RestOperations restTemplate(){
		if(log.isDebugEnabled()){
			log.debug("testconfig, customize restTemplate");
		}
		return new RestTemplate();
	}
}
