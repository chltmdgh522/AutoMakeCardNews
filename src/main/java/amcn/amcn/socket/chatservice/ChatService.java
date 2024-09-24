package amcn.amcn.socket.chatservice;

import amcn.amcn.socket.domain.dto.ChatMessage;
import amcn.amcn.socket.reposiotry.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;


    public List<ChatMessage> findChat(){
        List<ChatMessage> list=new ArrayList<>();
        return list;
    }
}
