package org.ll.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ll.config.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ApplyServiceImplTest extends TestConfig {

	private final static Logger log = LoggerFactory.getLogger(ApplyServiceImplTest.class);
	
	
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
	@WithMockUser(username="f", password="p")
	public final void testQuery() {
		Apply apply = new Apply();
		apply.setCountry("cn");
		apply.setArea("asia");
		apply.setProvince("gd");
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
