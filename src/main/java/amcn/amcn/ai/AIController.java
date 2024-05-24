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
        String prompt = aiService.generatePictureV2("눈내리고 있고 크리스마스 분위기나는 이미지 좀 생성해줘");
        model.addAttribute("url", prompt);
        return "test";
    }
    @PostMapping("/ai-image")
    public ResponseEntity<?> generateImage(@RequestBody String prompt) throws IOException, InterruptedException {
        return new ResponseEntity<>(aiService.generatePictureV2(prompt), HttpStatus.OK);
    }
}
