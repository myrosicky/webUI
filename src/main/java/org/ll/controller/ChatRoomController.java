package org.ll.controller;


import org.ll.model.ChatCommentDO;
import org.ll.service.ChatRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chatRoom")           
public class ChatRoomController {

    private static final Logger log = LoggerFactory.getLogger(ChatRoomController.class);
    
    @Autowired
    private ChatRoomService chatRoomService;
    
    @GetMapping("")    
    public String root(){
        return "chatRoom";
    }     
    
    @PostMapping(value = "/send.do", produces = "application/json; charset=UTF-8")    
    @ResponseBody      
    public int send(@RequestBody ChatCommentDO chatRoomDO){
        return chatRoomService.send(chatRoomDO);
    }  
    
    
}
