package amcn.amcn.commonentity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;

@Embeddable
@Data
public class NewsGenerate {
    @Lob
    private String title; // 뉴스 제목 및 이미지 제목
    @Lob
    private String summary; // 요약본

    private String keyword; // 원본에서 요약한 문장중 키워드 뽑기

    public NewsGenerate(){

    }
}
