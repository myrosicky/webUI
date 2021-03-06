package org.ll.controller;


import java.util.List;

import org.ll.model.QueryProductDO;
import org.ll.service.QueryProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/queryProduct")           
public class QueryProductController {

    private static final Logger log = LoggerFactory.getLogger(QueryProductController.class);
    
    @Autowired
    private QueryProductService queryProductService;
    
    @GetMapping("")    
    public String root(){
        return "queryProduct";
    }     
    
    @GetMapping(value = "/query.do", produces = "application/json; charset=UTF-8")
	@ResponseBody       
    public List<QueryProductDO> query(QueryProductDO queryProductDO){
        return queryProductService.query(queryProductDO);
    }   
    
    @GetMapping(value = "/viewProdDetail.do", produces = "application/json; charset=UTF-8")
	@ResponseBody       
    public List<QueryProductDO> viewProdDetail(QueryProductDO queryProductDO){
        return queryProductService.viewProdDetail(queryProductDO);
    }   
    
    
}
