package org.ll.controller;

import org.ll.model.ChatCommentDO;
import org.ll.model.QuotaDO;
import org.ll.service.iface.QuotaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;

@Controller
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    
    @Autowired
    private QuotaService quotaService;
    
    @MessageMapping("/queryQuota")
    @SendTo("/topic/quota")
    public ListenableFuture<QuotaDO> quota(QuotaDO quota) throws Exception {
//        List<QuotaDO> result = quotaService.query(quota);
//        QuotaDO tmp = result.isEmpty()? new QuotaDO() : result.get(0);
        QuotaDO tmp = quota;
        tmp.setAmount(0);
        return new AsyncResult<QuotaDO>(tmp);
    }
    
    @MessageMapping("/queryComment")
    @SendTo("/topic/comment")
    public ListenableFuture<ChatCommentDO> queryComment(ChatCommentDO chatComment) throws Exception {
        chatComment.setColor("white");
        chatComment.setSize(1);
        chatComment.setPosition(1);
        chatComment.setTime(3);
        
        if(log.isDebugEnabled()){
            log.debug("comment:" + chatComment.getText());
        }
        return new AsyncResult<ChatCommentDO>(chatComment);
    }
    
    
    
}
