package org.ll.service;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.business.models.applysystem.Apply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ll.config.TestConfig;
import org.ll.service.impl.ApplyServiceImpl;
import org.ll.util.TimeUtil;
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
	public final void testDelete() {
		
	}

	@Test
	@WithMockUser(username="f", password="p")
	public final void testQuery() {
		Apply apply = new Apply();
		apply.setCountry("cn");
		apply.setArea("asia");
		apply.setProvince("gd");
//		apply.setCity("gz");
		List<Apply> result = applyService.query(apply);
		log.debug("result.size():" + result.size());
		for(Apply tmp : result){
			log.debug(tmp.toString());
		}
	}

	@Test
	@WithMockUser(username="f", password="p")
	public final void testSave4Create() {
		Apply apply = new Apply();
		apply.setCountry("cn")              ;
		apply.setArea("asia")               ;
		apply.setProvince("gd")             ;
		apply.setCity("gz")                 ;
		apply.setCreateBy("junit")          ;
		apply.setCreateTime(TimeUtil.getCurrentTime())     ;
		apply.setIp("127.0.0.1")            ;
		apply.setNumber("A1")               ;
		apply.setType(Apply.TYPE_INDIVIDUAL);
		apply.setUserID(1l)                 ;
		
		applyService.save(apply);
	
		Apply apply2 = new Apply();
		apply2.setCountry("cn")              ;
		apply2.setArea("asia")               ;
		apply2.setProvince("gd")             ;
		apply2.setCity("zh")                 ;
		apply2.setCreateBy("junit")          ;
		apply2.setCreateTime(TimeUtil.getCurrentTime())     ;
		apply2.setIp("127.0.0.1")            ;
		apply2.setNumber("A2")               ;
		apply2.setType(Apply.TYPE_INDIVIDUAL);
		apply2.setUserID(1l)                 ;
		
		applyService.save(apply2);
	}
	
	@Test
	@WithMockUser(username="f", password="p")
	public final void testSave4U() {
		long id = 0l;
		Apply apply = new Apply();
		apply.setId(id);
		List<Apply> applys = applyService.query(apply);
		if(!applys.isEmpty()){
			apply = applys.get(0);
			apply.setUpdateBy("junit");
			apply.setUpdateTime(TimeUtil.getCurrentTime());
			applyService.save(apply);
		}
		
	}
	

}
