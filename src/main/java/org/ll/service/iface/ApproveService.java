package org.ll.service.iface;


import java.util.List;

import org.ll.model.ApplyDO;
import org.ll.model.ApproveDO;

public interface ApproveService {

    public List<ApproveDO> query(ApproveDO approveDO);

    public List<ApplyDO> queryPendingApply(ApproveDO approveDO);

    public int approve(ApplyDO applyDO);

    public int reject(ApplyDO applyDO);

    public List<ApproveDO> viewApproveHistory(ApproveDO approveDO);
}