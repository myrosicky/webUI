package org.ll.service;

import java.util.List;

import org.ll.model.QuotaDO;
import org.ll.service.common.CallAPIService;
import org.ll.service.iface.QuotaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotaServiceImpl implements QuotaService {

    private static final Logger log = LoggerFactory.getLogger(QuotaServiceImpl.class);
    
    @Autowired
    private CallAPIService callAPIService;
    
    @Override	
    public List<QuotaDO>  query(QuotaDO quotaDO){
        return callAPIService.get("/quota/query.do", List.class, quotaDO);
    }
    
    	
    public int saveMonthQuota(QuotaDO quotaDO){
        return callAPIService.post("/quota/saveMonthQuota.do", int.class, quotaDO);
    }
    
    	
    public int changeMonthQuota(QuotaDO quotaDO){
        return callAPIService.post("/quota/changeMonthQuota.do", int.class, quotaDO);
    }
    
    @Override	
    public int deleteMonthQuota(QuotaDO quotaDO){
        return callAPIService.post("/quota/delete.do", int.class, quotaDO);
    }	
    
}
