package org.ll.service;


import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.ApproveDO;

public interface ApproveService {

    public List<ApproveDO> query(ApproveDO approveDO);

    public List<Apply> queryPendingApply(ApproveDO approveDO);

    public int approve(Apply Apply);

    public int reject(Apply Apply);

    public List<ApproveDO> viewApproveHistory(ApproveDO approveDO);
}