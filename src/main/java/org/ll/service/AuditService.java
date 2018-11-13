package org.ll.service;


import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.AuditDO;

public interface AuditService {

    public List<AuditDO> query(AuditDO auditDO);

    public List<Apply> queryPendingApply(AuditDO auditDO);

    public int pass(Apply apply);

    public int reject(Apply apply);

    public List<Apply> viewAuditHistory(AuditDO auditDO);
}