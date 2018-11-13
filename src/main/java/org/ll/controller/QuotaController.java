package org.ll.controller;

import java.util.Calendar;
import java.util.List;

import org.ll.model.QuotaDO;
import org.ll.service.MessageService;
import org.ll.service.QuotaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/quota")           
public class QuotaController {

    private static final Logger log = LoggerFactory.getLogger(QuotaController.class);
    
    @Autowired
    private QuotaService quotaService;
    
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "", method = RequestMethod.GET)    
    public String root(){
        return "quota";
    }     
    
    @GetMapping(value = "/queryCurrentMonth.do")
	@ResponseBody       
    public List<QuotaDO> queryCurrentMonth(QuotaDO quotaDO){
        quotaDO.setMonth(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
        return quotaService.query(quotaDO);
    }   
    
    @RequestMapping(value = "/saveMonthQuota.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")    
    @ResponseBody      
    public  int  saveMonthQuota(@RequestBody QuotaDO quotaDO){
        int result = quotaService.saveMonthQuota(quotaDO);
        messageService.sendMsgToUpdateQuota(quotaDO);
        return result;
    }  
    
    @RequestMapping(value = "/changeMonthQuota.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")    
    @ResponseBody      
    public  int  changeMonthQuota(@RequestBody QuotaDO quotaDO){
        return quotaService.changeMonthQuota(quotaDO);
    }  
    
    @RequestMapping(value = "/deleteMonthQuota.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")    
    @ResponseBody      
    public  int  deleteMonthQuota(@RequestBody QuotaDO quotaDO){
        return quotaService.deleteMonthQuota(quotaDO);
    }  
}
