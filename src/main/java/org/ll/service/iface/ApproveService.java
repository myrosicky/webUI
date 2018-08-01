package org.ll.service.iface;


import java.util.List;

import org.stockws.model.Apply;
import org.stockws.model.ApproveDO;

public interface ApproveService {

    public List<ApproveDO> query(ApproveDO approveDO);

    public List<Apply> queryPendingApply(ApproveDO approveDO);

    public int approve(Apply Apply);

    public int reject(Apply Apply);

    public List<ApproveDO> viewApproveHistory(ApproveDO approveDO);
}