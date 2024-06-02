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
    private Long cardNewsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    // @Embedded
    // private NewsGenerate newsGenerate; // 이미지 검색하기 위해 제목, 키워드, 요약문

    private String message;

    // @Lob
    // private String simpleImage; // 단일이미지

    // @Lob
    // private String complexImage; // 영상 이미지 쟤네 둘(단일 영상)이 합칠까 생각중 ....

    @Lob
    private String image_url;

    @OneToMany(mappedBy = "cardNews")
    private List<Likes> likes = new ArrayList<>(); // 카드 뉴스 공감

    private String category;

    private String sound; // 카드뉴스 음향
}
