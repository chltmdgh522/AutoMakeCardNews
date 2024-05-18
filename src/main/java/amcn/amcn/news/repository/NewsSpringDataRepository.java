package amcn.amcn.news.repository;

import amcn.amcn.news.domain.News;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;


public interface NewsSpringDataRepository extends JpaRepository<News, Long> {

    @Query("SELECT new amcn.amcn.news.repository.dto.NewsDto(n.news_id, n.title, SUBSTRING(n.originalContent, 1, 40),n.category) " +
            "FROM News n")
    Page<News> findAll(Pageable pageable);


    @Query("SELECT new amcn.amcn.news.repository.dto.NewsDto(n.news_id, n.title, SUBSTRING(n.originalContent, 1, 40),n.category) " +
            "FROM News n " +
            "WHERE (:title IS NULL OR n.title LIKE %:title%) " +
            "AND (:category IS NULL OR n.category LIKE %:category%)")
    Page<News> searchByTitleAndCategory(String title, String category, Pageable pageable);


}
