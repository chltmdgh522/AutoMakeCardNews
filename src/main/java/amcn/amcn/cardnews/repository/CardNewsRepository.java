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


    public Long save(CardNews cardNews){
        em.persist(cardNews);
        return cardNews.getCardNewsId();
    }
    public Optional<CardNews> findCardNewsId(Long id) {
        CardNews cardNews = em.find(CardNews.class, id);
        return Optional.ofNullable(cardNews);
    }

    public List<CardNews> findNewAll(){
        List<CardNews> list = em.createQuery("SELECT n FROM CardNews n ORDER BY n.cardNewsId DESC", CardNews.class)
                .setMaxResults(10)
                .getResultList();

        return list;
    }
}
