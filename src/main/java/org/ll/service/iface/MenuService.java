package org.ll.service.iface;


import java.util.List;
import org.ll.model.MenuDO;

public interface MenuService {

    public List<MenuDO> getMenu(MenuDO menuDO);
    public int saveMenu(MenuDO menuDO);
}