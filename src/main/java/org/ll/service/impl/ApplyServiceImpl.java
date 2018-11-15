package org.ll.service.impl;

import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.business.models.applysystem.Apply;
import org.ll.service.ApplyService;
import org.ll.service.FileService;
import org.ll.service.common.CallAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ApplyServiceImpl implements ApplyService {

    private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);

    @Autowired
    private CallAPIService callAPIService;
    
    @Autowired
    private FileService fileService;

    public int save(Apply apply) {
    	callAPIService.put("/applys", apply);
    	
    	apply.setCity(apply.getCity() + "_sub");
    	callAPIService.put("/applys", apply);
//            callAPIService.put("/files", fileService.getImage(apply.getImage()));
        return 1;
    }

    public int delete(Apply Apply) {
    	int result = -1;
        try {
			callAPIService.delete("/applys", Apply);
			result = 1;
		} catch (Exception e) {
			log.error(null, e);
			result = -1;
		}
        return result;
    }

    public List<Apply> query(Apply apply) {
    	StringBuilder url = 
    			new StringBuilder("/applys")
			    	.append("/area").append(StringUtils.isNotBlank(apply.getArea())?("/"+apply.getArea()) : "")
					.append("/country").append(StringUtils.isNotBlank(apply.getCountry())?("/"+apply.getCountry()) : "")
    				.append("/province").append(StringUtils.isNotBlank(apply.getProvince())?("/"+apply.getProvince()) : "")
    				.append("/city").append(StringUtils.isNotBlank(apply.getCity())?("/"+apply.getCity()) : "")
    			;	
        return callAPIService.get(url.toString(), List.class, null);
    }

    @Override
    public int submit(Apply apply) {
    	int result = -1;
        try {
        	result = callAPIService.post("/applys", Integer.class, apply);
		} catch (Exception e) {
			result = -1;
		    log.error(null, e);
		}
        return result;
    }

	@Override
	public List<Apply> queryAll(List<Long> ids) {
		StringBuilder url = 
    			new StringBuilder("/applys?ids=")
				.append(StringUtils.join(ids, ","));
    			;	
        return callAPIService.get(url.toString(), List.class, null);
	}

}
