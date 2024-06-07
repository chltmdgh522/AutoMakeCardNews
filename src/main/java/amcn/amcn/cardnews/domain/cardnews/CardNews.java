package amcn.amcn.cardnews.domain.cardnews;

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

    @Lob
    private String jsonUrl;

    private String message;

    private String edit;

    @Lob
    private String imageUrl;

    @Lob
    private String originalUrl;

    @OneToMany(mappedBy = "cardNews")
    private List<Likes> likes = new ArrayList<>(); // 카드 뉴스 공감

    private String category;

    private String sound; // 카드뉴스 음향

    @Transient
    private String selected;
}
