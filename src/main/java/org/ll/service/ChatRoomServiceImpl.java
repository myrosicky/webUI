package org.ll.service;

import org.ll.dao.ChatRoomDAO;
import org.ll.model.ChatCommentDO;
import org.ll.service.iface.ChatRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private static final Logger log = LoggerFactory.getLogger(ChatRoomServiceImpl.class);
    
    @Autowired
    private ChatRoomDAO chatRoomDAO;
    
    @Override
    public int  send(ChatCommentDO chatRoomDO){
    	return chatRoomDAO.saveChatRoom(chatRoomDO);
    }
    
}
