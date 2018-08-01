package org.ll.service.iface;


import java.util.List;

import org.ll.model.QueryProductDO;

public interface QueryProductService {

    public List<QueryProductDO> query(QueryProductDO queryProductDO);
    public List<QueryProductDO> viewProdDetail(QueryProductDO queryProductDO);
}