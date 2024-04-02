package amcn.amcn.cardnews.domain.cardnews;

import amcn.amcn.board.domain.board.Board;
import amcn.amcn.commonentity.NewsGenerate;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CardNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private NewsGenerate newsGenerate;

    private String simpleImage;

    private String complexImage;

    @OneToMany(mappedBy = "cardNews")
    private List<CardNews> cards = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CardNewsType cardNewsType;

    private String sound;
}
