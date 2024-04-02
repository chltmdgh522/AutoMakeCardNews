package amcn.amcn.like.domain.like;

import amcn.amcn.board.domain.board.Board;
import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;

@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "carddews_id")
    private CardNews cardNews;


    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;



}
