package amcn.amcn.news.repository.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NewsDto {

    private Long newsId;

    private String title;

    private String originalContent;
    private String category;



}
