package org.ll.service;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.ll.service.common.CallAPIService;
import org.ll.service.iface.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyServiceImpl implements ApplyService {

    private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);

    @Autowired
    private CallAPIService callAPIService;
    
    @Autowired
    private FileService fileService;

    public int save(Apply apply) {
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
        return callAPIService.get("/applys/area/" + apply.getArea() + "/country/" + apply.getCountry() + "/province/" + apply.getProvince() + "/city/" + apply.getCity(), List.class, null);
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

}
