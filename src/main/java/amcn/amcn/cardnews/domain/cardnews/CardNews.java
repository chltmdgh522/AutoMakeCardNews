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

    private String trash;

    private Long fork = 0L;

    @Lob
    private String imageUrl;

    @Lob
    private String originalUrl;

    @OneToMany(mappedBy = "cardNews", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>(); // 카드 뉴스 공감

    private String category;

    private String sound; // 카드뉴스 음향

    @Transient
    private String selected;

    public CardNews() {

    }

    public CardNews(Member member, String title, String content, String edit, String trash, Long fork, String jsonUrl, String imageUrl, String originalUrl, String category) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.edit = edit;
        this.trash = trash;
        this.fork = fork;
        this.jsonUrl = jsonUrl;
        this.imageUrl = imageUrl;
        this.originalUrl = originalUrl;
        this.category = category;
    }
}
