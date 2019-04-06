package org.ll.service;


import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.Approve;

public interface ApproveService {

    public List<Approve> query(Approve approveDO);

    public List<Apply> queryPendingApply(Approve approveDO);

    public int approve(Apply Apply);

    public int reject(Apply Apply);

    public List<Approve> viewApproveHistory(Approve approveDO);
}