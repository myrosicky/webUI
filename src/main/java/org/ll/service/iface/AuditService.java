package org.ll.service.iface;


import java.util.List;

import org.ll.models.applysystem.Apply;
import org.ll.models.applysystem.AuditDO;

public interface AuditService {

    public List<AuditDO> query(AuditDO auditDO);

    public List<Apply> queryPendingApply(AuditDO auditDO);

    public int pass(Apply apply);

    public int reject(Apply apply);

    public List<Apply> viewAuditHistory(AuditDO auditDO);
}