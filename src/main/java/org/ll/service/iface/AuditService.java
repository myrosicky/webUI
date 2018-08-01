package org.ll.service.iface;


import java.util.List;

import org.stockws.model.Apply;
import org.stockws.model.AuditDO;

public interface AuditService {

    public List<AuditDO> query(AuditDO auditDO);

    public List<Apply> queryPendingApply(AuditDO auditDO);

    public int pass(Apply apply);

    public int reject(Apply apply);

    public List<Apply> viewAuditHistory(AuditDO auditDO);
}