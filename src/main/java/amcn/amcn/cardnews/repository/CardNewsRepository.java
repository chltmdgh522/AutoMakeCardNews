package amcn.amcn.cardnews.repository;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Transactional
@Repository
@Slf4j
@RequiredArgsConstructor
public class CardNewsRepository {

    private final EntityManager em;


    public void save(CardNews cardNews){
        em.persist(cardNews);
    }
}
