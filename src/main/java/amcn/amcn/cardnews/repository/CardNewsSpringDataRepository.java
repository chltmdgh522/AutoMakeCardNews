package amcn.amcn.cardnews.repository;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.news.domain.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardNewsSpringDataRepository extends JpaRepository<CardNews, Long> {
    @Query("SELECT e FROM CardNews e WHERE (:title IS NULL OR e.title LIKE %:title%) AND (:category IS NULL OR e.category = :category OR :category = '') ORDER BY CASE WHEN :selected = '최신' THEN e.cardNewsId END DESC, CASE WHEN :selected = '' THEN e.cardNewsId END DESC, CASE WHEN :selected = '과거' THEN e.cardNewsId END ASC")
    List<CardNews> findSearchCardNews(
            @Param("title") String title,
            @Param("category") String category,
            @Param("selected") String selected
    );

    @Query("SELECT e FROM CardNews e WHERE e.edit = 'X' AND (:category IS NULL OR e.category = :category OR :category = '') ORDER BY CASE WHEN :selected = '최신' THEN e.cardNewsId END DESC, CASE WHEN :selected = '' THEN e.cardNewsId END DESC, CASE WHEN :selected = '과거' THEN e.cardNewsId END ASC")
    List<CardNews> findSearchTemplate(
            @Param("category") String category,
            @Param("selected") String selected
    );

}
