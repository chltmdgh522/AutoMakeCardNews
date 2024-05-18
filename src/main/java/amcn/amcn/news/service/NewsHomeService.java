package amcn.amcn.news.service;

import amcn.amcn.news.domain.News;
import amcn.amcn.news.repository.NewsSpringDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsHomeService {

    private final NewsSpringDataRepository newsRepository;

    //페이징
    public Page<News> getList(String category, String title, int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("date"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        log.info("a={}", category);
        log.info("b={}", title);

        if (category.equals("") && title.equals("")) {
            return newsRepository.findAll(pageable);
        }

        return newsRepository.searchByTitleAndCategory(title,category,pageable);
    }
}
