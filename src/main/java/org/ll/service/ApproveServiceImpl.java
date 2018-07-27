package org.ll.service;

import java.util.List;
import java.util.Map;

import org.ll.model.ApplyDO;
import org.ll.model.ApproveDO;
import org.ll.service.common.CallAPIService;
import org.ll.service.iface.ApproveService;
import org.ll.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApproveServiceImpl implements ApproveService {

    private static final Logger log = LoggerFactory.getLogger(ApproveServiceImpl.class);

    @Autowired
    private CallAPIService callAPIService;

    public List<ApproveDO> query(ApproveDO approveDO) {
        return callAPIService.get("/approve/query.do", List.class, Util.convert(approveDO, Map.class));
    }


    public List<ApplyDO> queryPendingApply(ApproveDO approveDO) {
        return callAPIService.get("/approve/queryPendingApply.do", List.class, Util.convert(approveDO, Map.class));
    }


    public int approve(ApplyDO applyDO) {
        return callAPIService.post("/approve/approve.do", int.class, applyDO);
    }


    public int reject(ApplyDO applyDO) {
        return callAPIService.post("/approve/reject.do", int.class, applyDO);
    }


    public List<ApproveDO> viewApproveHistory(ApproveDO approveDO) {
        return callAPIService.get("/approve/viewApproveHistory.do", List.class, Util.convert(approveDO, Map.class));
    }

}
