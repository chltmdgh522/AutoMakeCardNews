package amcn.amcn.board.domain.careerboard;

import amcn.amcn.board.domain.board.BoardType;
import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class CareerBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long career_board_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "careerBoard")
    private List<Comment> comments = new ArrayList<>();


    @OneToMany(mappedBy = "careerBoard")
    private List<Likes> likes = new ArrayList<>();

    @Size(max = 30)
    private String title;

    @Lob
    private String content;


    private boolean modify;

    @Enumerated(EnumType.STRING)
    private CareerType careerType;


    private String careerImage;

    private int viewCount;


    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate modifyDate;
}
