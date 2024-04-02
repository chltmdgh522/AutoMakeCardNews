package amcn.amcn.board.domain.board;

import amcn.amcn.comment.domain.comment.Comment;
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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments=new ArrayList<>();

    @Size(max = 30)
    private String title;

    @Lob
    private String content;

    @OneToMany(mappedBy = "board")
    private List<Board> boards=new ArrayList<>();

    private boolean modify;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private String boardImage;

    private int viewCount;


    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate modifyDate;

}
