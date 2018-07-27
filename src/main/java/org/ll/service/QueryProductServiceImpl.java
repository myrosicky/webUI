package org.ll.service;

import java.util.List;

import org.ll.model.QueryProductDO;
import org.ll.service.common.CallAPIService;
import org.ll.service.iface.QueryProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryProductServiceImpl implements QueryProductService {

    private static final Logger log = LoggerFactory.getLogger(QueryProductServiceImpl.class);
    
    @Autowired
    private CallAPIService callAPIService;

    @Override	
    public List<QueryProductDO>  query(QueryProductDO queryProductDO){
    	return callAPIService.get("/queryProduct/query.do", List.class, queryProductDO);
    }
    
    
    @Override	
    public List<QueryProductDO>  viewProdDetail(QueryProductDO queryProductDO){
    	return callAPIService.get("/queryProduct/viewProdDetail.do", List.class, queryProductDO);
    }
    
}
