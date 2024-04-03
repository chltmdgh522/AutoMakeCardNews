package amcn.amcn.comment.domain.comment;

import amcn.amcn.board.domain.board.Board;
import amcn.amcn.board.domain.careerboard.CareerBoard;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_board_id")
    private CareerBoard careerBoard;

    private String content; // 댓글 내용

    @CreatedDate
    private LocalDate date; // 댓글 달린 시간



}
