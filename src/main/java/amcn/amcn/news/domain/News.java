package amcn.amcn.news.domain;

import amcn.amcn.cardnews.domain.cardnews.CardNewsType;
import amcn.amcn.commonentity.NewsGenerate;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long news_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "news")
    private List<Likes> likes=new ArrayList<>();

    private String journalistName; // 기자명

    private Date date; // 태초 뉴스 작성 시간 (사용자 및 관리자가 올린 뉴스 시간 XX)

    private String company; // 언론사
    @Lob
    private String originalContent; // 원본

    @Lob
    private String newsLink; // 해당 뉴스 링크


    @Embedded
    private NewsGenerate newsGenerate;

    @Enumerated(EnumType.STRING)
    private NewsType NewsType; // 뉴스 카테고리 IT, 스포츠 등 등

}
