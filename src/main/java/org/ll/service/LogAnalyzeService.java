package org.ll.service;

import java.util.Date;
import java.util.List;

import org.ll.model.LogInfoDO;


public interface LogAnalyzeService {

    public abstract List<LogInfoDO> analyse(String fileName, String logLevel, Date dateFrom, Date dateTo, int resultAmount,
        String searchContent);

}