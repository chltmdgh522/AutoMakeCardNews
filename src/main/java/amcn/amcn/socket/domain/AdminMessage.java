package amcn.amcn.socket.domain;


import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AdminMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Getters and Setters
}
