package org.ll.service;

import java.util.List;
import java.util.Map;

import org.ll.exception.AppException;
import org.ll.model.ApplyDO;
import org.ll.service.common.CallAPIService;
import org.ll.service.iface.ApplyService;
import org.ll.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class ApplyServiceImpl implements ApplyService {

    private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);

    @Autowired
    private CallAPIService callAPIService;
    
    @Autowired
    private FileService fileService;

    public int save(ApplyDO applyDO) {
            int result = callAPIService.post("/applys", int.class, applyDO);
            result = callAPIService.post("/file/upload.do", int.class, fileService.getImage(applyDO.getImage()));
            return result;
    }

    public int delete(ApplyDO applyDO) {
    	int result = -1;
        try {
			callAPIService.delete("/applys", applyDO);
			result = 1;
		} catch (Exception e) {
			log.error(null, e);
			result = -1;
		}
        return result;
    }

    public List<ApplyDO> query(ApplyDO applyDO) {
        return callAPIService.get("/applys", List.class, applyDO);
    }

    @Override
    public int submit(ApplyDO applyDO) {
    	int result = -1;
        try {
			callAPIService.put("/applys/submit", applyDO);
			result = 1;
		} catch (Exception e) {
			result = -1;
		    log.error(null, e);
		}
        return result;
    }

}
