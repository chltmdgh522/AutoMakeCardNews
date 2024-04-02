package amcn.amcn.news.domain;

import amcn.amcn.commonentity.NewsGenerate;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;



    private String journalistName; // 기자명

    private Date date;

    private String company; // 언론사
    @Lob
    private String originalContent; // 원본

    @Embedded
    private NewsGenerate newsGenerate;

}
