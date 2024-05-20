package amcn.amcn.like.repository;

import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Repository
@Transactional
public class LikeRepository {
    private final EntityManager em;

    public void newsSave(Likes likes) {
        em.persist(likes);
        em.flush();
    }

    public String newsRemove(Likes likes) {
        Likes findLikes=null;
        try {
            findLikes = em.createQuery("select l from Likes l where l.member.memberId = :member_id and " +
                            "l.news.newsId= :news_id", Likes.class)
                    .setParameter("member_id", likes.getMember().getMemberId())
                    .setParameter("news_id", likes.getNews().getNewsId())
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
            return null;
        }
        em.remove(findLikes);
        em.flush();
        return "삭제 성공";
    }

    public String findByNewsLike(Likes likes) {
        try {
            Likes findLikes = em.createQuery("select l from Likes l where l.member.memberId = :member_id and " +
                            "l.news.newsId= :news_id", Likes.class)
                    .setParameter("member_id", likes.getMember().getMemberId())
                    .setParameter("news_id", likes.getNews().getNewsId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return "O";
        }
        return "X";
    }
}
