package org.ll.service;

import java.util.List;

import org.ll.service.common.CallAPIService;
import org.ll.service.iface.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stockws.model.Apply;

@Service
public class ApplyServiceImpl implements ApplyService {

    private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);

    @Autowired
    private CallAPIService callAPIService;
    
    @Autowired
    private FileService fileService;

    public int save(Apply Apply) {
            int result = callAPIService.post("/applys", int.class, Apply);
            result = callAPIService.post("/file/upload.do", int.class, fileService.getImage(Apply.getImage()));
            return result;
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
        return callAPIService.get("/applys/" + apply.getCountry() + "/" + apply.getArea() + "/" + apply.getCity(), List.class, apply);
    }

    @Override
    public int submit(Apply Apply) {
    	int result = -1;
        try {
			callAPIService.put("/applys/submit", Apply);
			result = 1;
		} catch (Exception e) {
			result = -1;
		    log.error(null, e);
		}
        return result;
    }

}
