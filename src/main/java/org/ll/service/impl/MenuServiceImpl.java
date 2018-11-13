package org.ll.service.impl;

import java.util.List;

import org.ll.model.MenuDO;
import org.ll.service.MenuService;
import org.ll.service.common.CallAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
    
    @Autowired
    private CallAPIService callAPIService;
    
    @Override	
    public List<MenuDO>  getMenu(MenuDO menuDO){
        return callAPIService.get("/menus", List.class, menuDO);
    }
    
    @Override
    public int  saveMenu(MenuDO menuDO){
        return callAPIService.post("/menus", int.class, menuDO);
    }
    
}
