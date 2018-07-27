package org.ll.service.iface;


import java.util.List;

import org.ll.exception.AppException;
import org.ll.model.ApplyDO;

public interface ApplyService {

    public int save(ApplyDO applyDO);

    public int delete(ApplyDO applyDO);

    public List<ApplyDO> query(ApplyDO applyDO);

    public int submit(ApplyDO applyDO);

}