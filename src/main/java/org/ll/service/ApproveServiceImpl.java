package org.ll.service;

import java.util.List;
import java.util.Map;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.ApproveDO;
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


    public List<Apply> queryPendingApply(ApproveDO approveDO) {
        return callAPIService.get("/approve/queryPendingApply.do", List.class, Util.convert(approveDO, Map.class));
    }


    public int approve(Apply Apply) {
        return callAPIService.post("/approve/approve.do", int.class, Apply);
    }


    public int reject(Apply Apply) {
        return callAPIService.post("/approve/reject.do", int.class, Apply);
    }


    public List<ApproveDO> viewApproveHistory(ApproveDO approveDO) {
        return callAPIService.get("/approve/viewApproveHistory.do", List.class, Util.convert(approveDO, Map.class));
    }

}
