package amcn.amcn.member.domain.member;

import amcn.amcn.board.domain.board.Board;
import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.news.domain.News;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    String id;

    @OneToOne(mappedBy = "member")
    private Board board;

    @OneToOne(mappedBy = "member")
    private Comment comment;

    @OneToOne(mappedBy = "member")
    private News news;

    @OneToOne(mappedBy = "member")
    private CardNews cardNews;

    @OneToOne(mappedBy = "member")
    private Likes likes;


    private String loginId;

    @Transient
    private String passwordCheck;

    private String password;

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    private String phone;

    private Date birth;

    @Lob
    private String profile;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @CreatedDate
    private LocalDate createdDate;

}
