package amcn.amcn.commonentity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;

@Embeddable
@Data
public class NewsGenerate {
    @Lob
    private String title;
    @Lob
    private String summary; // 요약본

    private String keyword; // 원본에서 요약한 문장중 키워드 뽑기

    public NewsGenerate(){

    }
}
