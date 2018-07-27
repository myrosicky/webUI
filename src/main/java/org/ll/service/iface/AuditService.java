package org.ll.service.iface;


import java.util.List;

import org.ll.model.ApplyDO;
import org.ll.model.AuditDO;

public interface AuditService {

    public List<AuditDO> query(AuditDO auditDO);

    public List<ApplyDO> queryPendingApply(AuditDO auditDO);

    public int pass(ApplyDO apply);

    public int reject(ApplyDO apply);

    public List<ApplyDO> viewAuditHistory(AuditDO auditDO);
}