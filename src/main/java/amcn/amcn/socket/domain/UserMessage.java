package amcn.amcn.socket.domain;

import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMessageId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean confirm;


    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Getters and Setters
}