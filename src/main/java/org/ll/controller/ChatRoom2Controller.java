package org.ll.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chatRoom2")           
public class ChatRoom2Controller {

    private static final Logger log = LoggerFactory.getLogger(ChatRoom2Controller.class);
    
    
    @GetMapping("")    
    public String root(){
        return "chatRoom2";
    }     
    
}
