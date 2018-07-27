package org.ll.service;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ll.model.LogInfoDO;
import org.ll.service.common.CallAPIService;
import org.ll.service.iface.LogAnalyzeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogAnalyzeServiceRestImpl implements LogAnalyzeService {

    private static final Logger log = LoggerFactory.getLogger(LogAnalyzeServiceRestImpl.class);

    @Autowired
    private CallAPIService callAPIService;

    public List<LogInfoDO> analyse(String fileName, String logLevel, Date dateFrom, Date dateTo, int amount, String searchContent) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
        Map<String, String> uriVariables = new HashMap<String, String>(5);
        uriVariables.put("name", fileName);
        uriVariables.put("amount", String.valueOf(amount));
        uriVariables.put("logLevel", logLevel);
        if (dateFrom != null)
            uriVariables.put("dateFrom", dateFormat.format(dateFrom));
        if (dateTo != null)
            uriVariables.put("dateTo", dateFormat.format(dateTo));

        uriVariables.put("searchContent", searchContent);
        return callAPIService.get("/log/analyze.do", List.class, uriVariables);
    }

}
