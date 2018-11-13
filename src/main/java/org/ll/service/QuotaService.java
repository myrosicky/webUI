package org.ll.service;


import java.util.List;

import org.ll.model.QuotaDO;

public interface QuotaService {

    public List<QuotaDO> query(QuotaDO quotaDO);
    public int  saveMonthQuota(QuotaDO quotaDO);
    public int  changeMonthQuota(QuotaDO quotaDO);
    public int  deleteMonthQuota(QuotaDO quotaDO);
}