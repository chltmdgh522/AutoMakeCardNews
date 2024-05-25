package amcn.amcn.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AIController {
    private final AIService aiService;

    @GetMapping("/ai-image")
    public String getImage(Model model) throws IOException, InterruptedException {
        String prompt = aiService.generatePictureV2("[KBS 부산]수소산업 생태계 조성과 인재 양성을 위해 12개 대학이 참여하는 '부산 수소공유대학'이 오늘 우암부두 부산대 수소선박기술센터에서 출범했습니다.부산 수소공유대학에서는 '수소가스 분석 및 수소경제 전문가'와 '수소모빌리티와 핵심모듈 전문가' 등 2개 과정을, 각각 6개 대학이 나눠 맡아 교육합니다.참여 기관들은 부산지역 지·산·학 협력 거버넌스 구축으로 지역 대학생과 수소 전문기업 재직자의 기술 경쟁력을 강화할 수 있을 것으로 기대하고 있습니다.");
        model.addAttribute("url", prompt);
        return "test";
    }
    @PostMapping("/ai-image")
    public ResponseEntity<?> generateImage(@RequestBody String prompt) throws IOException, InterruptedException {

        return new ResponseEntity<>(aiService.generatePictureV2(prompt), HttpStatus.OK);
    }
}
