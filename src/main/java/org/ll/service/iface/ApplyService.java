package org.ll.service.iface;


import java.util.List;

import org.stockws.model.Apply;

public interface ApplyService {

    public int save(Apply Apply);

    public int delete(Apply Apply);

    public List<Apply> query(Apply Apply);

    public int submit(Apply Apply);

}