package org.ll.service;

import org.ll.model.QuotaDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    
    @Autowired
    private SimpMessagingTemplate template;
    
    public void sendMsgToUpdateQuota(QuotaDO quotaDO){
        template.convertAndSend("/topic/quota", quotaDO);
    }
}
