package amcn.amcn.news.domain;

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
    private Long newsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "news" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();


    private Date date; // 태초 뉴스 작성 시간 (사용자 및 관리자가 올린 뉴스 시간 XX)


    @Column(columnDefinition = "TEXT") //@Lob보다 더클때
    private String originalContent; // 원본

    @Lob
    private String newsLink; // 해당 뉴스 링크

    @Lob
    private String title; // 뉴스 제목

    @Column(columnDefinition = "TEXT") //@Lob보다 더클때
    private String summaryContent; //  뉴스 요약

    private String keyword; // 원본에서 요약한 문장중 키워드 뽑기

    private String category; //타입으로 넣은것보다 string이 나은듯

    //@Enumerated(EnumType.STRING)
    //private NewsType NewsType; // 뉴스 카테고리 IT, 스포츠 등 등


}
