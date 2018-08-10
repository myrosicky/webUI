package org.ll.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ll.models.applysystem.Apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
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
public class ApplyServiceImplTest {

	private final static Logger log = LoggerFactory.getLogger(ApplyServiceImplTest.class);
	
	@TestConfiguration
	public static class TestConfig {
		
		private static final Logger log = LoggerFactory.getLogger(TestConfig.class);
		
		@Bean
		RestOperations restTemplate(){
			if(log.isDebugEnabled()){
				log.debug("testconfig, customize restTemplate");
			}
			return new RestTemplate();
		}
	}
	
	
	@Autowired
	private ApplyServiceImpl applyService;
	
	@Test
	public final void testSave() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDelete() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testQuery() {
		Apply apply = new Apply();
		apply.setCountry("cn");
		apply.setArea("asia");
		apply.setCity("gz");
		List<Apply> result = applyService.query(apply);
		log.debug("result.size():" + result.size());
		for(Apply tmp : result){
			log.debug(tmp.toString());
		}
		
	}

	@Test
	public final void testSubmit() {
		fail("Not yet implemented"); // TODO
	}

}
