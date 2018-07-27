package org.ll.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.ll.dao.ChatRoomDAO;
import org.ll.service.iface.ChatRoomService;
import org.ll.model.ChatCommentDO;

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
