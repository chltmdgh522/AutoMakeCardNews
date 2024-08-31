package amcn.amcn.cardnews.domain.cardnews;

import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CardNewsSubstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardNewsSubstanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    private String content;


    public CardNewsSubstance(){

    }
}
