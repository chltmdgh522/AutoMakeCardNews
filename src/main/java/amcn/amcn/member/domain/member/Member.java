package amcn.amcn.member.domain.member;

import amcn.amcn.community.domain.board.Board;
import amcn.amcn.community.domain.careerboard.CareerBoard;
import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.news.domain.News;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    String memberId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> board = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CareerBoard> careerBoard = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<News> news = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardNews> cardNews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();


    private String loginId;

    @Transient
    private String passwordCheck;

    private String password;

    private String name;

    @Email
    private String email;

    @Transient
    private String emailF;

    @Transient
    private EmailType emailType;

    @Transient
    private String domain;

    private boolean aiImg;

    @Lob
    private String originalUrl;

    private LocalDate birthday;
    @Transient
    private String authPassword;


    private int hello;

    private Long point;

    @Lob
    private String profile;

    @Enumerated(EnumType.STRING)
    private MemberType memberSex;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @CreatedDate
    private LocalDate createdDate;

    public Member(){

    }

    public Member(String memberId, String loginId, String password, String email, LocalDate birthday, LocalDate createdDate, String profile,
                  RoleType roleType, MemberType memberSex, String name, Long point) {
        this.memberId =memberId;
        this.loginId=loginId;
        this.password=password;
        this.email=email;
        this.birthday=birthday;
        this.createdDate=createdDate;
        this.profile=profile;
        this.roleType=roleType;
        this.memberSex=memberSex;
        this.name=name;
        this.point=point;
    }

}
