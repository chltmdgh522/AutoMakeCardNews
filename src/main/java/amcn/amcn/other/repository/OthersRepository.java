package amcn.amcn.other.repository;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OthersRepository {

    private final EntityManager em;

    public List<CardNews> findHeartCardNews(Member loginMember){
        List<Likes> findLikes = em.createQuery("select l from Likes l where l.member.memberId= :memberId", Likes.class)
                .setParameter("memberId", loginMember.getMemberId())
                .getResultList();

        log.info(String.valueOf(findLikes.size()));
        List<CardNews> list=new ArrayList<>();
        for (Likes findLike : findLikes) {
            try {
                if (findLike.getCardNews() != null) {
                    CardNews cardId = em.createQuery("select c from CardNews c where c.cardNewsId = :cardId and " +
                                    "c.trash = 'X'", CardNews.class)
                            .setParameter("cardId", findLike.getCardNews().getCardNewsId())
                            .getSingleResult();
                    list.add(cardId);
                }else{
                    log.info("널이라고?");
                }
            } catch (NoResultException e) {

                log.info(String.valueOf(e));
            }
        }

        return list;
    }

    public List<CardNews> findForkCardNews(Member loginMember){
        return em.createQuery("select c from CardNews c where c.fork != 0 and c.member.memberId = :memberId", CardNews.class)
                .setParameter("memberId", loginMember.getMemberId())
                .getResultList();

    }
}
