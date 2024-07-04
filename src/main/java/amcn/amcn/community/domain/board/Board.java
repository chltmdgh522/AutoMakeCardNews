package amcn.amcn.community.domain.board;

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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Lob
    private String title;

    @Column(columnDefinition = "TEXT")
    private String substance;

    @OneToMany(mappedBy = "board" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    private boolean modify;

    private String category;

    private String titleMax="X";


    private String substanceMax="X";

    private String boardImage;

    private int viewCount;


    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate modifyDate;


    public Board(){

    }
    public Board(Member member, String title, String substance, boolean modify, String category, String titleMax, String substanceMax, int viewCount, LocalDate createdDate, LocalDate modifyDate) {
        this.member = member;
        this.title = title;
        this.substance = substance;
        this.modify = modify;
        this.category = category;
        this.titleMax = titleMax;
        this.substanceMax = substanceMax;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.modifyDate = modifyDate;
    }



}
