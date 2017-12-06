package org.ll.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ll.model.LogInfoDO;
import org.ll.model.MenuDO;
import org.ll.service.iface.LogAnalyzeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchDataController {

    private static final Logger log = LoggerFactory.getLogger(FetchDataController.class);

    @Autowired
    private LogAnalyzeService fileService;

    @RequestMapping(value = "/file/analyzeLog.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    List<LogInfoDO> analyzeLog(@RequestParam String name, @RequestParam(defaultValue = "") String searchContent,
        @RequestParam(defaultValue = "") String logLevel, @RequestParam(defaultValue = "") String dateFrom,
        @RequestParam(defaultValue = "") String dateTo, @RequestParam(defaultValue = "10") int amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateF = null;
        try {
            dateF = sdf.parse(dateFrom);
        } catch (ParseException e) {
            log.error("", e);
        }

        Date dateT = null;
        try {
            dateT = sdf.parse(dateTo);
        } catch (ParseException e) {
            log.error("", e);
        }
        return fileService.analyse(name, logLevel, dateF, dateT, amount, searchContent);
    }
    
    @GetMapping(value = "/getMenu.do", produces = "application/json; charset=UTF-8")
    List<MenuDO> getMenu(Principal principal) {
        return null;
    }
    
    
    
}
