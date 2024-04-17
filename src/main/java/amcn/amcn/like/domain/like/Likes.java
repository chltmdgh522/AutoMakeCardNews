package amcn.amcn.like.domain.like;

import amcn.amcn.board.domain.board.Board;
import amcn.amcn.board.domain.careerboard.CareerBoard;
import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.news.domain.News;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "career_board_id")
    private CareerBoard careerBoard;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToOne
    @JoinColumn(name = "card_news_id")
    private CardNews cardNews;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
