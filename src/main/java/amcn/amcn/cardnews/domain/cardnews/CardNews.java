package amcn.amcn.cardnews.domain.cardnews;

import amcn.amcn.board.domain.board.Board;
import amcn.amcn.commonentity.NewsGenerate;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CardNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // @Embedded
    // private NewsGenerate newsGenerate; // 이미지 검색하기 위해 제목, 키워드, 요약문

    private String message;

    // @Lob
    // private String simpleImage; // 단일이미지

    // @Lob
    // private String complexImage; // 영상 이미지 쟤네 둘(단일 영상)이 합칠까 생각중 ....

    private String image;

    @OneToMany(mappedBy = "cardNews")
    private List<Likes> likes = new ArrayList<>(); // 카드 뉴스 공감

    @Enumerated(EnumType.STRING)
    private CardNewsType cardNewsType; // 카드뉴스 카테고리 IT, 스포츠 등 등

    private String sound; // 카드뉴스 음향
}
