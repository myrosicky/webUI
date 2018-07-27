package org.ll.service.iface;


import java.util.List;

import org.ll.model.QuotaDO;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

public interface QuotaService {

    public List<QuotaDO> query(QuotaDO quotaDO);
    public int  saveMonthQuota(QuotaDO quotaDO);
    public int  changeMonthQuota(QuotaDO quotaDO);
    public int  deleteMonthQuota(QuotaDO quotaDO);
}