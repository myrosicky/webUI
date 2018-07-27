package org.ll.service.iface;


import java.util.List;
import org.ll.model.ChatCommentDO;

public interface ChatRoomService {

    public int send(ChatCommentDO chatRoomDO);
}