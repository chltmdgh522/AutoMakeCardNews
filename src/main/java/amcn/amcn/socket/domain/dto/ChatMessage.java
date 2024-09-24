package amcn.amcn.socket.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String content;
    private String sender;
    private String memberId;
    private String senderType; // 'admin' 또는 'user'
}
