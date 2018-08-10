package org.ll.service.iface;


import java.util.List;

import org.ll.models.applysystem.Apply;
import org.ll.models.applysystem.ApproveDO;

public interface ApproveService {

    public List<ApproveDO> query(ApproveDO approveDO);

    public List<Apply> queryPendingApply(ApproveDO approveDO);

    public int approve(Apply Apply);

    public int reject(Apply Apply);

    public List<ApproveDO> viewApproveHistory(ApproveDO approveDO);
}