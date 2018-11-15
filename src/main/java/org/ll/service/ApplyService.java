package org.ll.service;


import java.util.List;
import java.util.concurrent.Future;

import org.business.models.applysystem.Apply;

public interface ApplyService {

    public int save(Apply Apply);

    public int delete(Apply Apply);

    public List<Apply> query(Apply Apply);
    public List<Apply> queryAll(List<Long> ids);

    public int submit(Apply Apply);

}