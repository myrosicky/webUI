package org.ll.service.impl;

import java.util.List;
import java.util.Map;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.AuditDO;
import org.ll.service.AuditService;
import org.ll.service.common.CallAPIService;
import org.ll.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Autowired
    private CallAPIService callAPIService;

    public List<AuditDO> query(AuditDO auditDO) {
        return callAPIService.get("/audit/query.do", List.class, Util.convert(auditDO, Map.class));
    }


    public List<Apply> queryPendingApply(AuditDO auditDO) {
        return callAPIService.get("/audit/queryPendingApply.do", List.class, Util.convert(auditDO, Map.class));
    }


    public int pass(Apply apply) {
        return callAPIService.post("/audit/pass.do", int.class, apply);
    }


    public int reject(Apply apply) {
        return callAPIService.post("/audit/reject.do", int.class, apply);
    }


    public List<Apply> viewAuditHistory(AuditDO auditDO) {
        return callAPIService.get("/audit/viewAuditHistory.do", List.class, Util.convert(auditDO, Map.class));
    }

}
