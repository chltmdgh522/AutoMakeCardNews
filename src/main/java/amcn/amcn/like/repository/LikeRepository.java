package amcn.amcn.like.repository;

import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        Likes findLikes = null;
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

    public List<Likes> findByBookmarkNewsLike(Likes likes) {
        return em.createQuery("select l from Likes l where l.news.newsId = :news_id", Likes.class)
                .setParameter("news_id", likes.getNews().getNewsId())
                .getResultList();
    }






    public void cardNewsSave(Likes likes) {
        em.persist(likes);
        em.flush();
    }

    public String cardNewsRemove(Likes likes) {
        Likes findLikes = null;
        try {
            findLikes = em.createQuery("select l from Likes l where l.member.memberId = :member_id and " +
                            "l.cardNews.cardNewsId= :cardNews_id", Likes.class)
                    .setParameter("member_id", likes.getMember().getMemberId())
                    .setParameter("cardNews_id", likes.getCardNews().getCardNewsId())
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
            return null;
        }
        em.remove(findLikes);
        em.flush();
        return "삭제 성공";
    }


    // 아예 카드뉴스 삭제할떄 멤버 때문에 cascade뜸...
    public void cardRemove(List<Likes> likes){
        for (Likes like : likes) {
            em.remove(like);

        }
    }

    public String findByCardNewsLike(Likes likes) {
        try {
            Likes findLikes = em.createQuery("select l from Likes l where l.member.memberId = :member_id and " +
                            "l.cardNews.cardNewsId= :cardNews_id", Likes.class)
                    .setParameter("member_id", likes.getMember().getMemberId())
                    .setParameter("cardNews_id", likes.getCardNews().getCardNewsId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return "O";
        }
        return "X";
    }

    public List<Likes> findByBookmarkCardNewsLike(Likes likes) {
        return em.createQuery("select l from Likes l where l.cardNews.cardNewsId = :cardNews_id", Likes.class)
                .setParameter("cardNews_id", likes.getCardNews().getCardNewsId())
                .getResultList();
    }
}
