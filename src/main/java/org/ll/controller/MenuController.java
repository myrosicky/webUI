package org.ll.controller;

import java.security.Principal;
import java.util.List;

import org.ll.enums.Role;
import org.ll.model.MenuDO;
import org.ll.service.iface.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/menu")           
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);
    
    @Autowired
    private MenuService menuService;
    
    @GetMapping("")    
    public String root(){
        return "menu";
    }     
    
    @GetMapping(value = "/getMenu.do", produces = "application/json; charset=UTF-8")
	@ResponseBody       
    public List<MenuDO> getMenu(MenuDO menuDO, Principal principal){
        return menuService.getMenu(menuDO);
    }   
    
    @PostMapping(value = "/saveMenu.do", produces = "application/json; charset=UTF-8")    
    @ResponseBody      
    public int saveMenu(@RequestBody MenuDO menuDO){
        return menuService.saveMenu(menuDO);
    }  
    
}
