package amcn.amcn.news.repository;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.news.domain.News;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@Slf4j
@RequiredArgsConstructor
public class NewsRepository {

    private final EntityManager em;

    public Optional<News> findById(Long newsId) {
        try {
            News findNews = em.createQuery("select n from News n where n.newsId = :id ", News.class)
                    .setParameter("id", newsId)
                    .getSingleResult();
            return Optional.of(findNews);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    public List<News> findAll() {
        return em.createQuery("SELECT new amcn.amcn.news.repository.dto.NewsDto(n.title, SUBSTRING(n.originalContent, 1, 20)) FROM News n", News.class)
                .getResultList();
    }
}
