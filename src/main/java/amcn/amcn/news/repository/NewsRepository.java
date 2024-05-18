package amcn.amcn.news.repository;

import amcn.amcn.news.domain.News;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
@Slf4j
@RequiredArgsConstructor
public class NewsRepository {

    private final EntityManager em;


    public List<News> findAll() {
        return em.createQuery("SELECT new amcn.amcn.news.repository.dto.NewsDto(n.title, SUBSTRING(n.originalContent, 1, 20)) FROM News n", News.class)
                .getResultList();
    }
}
