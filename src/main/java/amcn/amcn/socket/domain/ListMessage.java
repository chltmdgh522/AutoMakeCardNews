package amcn.amcn.socket.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ListMessage {
    private String type;
    private Long userMessageId;
    private String message;
    private LocalDateTime timestamp;


}
