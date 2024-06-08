package amcn.amcn.cardnews.repository;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@Slf4j
@RequiredArgsConstructor
public class CardNewsRepository {

    private final EntityManager em;


    public Long save(CardNews cardNews) {
        em.persist(cardNews);
        return cardNews.getCardNewsId();
    }

    public Optional<CardNews> findCardNewsId(Long id) {
        CardNews cardNews = em.find(CardNews.class, id);
        return Optional.ofNullable(cardNews);
    }

    public List<CardNews> findNewAll() {
        List<CardNews> list = em.createQuery("SELECT n FROM CardNews n where n.trash = 'X' ORDER BY n.cardNewsId DESC", CardNews.class)
                .setMaxResults(10)
                .getResultList();

        return list;
    }

    public void update(CardNews cardNews) {
        CardNews findCardNews = em.find(CardNews.class, cardNews.getCardNewsId());
        findCardNews.setJsonUrl(cardNews.getJsonUrl());
        findCardNews.setImageUrl(cardNews.getImageUrl());
        findCardNews.setMember(cardNews.getMember());
        findCardNews.setCategory(cardNews.getCategory());
        findCardNews.setTitle(cardNews.getTitle());
        findCardNews.setContent(cardNews.getContent());

    }

    public void updateTrash(CardNews cardNews) {
        CardNews findCardNews = em.find(CardNews.class, cardNews.getCardNewsId());
        findCardNews.setTrash(cardNews.getTrash());
    }

    public List<CardNews> findTrashAll(Member loginMember) {
        return em.createQuery("select c from CardNews c where c.trash = 'O' AND " +
                        "c.member.memberId = :memberId", CardNews.class)
                .setParameter("memberId", loginMember.getMemberId())
                .getResultList();
    }

    public void findTrashAllDelete(Member loginMember) {
        List<CardNews> resultList = em.createQuery("select c from CardNews c where c.trash = 'O' AND " +
                        "c.member.memberId = : memberId", CardNews.class)
                .setParameter("memberId",loginMember.getMemberId())
                .getResultList();
        for (CardNews cardNews : resultList) {
            em.remove(cardNews);
        }
    }

    public void findTrashAllRestore(Member loginMember) {
        List<CardNews> resultList = em.createQuery("select c from CardNews c where c.trash = 'O' AND " +
                        "c.member.memberId = : memberId", CardNews.class)
                .setParameter("memberId",loginMember.getMemberId())
                .getResultList();
        for (CardNews cardNews : resultList) {
            cardNews.setTrash("X");
        }
    }

    public void findTrashSelectDelete(List<Long> list) {

        for(Long id : list) {
            CardNews cardNews = em.find(CardNews.class, id);
            em.remove(cardNews);
        }

    }

    public void findTrashSelectRestore(List<Long> list) {
        for(Long id : list) {
            CardNews cardNews = em.find(CardNews.class, id);
            cardNews.setTrash("X");
        }

    }
}
